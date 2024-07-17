package com.synrgy7team4.feature_auth.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.synrgy7team4.feature_auth.R
import com.synrgy7team4.feature_auth.databinding.ActivityLoginBinding
import com.synrgy7team4.feature_auth.presentation.onBoarding.OnBoardingFragment

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@LoginActivity, OnBoardingFragment::class.java))
        }
    }
}