package com.example.statussaver

import android.Manifest
import android.app.ActivityManager
import android.app.AppOpsManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.statussaver.model.Status
import com.example.statussaver.ui.theme.StatusSaverTheme
import com.example.statussaver.utilz.Common
import java.io.File
import java.util.*


private const val REQUEST_PERMISSIONS = 1234
private val PERMISSION = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

private const val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"

//
class MainActivity : ComponentActivity() {

    private val imagesList: ArrayList<Status> = arrayListOf()
    var items = ""
    private val handler = Handler()

    //
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatusSaverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                }
            }
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS && grantResults.isNotEmpty()) {
            if (arePermissionDenied()) {
                (Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE)) as ActivityManager).clearApplicationUserData()
                recreate()
            }
        }
    }

    private fun arePermissionDenied(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return checkStorageApi30()
        }
        for (permissions in PERMISSION) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    permissions
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
        }
        return false
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    fun checkStorageApi30(): Boolean {
        val appOps = applicationContext.getSystemService(
            AppOpsManager::class.java
        )
        val mode = appOps.unsafeCheckOpNoThrow(
            MANAGE_EXTERNAL_STORAGE_PERMISSION,
            applicationContext.applicationInfo.uid,
            applicationContext.packageName
        )
        return mode != AppOpsManager.MODE_ALLOWED
    }

    override fun onResume() {
        super.onResume()
        if (arePermissionDenied()) {
            requestPermissions(PERMISSION, REQUEST_PERMISSIONS)
            return
        }
        Common.APP_DIR = Environment.getExternalStorageDirectory().path +
                File.separator + "StatusSaver"
    }

}



