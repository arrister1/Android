package com.synrgy7team4.feature_auth.presentation.register

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentFotoKtpBinding
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class FotoKtpFragment : Fragment(), ImageCapture.OnImageSavedCallback {

    private var _binding: FragmentFotoKtpBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private var camera: Camera? = null
    private var imageCapture: ImageCapture? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var recognizer: TextRecognizer

    private val backCameraSelector by lazy {
        getCameraSelector(CameraSelector.LENS_FACING_BACK)
    }

    private val frontCameraSelector by lazy {
        getCameraSelector(CameraSelector.LENS_FACING_FRONT)
    }

    private val storagePermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                startCamera(backCameraSelector)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Storage permission not available closing app",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val cameraPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                startCamera(backCameraSelector)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera permission not available closing app",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cameraProvider?.unbindAll()
        _binding = FragmentFotoKtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCapture.setOnClickListener {
            //takePicture()
            //Biar lancar navigasinya dulu di emulator, uncomment kalo dah fiks, trus comment navigasi dibawahnya ini
            //requireView().findNavController().navigate(R.id.action_fotoKtpFragment_to_verifikasiKtpFragment)
        }
        binding.btnBack.setOnClickListener {
            requireView().findNavController().popBackStack()
        }
        binding.btnFlipCamera.setOnClickListener {
            flipCamera()
        }

        // Check for camera permission and request if not granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cameraPermissionResult.launch(Manifest.permission.CAMERA)
            storagePermissionResult.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            storagePermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            startCamera(backCameraSelector)
        }
    }

    private fun startCamera(cameraSelector: CameraSelector) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider!!, cameraSelector)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider, cameraSelector: CameraSelector) {
        val preview = Preview.Builder().build()
        val previewView = binding.previewView

        preview.setSurfaceProvider(previewView.surfaceProvider)

        imageCapture = ImageCapture.Builder()
            .setTargetRotation(previewView.display.rotation)
            .build()

        try {
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                this as LifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (exc: Exception) {
            Toast.makeText(requireContext(), "Error binding camera use cases", Toast.LENGTH_SHORT).show()
        }
    }

    private fun flipCamera() {
        val currentCameraProvider = cameraProvider ?: return

        val cameraSelector = if (camera?.cameraInfo?.lensFacing == CameraSelector.LENS_FACING_FRONT) {
            backCameraSelector
        } else {
            frontCameraSelector
        }

        startCamera(cameraSelector)
    }

    private fun takePicture() {
        val imageCapture = imageCapture ?: return

        val outputOptions = getImageOutputOptions()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            this
        )
    }

    private fun getCameraSelector(lensFacing: Int) =
        CameraSelector.Builder().requireLensFacing(lensFacing).build()

    private fun getImageOutputOptions(): ImageCapture.OutputFileOptions {
        val contentResolver = requireContext().contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        return ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()
    }

    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        val savedUri = outputFileResults.savedUri ?: return
        val image = InputImage.fromFilePath(requireContext(), savedUri)
        processImage(image)
    }

    private fun processImage(image: InputImage) {
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                handleTextRecognitionResult(visionText)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to recognize text", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleTextRecognitionResult(visionText: Text) {
        // Handle the recognized text here
        val recognizedText = visionText.text
        print("Text recognized: $recognizedText")
        //requireView().findNavController().navigate(R.id.action_fotoKtpFragment_to_verifikasiKtpFragment)
        Snackbar.make(binding.root, "Text recognized", Snackbar.LENGTH_LONG).show()
    }

    override fun onError(exception: ImageCaptureException) {
        exception.printStackTrace()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
