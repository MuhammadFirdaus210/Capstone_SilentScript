package com.example.silentscript.ui.game.gamestart

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object ImageFileUtils {

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return if (filePath != null) File(filePath) else null
    }

    @SuppressLint("Range")
    fun getFileFromContentUri(context: Context, contentUri: Uri): File? {
        val filePath: String?
        val cursor: Cursor? = context.contentResolver.query(contentUri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        filePath = if (cursor != null) {
            cursor.moveToFirst()
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        } else {
            contentUri.path
        }
        cursor?.close()
        return if (filePath != null) File(filePath) else null
    }

    fun saveBitmapToFile(bitmap: Bitmap, file: File) {
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
    }

    fun getBitmapFromFile(file: File): Bitmap? {
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }

    fun getUriFromFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    fun compressImageFile(file: File, context: Context): File? {
        // Create a bitmap from the file
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)

        // Check if bitmap is null
        if (bitmap == null) {
            Log.e("ImageFileUtils", "Failed to decode file to bitmap: ${file.absolutePath}")
            return null
        }

        // Compress the bitmap to a JPEG with 80% quality
        val outStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream)
        val compressedBitmapData = outStream.toByteArray()

        // Create a new file for the compressed bitmap
        val compressedFile = ImageFileUtils.createImageFile(context)

        // Write the compressed bitmap data to the new file
        val fos = FileOutputStream(compressedFile)
        fos.write(compressedBitmapData)
        fos.close()

        return compressedFile
    }

}
