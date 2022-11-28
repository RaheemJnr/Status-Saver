package com.example.statussaver.ui.screen.state

import com.example.statussaver.model.Status

data class UIState(
    val status: ArrayList<Status> = arrayListOf(),
    val errorMessage: String? = null
)