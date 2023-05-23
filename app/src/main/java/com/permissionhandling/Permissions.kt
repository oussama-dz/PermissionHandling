package com.permissionhandling

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun Permissions() {

    val activity = LocalContext.current as Activity

    val permissionDialog = remember {
        mutableStateListOf<NeededPermission>()
    }

    val microphonePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted)
                permissionDialog.add(NeededPermission.RECORD_AUDIO)
        }
    )

    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissions.entries.forEach { entry ->
                if (!entry.value)
                    permissionDialog.add(getNeededPermission(entry.key))
            }
        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterVertically
        )
    ) {
        Button(
            onClick = {
                microphonePermissionLauncher.launch(NeededPermission.RECORD_AUDIO.permission)
            }
        ) {
            Text(text = "Request microphone Permission")
        }

        Button(
            onClick = {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        NeededPermission.COARSE_LOCATION.permission,
                        NeededPermission.READ_CALENDAR.permission,
                        NeededPermission.READ_CONTACTS.permission
                    )
                )
            }
        ) {
            Text(text = "Request multiple Permissions")
        }

    }

    permissionDialog.forEach { permission ->
        PermissionAlertDialog(
            neededPermission = permission,
            onDismiss = { permissionDialog.remove(permission) },
            onOkClick = {
                permissionDialog.remove(permission)
                multiplePermissionLauncher.launch(arrayOf(permission.permission))
            },
            onGoToAppSettingsClick = {
                permissionDialog.remove(permission)
                activity.goToAppSetting()
            },
            isPermissionDeclined = !activity.shouldShowRequestPermissionRationale(permission.permission)
        )
    }
}


fun Activity.goToAppSetting() {
    val i =Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(i)
}