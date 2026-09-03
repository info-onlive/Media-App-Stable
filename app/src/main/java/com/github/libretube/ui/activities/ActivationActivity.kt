package com.github.libretube.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.github.libretube.api.RetrofitInstance
import com.github.libretube.databinding.ActivityActivationBinding
import com.github.libretube.ui.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ActivationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityActivationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activateButton.setOnClickListener {
            val code = binding.activationCode.text?.toString()?.trim().orEmpty()

            if (code.isBlank()) {
                Snackbar.make(
                    binding.root,
                    "Ingresa un código de activación",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            binding.activateButton.isEnabled = false

            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.activationApi.checkCode(code)

                    if (response.ok) {
                        getSharedPreferences("activation", MODE_PRIVATE)
                            .edit()
                            .putBoolean("is_activated", true)
                            .putString("activation_code", code)
                            .putString("expires_at", response.expiresAt)
                            .apply()

                        startActivity(Intent(this@ActivationActivity, MainActivity::class.java))
                        finish()
                    } else {
                        val message = when (response.reason) {
                            "expired" -> "Código expirado"
                            "not_found" -> "Código inválido o bloqueado"
                            "missing_code" -> "Ingresa un código de activación"
                            else -> "No se pudo validar el código"
                        }

                        Snackbar.make(
                            binding.root,
                            message,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Snackbar.make(
                        binding.root,
                        "Error de conexión. Intenta nuevamente.",
                        Snackbar.LENGTH_LONG
                    ).show()
                } finally {
                    binding.activateButton.isEnabled = true
                }
            }
        }
    }
}
