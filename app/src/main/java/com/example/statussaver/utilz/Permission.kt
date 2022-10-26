package com.example.statussaver.utilz

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState


/**
 * Composable helper for permission checking
 *
 * onDenied contains lambda for request permission
 *
 * @param permission permission for request
 * @param onGranted composable for [PackageManager.PERMISSION_GRANTED]
 * @param onDenied composable for [PackageManager.PERMISSION_DENIED]
 */


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permissions(
    multiplePermissionsState: MultiplePermissionsState,
    context: Context,
    rationaleText: String,
    content: @Composable () -> Unit,

    ) {
    // Track if the user doesn't want to see the rationale any more.
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    when {
        // If all permissions are granted, then show screen with the feature enabled
        multiplePermissionsState.allPermissionsGranted -> {
            //content to display when permission is granted
            content()
        }
        // If the user denied any permission but a rationale should be shown, or the user sees
        // the permissions for the first time, explain why the feature is needed by the app and
        // allow the user decide if they don't want to see the rationale any more.
        multiplePermissionsState.shouldShowRationale || !multiplePermissionsState.permissionRequested -> {
            if (doNotShowRationale) {
                DeniedText(rationaleText = rationaleText, context = context)
            } else {
                PermissionDialog(text = "Please grant the requested permission",
                    dismiss = { doNotShowRationale = true },
                    onRequestPermission = { multiplePermissionsState.launchMultiplePermissionRequest() })
            }
        }
        // If the criteria above hasn't been met, the user denied some permission. Let's present
        // the user with a FAQ in case they want to know more and send them to the Settings screen
        // to enable them the future there if they want to.
        else -> {
            DeniedText(rationaleText = rationaleText, context = context)
        }
    }
}

@Composable
fun PermissionDialog(
    text: String, onRequestPermission: () -> Unit, dismiss: () -> Unit
) {
    AlertDialog(onDismissRequest = { dismiss() }, title = {
        Text(
            text = "Permission Request", fontSize = 18.sp, fontWeight = FontWeight.SemiBold
        )
    }, text = {
        Text(
            text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold
        )
    }, confirmButton = {
        Button(onClick = onRequestPermission) {
            Text("Grant")
        }
    })
}

//denied message
@Composable
fun DeniedText(
    rationaleText: String, context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Camera and Location permissions are denied.$rationaleText"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            })
        }) {
            Text("Open Settings")
        }
    }
}
