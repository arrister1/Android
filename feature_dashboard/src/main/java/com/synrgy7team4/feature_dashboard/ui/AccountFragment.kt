package com.synrgy7team4.feature_dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.synrgy7team4.common.NavigationHandler
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_dashboard.R
import com.synrgy7team4.feature_dashboard.databinding.FragmentAccountBinding
import com.synrgy7team4.feature_dashboard.viewmodel.AccountViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val navHandler: NavigationHandler by inject()
    private val viewModel by viewModel<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAccountBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()

        binding.linearlayKeluarAkun.setOnClickListener {
            makeToast(requireContext(),"Anda berhasil keluar")
            lifecycleScope.launch {
                awaitAll(
                    viewModel.deleteTokens(),
                    viewModel.deleteUserData()
                )
                navHandler.navigateToOnBoarding()
            }
        }
        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.tvNamaPengguna.text = userData.name
            binding.tvEmailPengguna.text = userData.email
            binding.tvNoHpPengguna.text = userData.noHp
            setupAccessibility(userData.name, userData.email, userData.noHp)
        }
    }

    fun setupAccessibility(userName: String, userEmail: String, userPhoneNumber: String) {
        binding.apply {
            imgProfilPengguna.contentDescription = getString(R.string.profil_pengguna)
            tvNamaPengguna.contentDescription = userName
            tvEmailPengguna.contentDescription = userEmail
            tvNoHpPengguna.contentDescription = userPhoneNumber
            btnEdit.contentDescription = getString(R.string.edit_profil)
            linearlayUbahPassword.contentDescription = getString(R.string.ubah_password)
            linearlayNotifikasi.contentDescription = getString(R.string.notifikasi)
            linearlayPusatBantuan.contentDescription = getString(R.string.pusat_bantuan)
            linearlaySyaratKetentuan.contentDescription = getString(R.string.syarat_ketentuan)
            linearlayKebijakanPrivasi.contentDescription = getString(R.string.kebijakan_privasi)
            linearlayKeluarAkun.contentDescription = getString(R.string.keluar_akun)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}