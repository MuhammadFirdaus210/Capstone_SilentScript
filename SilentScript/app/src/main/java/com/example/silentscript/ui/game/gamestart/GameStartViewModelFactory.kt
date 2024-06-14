package com.example.silentscript.ui.game.gamestart

import CameraAndFileHandler
import GameStartViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameStartViewModelFactory(private val cameraAndFileHandler: CameraAndFileHandler) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(GameStartViewModel::class.java)) {
            return GameStartViewModel(cameraAndFileHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}