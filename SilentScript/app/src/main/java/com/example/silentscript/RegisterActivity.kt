package com.example.silentscript

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.silentscript.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmation: EditText
    private lateinit var btnRegis: Button
    private lateinit var progressDialog: ProgressDialog


    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirmation = findViewById(R.id.confirmation)
        btnRegis = findViewById(R.id.register_register)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("Please wait...")

        btnRegis.setOnClickListener {
            if (username.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty()){
                if(password.text.toString() == confirmation.text.toString()){
                    processRegister()
                }else{
                    Toast.makeText(this, "Confirm", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun processRegister() {
        val username = username.text.toString()
        val email = email.text.toString()
        val password = password.text.toString()

        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = username
                    }
                    val user = task.result?.user
                    user?.updateProfile(userUpdateProfile)
                        ?.addOnCompleteListener {
                            progressDialog.dismiss()
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        ?.addOnFailureListener { error ->
                            progressDialog.dismiss()
                            Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { error ->
                progressDialog.dismiss()
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}