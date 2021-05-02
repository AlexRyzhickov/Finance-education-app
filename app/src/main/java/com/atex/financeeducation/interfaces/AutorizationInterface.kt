package com.atex.financeeducation.interfaces

interface AutorizationInterface {
    fun signUp(email: String, password: String, nickname: String)
    fun signIn(email: String, password: String)
    fun signOut()
    fun hideBottomNavView()
}