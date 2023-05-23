package com.permissionhandling

enum class NeededPermission(
    val permission: String,
    val title: String,
    val description: String,
    val permanentlyDeniedDescription: String,
) {
    COARSE_LOCATION(
        permission = android.Manifest.permission.ACCESS_COARSE_LOCATION,
        title = "Approximate Location Permission",
        description = "This permission is needed to get your approximate location. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to get your approximate location. Please grant the permission in app settings.",
    ),

    READ_CALENDAR(
        permission = android.Manifest.permission.READ_CALENDAR,
        title = "Read Calendar Permission",
        description = "This permission is needed to read your calendar. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to read your calendar. Please grant the permission in app settings.",
    ),

    READ_CONTACTS(
        permission = android.Manifest.permission.READ_CONTACTS,
        title = "Read Contacts Permission",
        description = "This permission is needed to read your contacts. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to read your contacts. Please grant the permission in app settings.",
    ),

    RECORD_AUDIO(
        permission = android.Manifest.permission.RECORD_AUDIO,
        title = "Record Audio permission",
        description = "This permission is needed to access your microphone. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to access your microphone. Please grant the permission in app settings.",
    );

    fun permissionTextProvider(isPermanentDenied: Boolean): String {
        return if (isPermanentDenied) this.permanentlyDeniedDescription else this.description
    }
}

fun getNeededPermission(permission: String): NeededPermission {
    return NeededPermission.values().find { it.permission == permission } ?: throw IllegalArgumentException("Permission $permission is not supported")
}