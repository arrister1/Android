package com.synrgy7team4.feature_dashboard.presentation.ui.qris

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.FragmentQrisBinding
import com.synrgy7team4.feature_dashboard.presentation.ui.home.HomeViewModel
import com.synrgy7team4.feature_dashboard.utility.QrUtility
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class QrisFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private var _binding: FragmentQrisBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private var isHidden: Boolean = false
    private lateinit var fullBalance: String
    private lateinit var hiddenBalance: String


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val qrisViewModel =
            ViewModelProvider(this).get(QrisViewModel::class.java)

        _binding = FragmentQrisBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        qrisViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences= requireActivity().getSharedPreferences("RegisterPrefs", Context.MODE_PRIVATE)
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            HomeViewModel::class.java)


        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        binding.payButton.setOnClickListener {
            sharedPreferences.edit().putString("payOrReceived", "membayar" ).apply()
            val deepLinkUri = Uri.parse("app://com.example.app/dashboard/showQris")
//            view.findNavController().navigate(deepLinkUri)
            showDialogQRGeneratePage(viewModel)
        }

        binding.transferButton.setOnClickListener {
            sharedPreferences.edit().putString("payOrReceived", "menerima transfer" ).apply()
            val deepLinkUri = Uri.parse("app://com.example.app/dashboard/showQris")
//            view.findNavController().navigate(deepLinkUri)
            showDialogQRGeneratePage(viewModel)
        }

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.galleryButton.setOnClickListener {
            openGallery()
        }



        fullBalance = getString(R.string.dummy_account_balance)
        hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")




    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncherGallery.launch(galleryIntent)
    }

    private var resultLauncherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            val imageUri = data!!.data!!
            val imagePath = convertMediaUriToPath(imageUri)
            val imgFile = File(imagePath)
            scanImageQRCode(imgFile)
        } else {
            Toast.makeText(requireContext(), "Result Not Found", Toast.LENGTH_LONG).show()
        }
    }

    private fun convertMediaUriToPath(uri: Uri):String {
        val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor = context?.contentResolver?.query(uri, proj, null, null, null)
        val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(columnIndex)
        cursor.close()
        return path
    }

    private fun scanImageQRCode(file: File){
        val inputStream: InputStream = BufferedInputStream(FileInputStream(file))
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val decoded = scanQRImage(bitmap)
        Toast.makeText(requireContext(), "Decoded string=$decoded", Toast.LENGTH_SHORT).show()
    }

    private fun scanQRImage(bMap: Bitmap): String? {
        var contents: String? = null
        val intArray = IntArray(bMap.width * bMap.height)
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.width, 0, 0, bMap.width, bMap.height)
        val source: LuminanceSource = RGBLuminanceSource(bMap.width, bMap.height, intArray)
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        val reader: Reader = MultiFormatReader()
        try {
            val result: Result = reader.decode(bitmap)
            contents = result.text

        } catch (e: Exception) {
            Log.e("QrTest", "Error decoding qr code", e)
            Toast.makeText(requireContext(), "Error decoding QR Code, Mohon pilih gambar QR Code yang benar!", Toast.LENGTH_SHORT).show()
        }
        return contents
    }

    private fun showDialogQRGeneratePage(viewModel: HomeViewModel) {
        val dialog: Dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.fragment_show_qris)
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = com.synrgy7team4.common.R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

        val tvGet = dialog.findViewById<TextView>(R.id.tvGet)
        val amountTextView = dialog.findViewById<TextView>(R.id.amountTextView)
        val accountNo = dialog.findViewById<TextView>(R.id.accountNo)
        val qrImageView = dialog.findViewById<ImageView>(R.id.qrImageView)
        val ivToggleBalance = dialog.findViewById<ImageView>(R.id.iv_toggle_balance)
        val ammount = dialog.findViewById<TextView>(R.id.ammount)


        val getAccountNumber = sharedPreferences.getString("accountNumber", null)
        val getPayText = sharedPreferences.getString("payOrReceived", null)
        val getToken = sharedPreferences.getString("token", null)

        tvGet.text = getPayText

//        viewModel.fectData(getToken, getAccountNumber)
        viewModel.data.observe(viewLifecycleOwner) { data ->
            amountTextView.text = data.data.toString()
            accountNo.text = getAccountNumber
        }

        val mQRBitmap = QrUtility.generateQR(getAccountNumber!!)
        if (mQRBitmap != null) {
            qrImageView.setImageBitmap(mQRBitmap)
        } else {
            Toast.makeText(requireActivity(), "Failed to Generated QR Code", Toast.LENGTH_SHORT).show()
        }

        ivToggleBalance.setOnClickListener {
//            balanceVisibility
            if (isHidden) {
                ammount.text = fullBalance
                ivToggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_on)
            } else {
                ammount.text = hiddenBalance
                ivToggleBalance.setImageResource(com.synrgy7team4.common.R.drawable.ic_visibility_off)
            }
            isHidden = !isHidden
        }

    }




    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}