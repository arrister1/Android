package com.synrgy7team4.feature_auth.ui.registerScreen

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.FragmentUploadKtpBinding
import java.io.ByteArrayOutputStream
import java.io.IOException

class UploadKtpFragment : Fragment() {
    private var _binding: FragmentUploadKtpBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private var sImage: String? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    Toast.makeText(
                        requireActivity(),
                        "Mohon Tunggu Hingga Proses Upload Selesai",
                        Toast.LENGTH_SHORT
                    ).show()
                    handleImageUri(it)
                }

                sImage?.let {
                    val bytes = Base64.decode(it, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    binding.ivKtp.setImageBitmap(bitmap)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUploadKtpBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAccessibility()

        sharedPreferences =
            requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)

        binding.btnEncode.setOnClickListener {
            selectImage()
        }

        binding.btnSend.setOnClickListener {
            makeToast(requireContext(), "Mohon tunggu sebentar")
            findNavController().navigate(R.id.action_uploadKtpFragment_to_verifikasiKtpFragment)
        }
    }

    private fun selectImage() {
//        binding.tvEncode.text = ""
        binding.ivKtp.setImageBitmap(null)
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        pickImageLauncher.launch(Intent.createChooser(intent, "Pilih Gambar"))
    }

    private fun handleImageUri(uri: Uri) {
        try {
            val stream = ByteArrayOutputStream()
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream)

            val bytes = stream.toByteArray()
            sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
//            sImage = encodeImageToBase64(resizingBitmap)
            sharedPreferences.edit().putString("ktp", sImage).apply()
//            binding.tvEncode.text = sImage
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setupAccessibility() {
        binding.apply {
            ivKtp.contentDescription = getString(R.string.ektp_image)
            btnSend.contentDescription = getString(R.string.tombol_kirim)
            btnEncode.contentDescription = getString(R.string.tombol_upload_ktp)
            tvDesc.contentDescription =
                getString(R.string.teks_silahkan_upload_dan_kirim_e_ktp_anda)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}