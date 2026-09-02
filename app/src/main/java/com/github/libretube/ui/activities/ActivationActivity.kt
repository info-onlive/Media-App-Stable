package com.github.libretube.ui.activities

import android.content.Intent
import android.os.Bundle
import com.github.libretube.databinding.ActivityActivationBinding
import com.github.libretube.ui.base.BaseActivity
import com.google.android.material.snackbar.Snackbar

class ActivationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityActivationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activateButton.setOnClickListener {
            val code = binding.activationCode.text?.toString()?.trim().orEmpty()

            if (code == "YP-TEST-2026") {
                getSharedPreferences("activation", MODE_PRIVATE)
                    .edit()
                    .putBoolean("is_activated", true)
                    .apply()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Snackbar.make(
                    binding.root,
                    "Código de activación inválido",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}
