package com.example.silentscript.ui.game.gamestart

import CameraAndFileHandler
import GameStartViewModel
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.silentscript.databinding.ActivityStartGameBinding
import com.example.silentscript.ui.game.gamestart.ImageFileUtils.compressImageFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.ExecutorService

class StartGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartGameBinding
    private lateinit var cameraExecutor: ExecutorService
    private var imageUri: Uri? = null
    private var imageFilePath: String? = null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var cameraAndFileHandler: CameraAndFileHandler
    private lateinit var gameStartViewModel: GameStartViewModel
    private lateinit var uid : String

    companion object {
        private const val TAG = "StartGameActivity"
        private const val KEY_IMAGE_URI = "imageUri"
        private const val KEY_IMAGE_FILE_PATH = "imageFilePath"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val levelId: Int = intent.getIntExtra("levelId", 0)
        binding.id.text = levelId.toString()

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
                val token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRmOGIxNTFiY2Q5MGQ1YjMwMjBlNTNhMzYyZTRiMzA3NTYzMzdhNjEiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiaG9yZSIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9zbGllbnRzY3JpcHQiLCJhdWQiOiJzbGllbnRzY3JpcHQiLCJhdXRoX3RpbWUiOjE3MTgzODM5OTQsInVzZXJfaWQiOiJ2dVFyaGlnQVBNYVZ4enRhdmFmQmM2bnAzVkozIiwic3ViIjoidnVRcmhpZ0FQTWFWeHp0YXZhZkJjNm5wM1ZKMyIsImlhdCI6MTcxODM4Mzk5NCwiZXhwIjoxNzE4Mzg3NTk0LCJlbWFpbCI6ImhvcmVAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbImhvcmVAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.gn08kF62NZP0098wUhbiRPVrioNhuSGvZi7KT_U6y-gkEfnPBEUUYjLbmEVdKxiISKnISld4DaUtqDDzTUjr9VRaVcrKQBN87EojPa56dFrS4A8AcZDVH-CiLELPtUYUQAS41vgB4BcpLNXtwyj4CsoDg7WtcDfuBnwz2mego9TLT30u8z4sbT05CIgLW2w9VEjA83Xdsc6-qOGwvoyEMPP_1lWTMt0v-3yRJbV_RZli4DpQK191XiYWX3BJE3gnbX_0C1o6kgkhfr7K7Ho2lWRxATsvHdAZN0nTlqj_oSuICKSI89Av5SypU96jHRzgNii8EhUUh11_-_hJX34USQ"
                val level = levelId.toString()

                try {
                    gameStartViewModel.postGame(token, imagePart, level)
                } catch (e: Exception) {

                    Log.e(TAG, "Exception when calling postGame", e)
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
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })

        gameStartViewModel.uploadResponse.observe(this, Observer { response ->
            Log.d(TAG, "UploadResponse LiveData updated with: ${response.message}")
            Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
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
                    Toast.makeText(this, "Failed to decode image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Image file does not exist", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Failed to get image file", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_IMAGE_URI, imageUri)
        outState.putString(KEY_IMAGE_FILE_PATH, imageFilePath)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}






