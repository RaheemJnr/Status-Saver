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
import com.example.statussaver.utilz.Constants.WHATSAPP_STATUS_DIRECTORY_NEW
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.*


class MainViewModel() : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        // This doesn't handle multiple 'refreshing' tasks, don't use this
        viewModelScope.launch {
            // A fake 2 second 'refresh'
            _isRefreshing.emit(true)
            delay(2000)
            _isRefreshing.emit(false)
        }
    }

    private val _waBusinessImageStatus = MutableLiveData<List<Status>>()
    val waBusinessImageStatus: LiveData<List<Status>> get() = _waBusinessImageStatus

    private val _waBusinessVideoStatus = MutableLiveData<List<Status>>()
    val waBusinessVideoStatus: LiveData<List<Status>> get() = _waBusinessVideoStatus

    //
    private val _whatsappImageStatus = MutableLiveData<List<Status>>()
    val whatsappImageStatus: LiveData<List<Status>> get() = _whatsappImageStatus

    private val _whatsappVideoStatus = MutableLiveData<List<Status>>()
    val whatsappVideoStatus: LiveData<List<Status>> get() = _whatsappVideoStatus


    fun getWABusinessStatus() {
        if (BUSINESS_STATUS_DIRECTORY.exists()) {
            executeForImage(BUSINESS_STATUS_DIRECTORY)
        } else if (BUSINESS_STATUS_DIRECTORY_NEW.exists()) {
            executeForImage(BUSINESS_STATUS_DIRECTORY_NEW)
        }
    }
    fun getWABusinessStatusVideo() {
        if (BUSINESS_STATUS_DIRECTORY.exists()) {
            executeForVideo(BUSINESS_STATUS_DIRECTORY)
        } else if (BUSINESS_STATUS_DIRECTORY_NEW.exists()) {
            executeForVideo(BUSINESS_STATUS_DIRECTORY_NEW)
        }
    }

    fun getWhatsappStatus() {
        if (WHATSAPP_STATUS_DIRECTORY.exists()) {
            Log.d("WhatsApp", "Folder exist")
            executeForImage(WHATSAPP_STATUS_DIRECTORY)
        } else if (WHATSAPP_STATUS_DIRECTORY.exists()) {
            Log.d("WhatsApp", "new Folder exist")
            executeForImage(WHATSAPP_STATUS_DIRECTORY)
        }
    }




    fun getWhatsappStatusVideo() {
        if (WHATSAPP_STATUS_DIRECTORY.exists()) {
            executeForVideo(WHATSAPP_STATUS_DIRECTORY)
        } else if (WHATSAPP_STATUS_DIRECTORY_NEW.exists()) {
            executeForVideo(WHATSAPP_STATUS_DIRECTORY_NEW)
        }
    }


    private fun executeForImage(wAFolder: File) {
        val imageStatus = arrayListOf<Status>()
        viewModelScope.launch {
            val statusFiles: Array<File>? = wAFolder.listFiles()
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                Arrays.sort(statusFiles)
                for (file in statusFiles) {
                    val status = Status(file = file, title = file.name, path = file.absolutePath)
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        imageStatus.add(status)
                        _waBusinessImageStatus.postValue(imageStatus)
                        _whatsappImageStatus.postValue(imageStatus)
                    }
                }
            }
        }

    }

    private fun executeForVideo(waFolder: File) {
        val videoStatus = arrayListOf<Status>()
        viewModelScope.launch {
            val statusFiles = waFolder.listFiles()
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                Arrays.sort(statusFiles)
                for (file in statusFiles) {
                    val status = Status(file, file.name, file.absolutePath)
                    if (status.isVideo) {
                        videoStatus.add(status)
                        _waBusinessVideoStatus.postValue(videoStatus)
                        _whatsappVideoStatus.postValue(videoStatus)
                    }
                }
            }
        }
    }

}