package com.example.statussaver

import android.app.ActivityManager
import android.app.AppOpsManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.statussaver.ui.components.MainScreen
import com.example.statussaver.ui.theme.StatusSaverTheme
import com.example.statussaver.utilz.Common
import com.example.statussaver.utilz.Constants.MANAGE_EXTERNAL_STORAGE_PERMISSION
import com.example.statussaver.utilz.Constants.PERMISSIONS
import com.example.statussaver.utilz.Constants.REQUEST_PERMISSIONS
import com.example.statussaver.viewmodel.MainViewModel
import java.io.File
import java.util.*


//
class MainActivity : ComponentActivity() {
    //
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatusSaverTheme {
                // Enable edge-to-edge experience and ProvideWindowInsets to the composable
                WindowCompat.setDecorFitsSystemWindows(window, false)
                val mainViewModel: MainViewModel = viewModel()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(mainViewModel = mainViewModel)
                    //  getPermission()
                }
            }
        }
    }

/*
@RequiresApi(Build.VERSION_CODES.Q)
fun getPermission() {
val storageManager = application.getSystemService(Context.STORAGE_SERVICE) as StorageManager
val intent = storageManager.primaryStorageVolume.createOpenDocumentTreeIntent()
val targetDirectory = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
var uri = intent.getParcelableExtra<Uri>("android.provider.extra.INITIAL_URI") as Uri
var scheme = uri.toString()
scheme = scheme.replace("/root/", "/tree/")
scheme += "%3A$targetDirectory"
uri = Uri.parse(scheme)
intent.putExtra("android.provider.extra.INITIAL_URI", uri)
intent.putExtra("android.content.extra.SHOW_ADVANCE", true)
startActivityForResult(intent, 1237)
}
*/


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
        for (permissions in PERMISSIONS) {
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
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS)
            return
        }
        Common.APP_DIR = Environment.getExternalStorageDirectory().path +
                File.separator + "StatusSaver"
    }


/*
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
var treedata = ""
super.onActivityResult(requestCode, resultCode, data)
if (requestCode == 1237 && resultCode == RESULT_OK) {
val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
Intent.FLAG_GRANT_WRITE_URI_PERMISSION

val treeUri = data?.data
treedata = treeUri.toString()
Log.d("whatsapp", "$treeUri::: $treedata  ")

if (treeUri != null) {
contentResolver.takePersistableUriPermission(
treeUri,
takeFlags
)
val fileDoc = DocumentFile.fromTreeUri(applicationContext, treeUri)
if (fileDoc != null) {
Log.d("whatsapp", "${fileDoc.listFiles()} ")
}
if (fileDoc != null) {
for (file in fileDoc.listFiles()){
Log.d("statusssss","$file")
}


}
}


}
/ *

val treeUri = data?.data?.also { uri ->

contentResolver.takePersistableUriPermission(
uri,
takeFlags
)

val contentResolver = applicationContext.contentResolver

fun dumpImageMetaData(uri: Uri) {

// The query, because it only applies to a single document, returns only
// one row. There's no need to filter, sort, or select fields,
// because we want all fields for one document.
val cursor: Cursor? = contentResolver.query(
uri, null, null, null, null, null
)

cursor?.use {
// moveToFirst() returns false if the cursor has 0 rows. Very handy for
// "if there's anything to look at, look at it" conditionals.
if (it.moveToFirst()) {

// Note it's called "Display Name". This is
// provider-specific, and might not necessarily be the file name.
val displayName: String =
it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
// Log.i(TAG, "Display Name: $displayName")

val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
// If the size is unknown, the value stored is null. But because an
// int can't be null, the behavior is implementation-specific,
// and unpredictable. So as
// a rule, check if it's null before assigning to an int. This will
// happen often: The storage API allows for remote files, whose
// size might not be locally known.
val size: String = if (!it.isNull(sizeIndex)) {
// Technically the column stores an int, but cursor.getString()
// will do the conversion automatically.
it.getString(sizeIndex)


} else {
"Unknown"
}
Log.d("whatsapp", "$displayName$size")
// Log.i(TAG, "Size: $size")
}
}
}
}
* /
}
*/
}





