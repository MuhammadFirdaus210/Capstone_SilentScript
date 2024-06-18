package com.example.silentscript

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.mystoryapp.data.preference.UserPreferences
import com.example.silentscript.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnRegis: TextView
    private lateinit var btnLogin: Button
    private lateinit var progressDialog: ProgressDialog

    private var firebaseAuth = FirebaseAuth.getInstance()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        showToken()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login)
        btnRegis = findViewById(R.id.register)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("Please wait...")

        btnLogin.setOnClickListener {
            if (email.text.isNotEmpty() && password.text.isNotEmpty()){
                processLogin()
            }else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
        btnRegis.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun processLogin(){
        val email = email.text.toString()
        val password = password.text.toString()

        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val firebaseUser = authResult.user
                firebaseUser?.getIdToken(true)?.addOnSuccessListener { idTokenResult ->
                    val token = idTokenResult.token
                    // Here you have the token, you can now store it
                    // For example, using your UserPreferences class
                    val userPreferences = UserPreferences.getInstance(dataStore)
                    lifecycleScope.launch {
                        userPreferences.saveToken(token!!)
                    }
                }
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener{
                progressDialog.dismiss()
            }
    }
    private fun showToken() {
        val userPreferences = UserPreferences.getInstance(dataStore)
        lifecycleScope.launch {
            val uid = userPreferences.getUid().first()
            Toast.makeText(this@LoginActivity, "UID: $uid", Toast.LENGTH_LONG).show()
        }
    }
}