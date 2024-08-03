package com.synrgy7team4.feature_mutasi.presentation.ui

import MutationAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy7team4.feature_mutasi.R
import com.synrgy7team4.feature_mutasi.databinding.FragmentMutasiBinding
import com.synrgy7team4.feature_mutasi.presentation.viewmodel.MutasiViewmodel


class MutasiFragment : Fragment() {

    private var _binding: FragmentMutasiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MutasiViewmodel by viewModel()
    private lateinit var adapter: MutationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMutasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the spinner
        val transaksi = resources.getStringArray(R.array.spinner_jenis_transaksi)
        val spinner = binding.spinner
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, transaksi)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Handle spinner selection
                viewModel.loadMutations("9074822924") // Replace with actual account number if needed
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Set up the RecyclerView
        setupRecyclerView()

        // Observe the ViewModel data
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = MutationAdapter(emptyList())
        binding.rvMutasi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMutasi.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.mutations.observe(viewLifecycleOwner, Observer { mutations ->
            adapter = MutationAdapter(mutations)
            binding.rvMutasi.adapter = adapter
        })

        // Optionally, observe loading and error states
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            // Show/hide loading indicator
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            // Show error message
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
