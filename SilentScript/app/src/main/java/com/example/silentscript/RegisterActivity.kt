package com.example.silentscript

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.silentscript.databinding.ActivityRegisterBinding
import com.example.silentscript.ui.register.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var progressDialog: ProgressDialog

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Register")
        progressDialog.setMessage("Please wait...")

        binding.login.setOnClickListener(){
            finish()
        }

        binding.registerRegister.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val passwordConfirmation = binding.confirmation.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                if(password == passwordConfirmation){
                    processRegister(username, email, password, passwordConfirmation)
                }else{
                    Toast.makeText(this, "Confirm", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun processRegister(username: String, email: String, password: String, passwordConfirmation: String) {
        progressDialog.show()
        registerViewModel.registerUser(username, email, password, passwordConfirmation ) { registerResponse ->
            progressDialog.dismiss()
            if (registerResponse != null) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}