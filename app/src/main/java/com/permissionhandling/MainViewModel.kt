package com.permissionhandling

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val permissionDialog = mutableStateListOf<NeededPermission>()

    fun dismissDialog() {
        permissionDialog.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
    ){
        if (!isGranted) {
            permissionDialog.add(getNeededPermission(permission))
        }
    }
}