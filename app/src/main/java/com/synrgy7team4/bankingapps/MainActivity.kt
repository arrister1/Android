package com.synrgy7team4.bankingapps

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.synrgy7team4.bankingapps.databinding.ActivityMainBinding
import com.synrgy7team4.common.NavigationHandler
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), NavigationHandler, TokenHandler, UserHandler {
    companion object {
        private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_PHONE_NUMBER_KEY = stringPreferencesKey("user_phone_number")
        private val ACCOUNT_NUMBER_KEY = stringPreferencesKey("account_number")
        private val ACCOUNT_PIN_KEY = stringPreferencesKey("account_pin")
        private val DATE_OF_BIRTH_KEY = stringPreferencesKey("date_of_birth")
        private val KTP_NUMBER_KEY = stringPreferencesKey("ktp_number")
        private val KTP_PHOTO_KEY = stringPreferencesKey("ktp_photo")
    }

    private val dataStore: DataStore<Preferences> by inject()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getKoin().declare(this as NavigationHandler)
        getKoin().declare(this as TokenHandler)
        getKoin().declare(this as UserHandler)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Mengatur kemunculan bottom navigasi pada fragment tertentu
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                com.synrgy7team4.feature_dashboard.R.id.homeFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.bottomNav.menu.findItem(R.id.menu_home).iconTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.primary_darkBlue))
                }

                com.synrgy7team4.feature_dashboard.R.id.accountFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.bottomNav.menu.findItem(R.id.menu_account).iconTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.primary_darkBlue))
                }

                else -> {
                    binding.bottomNav.visibility = View.GONE
                }
            }
        }
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        // Mengatur navigasi bottom navigation
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    navController.navigate(com.synrgy7team4.feature_dashboard.R.id.homeFragment)
                    true
                }

                R.id.menu_qris -> {
                    navController.navigate(com.synrgy7team4.feature_dashboard.R.id.qrisFragment)
                    true
                }

                R.id.menu_account -> {
                    navController.navigate(com.synrgy7team4.feature_dashboard.R.id.accountFragment)
                    true
                }

                else -> false
            }
        }
    }

    // Implementasi NavigationHandler
    override fun navigateToOnBoarding() {
        navController.navigate(R.id.dashboard_to_onboarding_navigation)
    }

    override fun navigateToDashboard() {
        navController.navigate(R.id.auth_to_dashboard_navigation)
    }

    override fun navigateToMutasi() {
        navController.navigate(R.id.dashboard_to_mutasi_navigation)
    }

    override fun navigateToTransfer() {
        navController.navigate(R.id.dashboard_to_transfer_navigation)
    }

    override fun navigateQrisToTransfer() {
        navController.navigate(R.id.qris_to_transfer_navigation)
    }

    // Implementasi TokenHandler
    override suspend fun saveTokens(jwtToken: String, refreshToken: String) {
        dataStore.edit {
            it[JWT_TOKEN_KEY] = jwtToken
            it[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    override suspend fun loadJwtToken(): String? =
        dataStore.data.map {
            it[JWT_TOKEN_KEY]
        }.firstOrNull()

    override suspend fun loadRefreshToken(): String? =
        dataStore.data.map {
            it[REFRESH_TOKEN_KEY]
        }.firstOrNull()

    override suspend fun deleteTokens() {
        dataStore.edit {
            it.remove(JWT_TOKEN_KEY)
        }
    }

    // Implementasi UserHandler
    override suspend fun saveUserName(userName: String) {
        dataStore.edit {
            it[USER_NAME_KEY] = userName
        }
    }

    override suspend fun loadUserName(): String? =
        dataStore.data.map {
            it[USER_NAME_KEY]
        }.firstOrNull()

    override suspend fun saveUserEmail(userEmail: String) {
        dataStore.edit {
            it[USER_EMAIL_KEY] = userEmail
        }
    }

    override suspend fun loadUserEmail(): String? =
        dataStore.data.map {
            it[USER_EMAIL_KEY]
        }.firstOrNull()

    override suspend fun saveUserPhoneNumber(userPhoneNumber: String) {
        dataStore.edit {
            it[USER_PHONE_NUMBER_KEY] = userPhoneNumber
        }
    }

    override suspend fun loadUserPhoneNumber(): String? =
        dataStore.data.map {
            it[USER_PHONE_NUMBER_KEY]
        }.firstOrNull()

    override suspend fun saveAccountNumber(accountNumber: String) {
        dataStore.edit {
            it[ACCOUNT_NUMBER_KEY] = accountNumber
        }
    }

    override suspend fun loadAccountNumber(): String? =
        dataStore.data.map {
            it[ACCOUNT_NUMBER_KEY]
        }.firstOrNull()

    override suspend fun saveAccountPin(accountPin: String) {
        dataStore.edit {
            it[ACCOUNT_PIN_KEY] = accountPin
        }
    }

    override suspend fun loadAccountPin(): String? =
        dataStore.data.map {
            it[ACCOUNT_PIN_KEY]
        }.firstOrNull()

    override suspend fun saveDateOfBirth(dateOfBirth: String) {
        dataStore.edit {
            it[DATE_OF_BIRTH_KEY] = dateOfBirth
        }
    }

    override suspend fun loadDateOfBirth(): String? =
        dataStore.data.map {
            it[DATE_OF_BIRTH_KEY]
        }.firstOrNull()

    override suspend fun saveKtpNumber(ktpNumber: String) {
        dataStore.edit {
            it[KTP_NUMBER_KEY] = ktpNumber
        }
    }

    override suspend fun loadKtpNumber(): String? =
        dataStore.data.map {
            it[KTP_NUMBER_KEY]
        }.firstOrNull()

    override suspend fun saveKtpPhoto(ktpPhoto: String) {
        dataStore.edit {
            it[KTP_PHOTO_KEY] = ktpPhoto
        }
    }

    override suspend fun loadKtpPhoto(): String? =
        dataStore.data.map {
            it[KTP_PHOTO_KEY]
        }.firstOrNull()

    override suspend fun deleteUserData() {
        dataStore.edit {
            it.remove(USER_NAME_KEY)
            it.remove(USER_EMAIL_KEY)
            it.remove(USER_PHONE_NUMBER_KEY)
            it.remove(ACCOUNT_NUMBER_KEY)
            it.remove(ACCOUNT_PIN_KEY)
            it.remove(DATE_OF_BIRTH_KEY)
            it.remove(KTP_NUMBER_KEY)
            it.remove(KTP_PHOTO_KEY)
        }
    }
}