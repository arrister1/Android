package com.synrgy7team4.feature_dashboard.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synrgy7team4.feature_mutasi.R
import com.synrgy7team4.feature_mutasi.databinding.ActivityMutationBinding
import com.synrgy7team4.feature_mutasi.presentation.ui.MutasiFragment


class MutationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMutationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMutationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = MutasiFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    }
}