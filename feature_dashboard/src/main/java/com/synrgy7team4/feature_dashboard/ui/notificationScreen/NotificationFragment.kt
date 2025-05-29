package com.synrgy7team4.feature_dashboard.ui.notificationScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy7team4.common.Log
import com.synrgy7team4.common.makeToast
import com.synrgy7team4.feature_dashboard.databinding.FragmentNotificationBinding
import com.synrgy7team4.feature_dashboard.viewmodel.NotificationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<NotificationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentNotificationBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotification()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.notificationGetResponse.observe(viewLifecycleOwner) { notifications ->
            val notificationAdapter = NotificationAdapter(notifications)
            binding.rvNotifikasi.adapter = notificationAdapter
            binding.rvNotifikasi.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            makeToast(requireContext(), error.message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}