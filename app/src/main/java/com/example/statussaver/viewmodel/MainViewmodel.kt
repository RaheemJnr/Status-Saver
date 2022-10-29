package com.example.statussaver.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statussaver.model.Status
import com.example.statussaver.utilz.Constants.BUSINESS_STATUS_DIRECTORY
import com.example.statussaver.utilz.Constants.BUSINESS_STATUS_DIRECTORY_NEW
import com.example.statussaver.utilz.Constants.WHATSAPP_STATUS_DIRECTORY
import kotlinx.coroutines.launch
import java.io.File
import java.util.*


class MainViewModel() : ViewModel() {

    private val _status = MutableLiveData<List<Status>>()
    val status: LiveData<List<Status>> get() = _status


    fun getWABusinessStatus() {
        if (BUSINESS_STATUS_DIRECTORY.exists()) {
            Log.d("WhatsApp", "Folder exist")
            execute(BUSINESS_STATUS_DIRECTORY)
        } else if (BUSINESS_STATUS_DIRECTORY_NEW.exists()) {
            Log.d("WhatsApp", "new Folder exist")
            execute(BUSINESS_STATUS_DIRECTORY_NEW)
        }
    }

    fun getWhatsappStatus() {
        if (WHATSAPP_STATUS_DIRECTORY.exists()) {
            Log.d("WhatsApp", "Folder exist")
            execute(WHATSAPP_STATUS_DIRECTORY)
        } else if (WHATSAPP_STATUS_DIRECTORY.exists()) {
            Log.d("WhatsApp", "new Folder exist")
            execute(WHATSAPP_STATUS_DIRECTORY)
        }
    }

    private fun execute(wAFolder: File) {
        viewModelScope.launch {
            val statusFiles: Array<File>? = wAFolder.listFiles()
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                Arrays.sort(statusFiles)
                for (file in statusFiles) {
                    val status = Status(file, file.name, file.absolutePath)
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        _status.postValue(listOf(status))
                    }
                }
            }
        }
    }


}