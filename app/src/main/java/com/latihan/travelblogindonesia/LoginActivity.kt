package com.latihan.travelblogindonesia

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            val lgnBtn = findViewById<Button>(R.id.loginButton)
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)

            val preferences = BlogPreferences(this)
            if (preferences.isLoggedIn) {
                startMainActivity()
                finish()
                return
            }

            lgnBtn.setOnClickListener{
                onKlikLogin(lgnBtn,progressBar,preferences)

            }
        }


    private fun onKlikLogin(lgnBtn: Button, progressBar: ProgressBar, preferences: BlogPreferences) {
        val username: EditText? = findViewById<TextInputLayout>(R.id.textUsernameLayout).editText
        val password: EditText? = findViewById<TextInputLayout>(R.id.textPasswordInput).editText
        if(username?.text.toString() != "admin" && password?.text.toString() != "admin"){
            showErrorDialog()
        } else {
            username?.isEnabled = false
            password?.isEnabled = false
            preferences.isLoggedIn = true
            performLogin(lgnBtn, progressBar)

        }
    }


    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("Login Gagal")
            .setMessage("Username atau password salah.")
            .setPositiveButton("YA") { dialog, which -> dialog.dismiss() }
            .show()
    }

    private fun performLogin(lgnBtn: Button, progressBar: ProgressBar) {
        lgnBtn.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startMainActivity()
            finish()
        }, 3000)
    }

    private fun startMainActivity(){
        val pindahIntent= Intent(this,MainActivity::class.java)
        startActivity(pindahIntent)
    }
}