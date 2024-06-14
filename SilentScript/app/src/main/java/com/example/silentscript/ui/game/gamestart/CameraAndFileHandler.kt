import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log

class CameraAndFileHandler(private val activity: Activity) {

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_PICK = 2
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    var imageUri: Uri? = null

    fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        activity.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Uri? {
        Log.d("CameraAndFileHandler", "handleActivityResult called with requestCode $requestCode, resultCode $resultCode, data $data")

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Log.d("CameraAndFileHandler", "Image capture completed successfully, imageUri: $imageUri")
            return imageUri
        }

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            Log.d("CameraAndFileHandler", "Image pick completed successfully, imageUri: $imageUri")
            return imageUri
        }

        Log.d("CameraAndFileHandler", "No action was taken, returning null")
        return null
    }
}
