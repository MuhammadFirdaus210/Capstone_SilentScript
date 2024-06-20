package com.example.silentscript.ui.game.gamestart

import CameraAndFileHandler
import GameStartViewModel
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mystoryapp.data.preference.UserPreferences
import com.example.silentscript.R
import com.example.silentscript.databinding.ActivityStartGameBinding
import com.example.silentscript.ui.game.GameDetailActivity
import com.example.silentscript.ui.game.gamestart.ImageFileUtils.compressImageFile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class StartGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartGameBinding
    private lateinit var cameraExecutor: ExecutorService
    private var imageUri: Uri? = null
    private var imageFilePath: String? = null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var cameraAndFileHandler: CameraAndFileHandler
    private lateinit var gameStartViewModel: GameStartViewModel


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

    companion object {
        private const val TAG = "StartGameActivity"
        private const val KEY_IMAGE_URI = "imageUri"
        private const val KEY_IMAGE_FILE_PATH = "imageFilePath"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val levelId: Int = intent.getIntExtra("levelId", 0)


        binding.back.setOnClickListener(){
           finish()
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        cameraAndFileHandler = CameraAndFileHandler(this)
        val viewModelFactory = GameStartViewModelFactory(cameraAndFileHandler)
        gameStartViewModel = ViewModelProvider(this, viewModelFactory).get(GameStartViewModel::class.java)

        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(KEY_IMAGE_URI)
            imageFilePath = savedInstanceState.getString(KEY_IMAGE_FILE_PATH)
        }

        if (gameStartViewModel.allPermissionsGranted()) {
            binding.imgCamera.setOnClickListener {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    try {
                        val imageFile = ImageFileUtils.createImageFile(this)
                        imageFilePath = imageFile.absolutePath
                        imageUri = ImageFileUtils.getUriFromFile(this, imageFile)
                        cameraAndFileHandler.imageUri = imageUri // Save imageUri in handler
                        Log.d(TAG, "Image URI: $imageUri")
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                        takePictureIntent.resolveActivity(packageManager)?.also {
                            startActivityForResult(takePictureIntent, CameraAndFileHandler.REQUEST_IMAGE_CAPTURE)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Error creating file: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            requestPermissions(CameraAndFileHandler.REQUIRED_PERMISSIONS, CameraAndFileHandler.REQUEST_IMAGE_CAPTURE)
        }

        progressDialog = ProgressDialog(this).apply {
            setMessage("Uploading...")
            setCancelable(false)
        }

        binding.fileUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, CameraAndFileHandler.REQUEST_IMAGE_PICK)
        }

        binding.upload.setOnClickListener {
            imageUri?.let { uri ->
                progressDialog.show()
                val inputStream = contentResolver.openInputStream(uri)
                val requestFile = inputStream?.readBytes()?.toRequestBody("image/jpg".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestFile!!)

                val userPreferences = UserPreferences.getInstance(dataStore)
                lifecycleScope.launch {
                    val token = userPreferences.getToken().first()
                    val level = levelId.toString()

                    try {
                        gameStartViewModel.postGame(token, imagePart, level)
                    } catch (e: Exception) {
                        Log.e(TAG, "Exception when calling postGame", e)
                    }
                }
            } ?: run {
                Toast.makeText(this, "Image URI is null. Please capture or select an image first.", Toast.LENGTH_SHORT).show()
            }
        }

        gameStartViewModel.isLoading.observe(this, Observer { isLoading ->
            if (!isLoading) {
                progressDialog.dismiss()
            }
        })

        gameStartViewModel.message.observe(this, Observer { message ->
            Log.d(TAG, "Message LiveData updated with: $message")
        })

        gameStartViewModel.uploadResponse.observe(this, Observer { response ->
            Log.d(TAG, "UploadResponse LiveData updated with: ${response.message}")
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialogalert, null)
            val textView = dialogView.findViewById<TextView>(R.id.dialog_text)
            val btnNext = dialogView.findViewById<Button>(R.id.btn_next)
            val btnBack = dialogView.findViewById<Button>(R.id.btn_back)
            textView.text = response.message
            AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton(R.string.lagi, null)
                .show()

            // Tampilkan atau sembunyikan tombol "Next" berdasarkan pesan respons
            if (response.message == "Selamat kamu mendapatkan +100") {
                btnNext.visibility = View.VISIBLE
            } else {
                btnNext.visibility = View.GONE
            }

            btnNext.setOnClickListener {
                val nextLevel = levelId + 1
                val abjad = (1..26).associateWith { it -> ('A' + it - 1).toString() }
                val img = "https://storage.googleapis.com/silentscript/huruf-collection/${abjad[nextLevel]}.jpg"
                val intent = Intent(this, GameDetailActivity::class.java)
                intent.putExtra("levelId", nextLevel)
                intent.putExtra("image", img)
                startActivity(intent)
                Log.d("StartGameActivity", "Image URL passed to GameDetailActivity: $img")
            }

            btnBack.setOnClickListener {
                finish()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraAndFileHandler.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Use the imageUri saved in the handler
            imageUri = cameraAndFileHandler.imageUri
            val imageFile = File(imageUri!!.path!!)
            val compressedImageFile = compressImageFile(imageFile, this)
            if (compressedImageFile != null) {
                // Replace the original image file with the compressed one
                imageUri = ImageFileUtils.getUriFromFile(this, compressedImageFile)
            } else {
                Log.e(TAG, "Failed to compress image file")
            }
        }else {
            imageUri = cameraAndFileHandler.handleActivityResult(requestCode, resultCode, data)
        }
        Log.d(TAG, "onActivityResult imageUri: $imageUri")

        val imageFile = if (requestCode == CameraAndFileHandler.REQUEST_IMAGE_PICK) {
            ImageFileUtils.getFileFromContentUri(this, imageUri!!)
        } else {
            File(imageFilePath!!)
        }

        imageFile?.let { file ->
            if (file.exists()) {
                val imageBitmap = ImageFileUtils.getBitmapFromFile(file)
                imageBitmap?.let {
                    binding.previewView.setImageBitmap(it)
                } ?: run {
                    Toast.makeText(this, "Foto Terlebih Dahulu", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Foto TIdak Ada", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Gagal mendapatkan FIle", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_IMAGE_URI, imageUri)
        outState.putString(KEY_IMAGE_FILE_PATH, imageFilePath)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        cameraExecutor.shutdown()
    }
}






