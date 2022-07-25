package com.mkavaktech.readingtrackers.view.auth.login.model

data class CustomUserModel(
    val id: String?,
    val userId: String,
    val displayName: String,
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf("user_id" to userId, "display_name" to displayName)
    }
}
