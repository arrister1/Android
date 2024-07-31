package com.synrgy7team4.feature_auth.presentation.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jer.shared.ViewModelFactoryProvider
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentUploadKtpBinding
import com.synrgy7team4.feature_auth.presentation.viewmodel.RegisterViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class UploadKtpFragment : Fragment() {

    private val binding by lazy { FragmentUploadKtpBinding.inflate(layoutInflater) }
    private var currentImageUri: Uri? = null
    private lateinit var sharedPreferences: SharedPreferences
//    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

//    companion object {
//        private const val FILENAME_FORMAT ="yyyyMMdd_HHmmss"
//    }

    private val viewModel by viewModels<RegisterViewModel> {
        val app = requireActivity().application as ViewModelFactoryProvider
        app.provideViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        binding.btnUpload.setOnClickListener {
            selectFile()
            showPicture()
        }

        binding.btnSend.setOnClickListener {
            sendRegisterRequest()
            requireView().findNavController().navigate(R.id.action_uploadKtpFragment_to_registrationSuccessFragment)
        }

        viewModel.registerResult.observe(viewLifecycleOwner) {
            setToast("Selamat! Registrasi Berhasil, \nTerimakasih Telah Melengkapi Data Kamu ")
        }

        viewModel.error.observe(viewLifecycleOwner) {
            setToast("Error: $it")
        }

    }

    private fun showPicture() {
        currentImageUri?.let {
            binding.ivKtp.setImageURI(it)
        }
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_FILE && resultCode ==  Activity.RESULT_OK) {
            currentImageUri = data?.data
        }
    }

    companion object {
        private const val REQUEST_CODE_SELECT_FILE = 1001
    }

    private fun sendRegisterRequest() {
        val email = sharedPreferences.getString("email", "budi@example.com")
        val hp = sharedPreferences.getString("hp", "911")
        val password = sharedPreferences.getString("password", "12345678")
        val confirm_password = sharedPreferences.getString("confirm_password", "12345678")
        val ktp = sharedPreferences.getString("ktp", "")
        val name = sharedPreferences.getString("name", "Budi")
        val date = sharedPreferences.getString("date", "01-01-2000")
        val pin = sharedPreferences.getString("pin", "111111")
        val confirm_pin = sharedPreferences.getString("confirm_pin", "111111")

        if (!email.isNullOrEmpty() &&
            !hp.isNullOrEmpty() &&
            !password.isNullOrEmpty() &&
            !confirm_password.isNullOrEmpty() &&
            !ktp.isNullOrEmpty() &&
            !name.isNullOrEmpty() &&
            !date.isNullOrEmpty() &&
            !pin.isNullOrEmpty() &&
            !confirm_pin.isNullOrEmpty()
        )
        {
            currentImageUri?.let {
                viewModel.registerUser(email.toString(), hp.toString(), password.toString(), confirm_password.toString(), ktp.toString(), name.toString(), date.toString(), pin.toString(), confirm_pin.toString(), it.toString(), requireActivity(), it)

            } ?: kotlin.run {
                setToast("Mohon pilih gambar dahulu")
            }

        } else {
            setToast("Registration data is not complete")
        }

    }

//
//    private fun startGallery() {
//        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }
//
//    private val launcherGallery = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia()
//    ) { uri: Uri? ->
//        if (uri != null) {
//            currentImageUri = uri
//            showPicture()
//        } else {
//            Log.d("Photo Picker", "No media selected")
//        }
//    }




//    private fun showPicture() {
//        currentImageUri?.let {
//            binding.ivKtp.setImageURI(it)
//        }
//    }
//
//    private fun uploadPicture() {
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, this).reduceFileImage()
//            val description = binding.tfInputDescription.text.toString().trim()
//
//            val requestBody = description.toRequestBody("text/plain".toMediaType())
//            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "photo",
//                imageFile.name,
//                requestImageFile
//            )
//
//
//
//            uploadViewModel.fileUploadResponse.observe(this) {
//                if (it.error) {
//                    showToast(it.message)
//                } else {
//                    val intent = Intent(this, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    showToast(it.message)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        } ?: showToast(getString(R.string.warning_if_empty))
//    }
//
//    fun uriToFile(imageUri: Uri, context: Context): File {
//        val myFile = createCustomTempFile(context)
//        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
//        val outputStream = FileOutputStream(myFile)
//        val buffer = ByteArray(1024)
//        var length: Int
//        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
//        outputStream.close()
//        inputStream.close()
//        return myFile
//    }
//
//    fun createCustomTempFile(context: Context): File {
//        val filesDir = context.externalCacheDir
//        return File.createTempFile(timeStamp, ".jpg", filesDir)
//    }


    private fun setToast(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }


}