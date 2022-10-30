package com.example.statussaver.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statussaver.model.Status
import com.example.statussaver.utilz.Constants.BUSINESS_STATUS_DIRECTORY
import com.example.statussaver.utilz.Constants.BUSINESS_STATUS_DIRECTORY_NEW
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
//    private val _whatsappImageStatus = MutableLiveData<List<Status>>()
//    val whatsappImageStatus: LiveData<List<Status>> get() = _whatsappImageStatus
//
//    private val _whatsappVideoStatus = MutableLiveData<List<Status>>()
//    val whatsappVideoStatus: LiveData<List<Status>> get() = _whatsappVideoStatus


    fun getWABusinessStatusImage() {
        if (BUSINESS_STATUS_DIRECTORY.exists()) {
            executeForBusinessImage(BUSINESS_STATUS_DIRECTORY)
        } else if (BUSINESS_STATUS_DIRECTORY_NEW.exists()) {
            executeForBusinessImage(BUSINESS_STATUS_DIRECTORY_NEW)
        }
    }

    fun getWABusinessStatusVideo() {
        if (BUSINESS_STATUS_DIRECTORY.exists()) {
            executeForBusinessVideo(BUSINESS_STATUS_DIRECTORY)
        } else if (BUSINESS_STATUS_DIRECTORY_NEW.exists()) {
            executeForBusinessVideo(BUSINESS_STATUS_DIRECTORY_NEW)
        }
    }

//    fun getWhatsappStatus() {
//        if (WHATSAPP_STATUS_DIRECTORY.exists()) {
//            Log.d("WhatsApp", "Folder exist")
//            executeForWhatsappImage(WHATSAPP_STATUS_DIRECTORY)
//        } else if (WHATSAPP_STATUS_DIRECTORY.exists()) {
//            Log.d("WhatsApp", "new Folder exist")
//            executeForWhatsappImage(WHATSAPP_STATUS_DIRECTORY)
//        }
//    }


//    fun getWhatsappStatusVideo() {
//        if (WHATSAPP_STATUS_DIRECTORY.exists()) {
//            Log.d("WhatsApp", "Folder exist")
//            executeForWhatsappVideo(WHATSAPP_STATUS_DIRECTORY)
//        } else if (WHATSAPP_STATUS_DIRECTORY_NEW.exists()) {
//            Log.d("WhatsApp", "new Folder exist")
//            executeForWhatsappVideo(WHATSAPP_STATUS_DIRECTORY_NEW)
//        }
//    }


    private fun executeForBusinessImage(wAFolder: File) {
        val businessImageStatus = arrayListOf<Status>()
        viewModelScope.launch {
            val statusFiles: Array<File>? = wAFolder.listFiles()
            businessImageStatus.clear()
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                Arrays.sort(statusFiles)
                for (file in statusFiles) {
                    val status = Status(file = file, title = file.name, path = file.absolutePath)
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        businessImageStatus.add(status)
                        _waBusinessImageStatus.postValue(businessImageStatus)
                    }
                }
            }
        }

    }
    private fun executeForBusinessVideo(waFolder: File) {
        val businessVideoStatus = arrayListOf<Status>()
        viewModelScope.launch {
            val statusFiles = waFolder.listFiles()
            businessVideoStatus.clear()
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                Arrays.sort(statusFiles)
                for (file in statusFiles) {
                    val status = Status(file, file.name, file.absolutePath)
                    if (status.isVideo) {
                        businessVideoStatus.add(status)
                        _waBusinessVideoStatus.postValue(businessVideoStatus)
                    }
                }
            }
        }
    }

//    private fun executeForWhatsappImage(wAFolder: File) {
//        val whatsappImageStatus = arrayListOf<Status>()
//        viewModelScope.launch {
//            val statusFiles: Array<File>? = wAFolder.listFiles()
//            if (statusFiles != null && statusFiles.isNotEmpty()) {
//                Arrays.sort(statusFiles)
//                for (file in statusFiles) {
//                    val status = Status(file = file, title = file.name, path = file.absolutePath)
//                    if (!status.isVideo && status.title.endsWith(".jpg")) {
//                        whatsappImageStatus.add(status)
//                        _whatsappImageStatus.postValue(whatsappImageStatus)
//                    }
//                }
//            }
//        }
//
//    }
//    private fun executeForWhatsappVideo(waFolder: File) {
//        val whatsappVideoStatus = arrayListOf<Status>()
//        viewModelScope.launch {
//            val statusFiles = waFolder.listFiles()
//            if (statusFiles != null && statusFiles.isNotEmpty()) {
//                Arrays.sort(statusFiles)
//                for (file in statusFiles) {
//                    val status = Status(file, file.name, file.absolutePath)
//                    if (status.isVideo) {
//                        whatsappVideoStatus.add(status)
//                        _whatsappVideoStatus.postValue(whatsappVideoStatus)
//                    }
//                }
//            }
//        }
//    }

}