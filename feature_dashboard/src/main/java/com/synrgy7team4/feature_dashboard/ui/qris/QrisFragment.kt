package com.synrgy7team4.feature_dashboard.ui.qris

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
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.Reader
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.FragmentQrisBinding
import com.synrgy7team4.feature_dashboard.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class QrisFragment : Fragment() {
    private var _binding: FragmentQrisBinding? = null
    private val binding get() = _binding!!

    private lateinit var codeScanner: CodeScanner
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel by viewModel<HomeViewModel>()
    private var isHidden: Boolean = false
    private lateinit var accountNumber: String
    private lateinit var fullBalance: String
    private lateinit var hiddenBalance: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentQrisBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()

        sharedPreferences= requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback { textResultScan ->
            activity.runOnUiThread {
                Toast.makeText(activity, textResultScan.text, Toast.LENGTH_LONG).show()
                sharedPreferences.edit().putString("accountToTransfer", textResultScan.text).apply()
                val deepLinkToTrans = Uri.parse("app://com.example.app/trans/transferInput")
                view.findNavController().navigate(deepLinkToTrans)
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        binding.payButton.setOnClickListener {
            sharedPreferences.edit().putString("payOrReceived", "membayar" ).apply()
            showDialogQRGeneratePage(viewModel)
//            findNavController().navigate(R.id.action_navigation_dashboard_to_showQrisFragment)
        }

        binding.transferButton.setOnClickListener {
            sharedPreferences.edit().putString("payOrReceived", "menerima transfer" ).apply()
            showDialogQRGeneratePage(viewModel)
//            findNavController().navigate(R.id.action_navigation_dashboard_to_showQrisFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.galleryButton.setOnClickListener {
            openGallery()
        }



        fullBalance = getString(R.string.dummy_account_balance)
        hiddenBalance = fullBalance.replace(Regex("\\d"), "*").replace(Regex("[,.]"), "")

        accountNumber = R.string.dummy_acc_number.toString()


        viewModel.userData.observe(viewLifecycleOwner) {userData ->
            accountNumber = userData.accountNumber
        }
        viewModel.getUserData()


        viewModel.userBalance.observe(viewLifecycleOwner) { balance ->
            fullBalance = balance.toString()
        }
        viewModel.getUserBalance()



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
        val accountNo = dialog.findViewById<TextView>(R.id.accountNo)
        val qrImageView = dialog.findViewById<ImageView>(R.id.qrImageView)
        val ivToggleBalance = dialog.findViewById<ImageView>(R.id.iv_toggle_balance)
        val ammount = dialog.findViewById<TextView>(R.id.ammount)


        val getPayText = sharedPreferences.getString("payOrReceived", null)

        tvGet.text = getPayText
        accountNo.text = accountNumber
        ammount.text = fullBalance

        val mQRBitmap = QRUtility.generateQR(accountNumber)
        if (mQRBitmap != null) {
            qrImageView.setImageBitmap(mQRBitmap)
        } else {
            Toast.makeText(requireActivity(), "Failed to Generated QR Code", Toast.LENGTH_SHORT).show()
        }





        ivToggleBalance.setOnClickListener {

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