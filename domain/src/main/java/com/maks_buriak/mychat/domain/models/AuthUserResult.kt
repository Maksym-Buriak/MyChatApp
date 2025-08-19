package com.maks_buriak.mychat.domain.models

data class AuthUserResult (
    val user: User,
    val isNewUser: Boolean
)