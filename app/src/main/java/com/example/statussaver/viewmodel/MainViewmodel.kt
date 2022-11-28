package com.example.statussaver.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statussaver.model.Status
import com.example.statussaver.model.UIDataState
import com.example.statussaver.ui.screen.state.UIState
import com.example.statussaver.utilz.Common
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

    //whats business image
    private val _waBusinessImageStatus = MutableLiveData<UIDataState<UIState>>()
    val waBusinessImageStatus: LiveData<UIDataState<UIState>> get() = _waBusinessImageStatus

    //whats business video
    private val _waBusinessVideoStatus = MutableLiveData<UIDataState<UIState>>()
    val waBusinessVideoStatus: LiveData<UIDataState<UIState>> get() = _waBusinessVideoStatus

    // whatsapp image
    private val _whatsappImageStatus = MutableLiveData<List<Status>>()
    val whatsappImageStatus: LiveData<List<Status>> get() = _whatsappImageStatus

    /// whatsapp video
    private val _whatsappVideoStatus = MutableLiveData<List<Status>>()
    val whatsappVideoStatus: LiveData<List<Status>> get() = _whatsappVideoStatus

    //saved status and display them in saved
    private val _savedStatus = MutableLiveData<List<Status>>()
    val savedStatus: LiveData<List<Status>> get() = _savedStatus

    //observe ui error
    private val _errorMessageBusiness = MutableLiveData<String>()
    val errorMessageBusiness: LiveData<String> get() = _errorMessageBusiness

    //
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    //
    fun getWABusinessStatusImage() {
        when {
            BUSINESS_STATUS_DIRECTORY.exists() -> {
                executeForBusinessImage(BUSINESS_STATUS_DIRECTORY)
            }
            BUSINESS_STATUS_DIRECTORY_NEW.exists() -> {
                executeForBusinessImage(BUSINESS_STATUS_DIRECTORY_NEW)
            }
            else -> {
                _errorMessageBusiness.postValue("Can't find Whatsapp Business Directory")
            }
        }
    }

    fun getWABusinessStatusVideo() {
        when {
            BUSINESS_STATUS_DIRECTORY.exists() -> {
                executeForBusinessVideo(BUSINESS_STATUS_DIRECTORY)
            }
            BUSINESS_STATUS_DIRECTORY_NEW.exists() -> {
                executeForBusinessVideo(BUSINESS_STATUS_DIRECTORY_NEW)
            }
            else -> {
                _errorMessageBusiness.postValue("Can't find Whatsapp Business Directory")
            }
        }
    }

    //
    fun getWhatsappStatusImage() {
        when {
            WHATSAPP_STATUS_DIRECTORY.exists() -> {
                executeForWhatsappImage(WHATSAPP_STATUS_DIRECTORY)
            }
            WHATSAPP_STATUS_DIRECTORY_NEW.exists() -> {
                executeForWhatsappImage(WHATSAPP_STATUS_DIRECTORY_NEW)
            }
            else -> {
                _errorMessage.postValue("Can't find Whatsapp Directory")
            }
        }

    }

    fun getWhatsappStatusVideo() {
        when {
            WHATSAPP_STATUS_DIRECTORY.exists() -> {
                executeForWhatsappVideo(WHATSAPP_STATUS_DIRECTORY)
            }
            WHATSAPP_STATUS_DIRECTORY_NEW.exists() -> {
                executeForWhatsappVideo(WHATSAPP_STATUS_DIRECTORY_NEW)
            }
            else -> {
                _errorMessage.postValue("Can't find Whatsapp Directory")
            }
        }
    }

    //
    private fun executeForBusinessImage(wAFolder: File) {
        //  val businessImageStatus = arrayListOf<Status>()
        viewModelScope.launch {
            val statusFiles: Array<File>? = wAFolder.listFiles()
            // businessImageStatus.clear()
            _waBusinessImageStatus.postValue(UIDataState.loading())
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                statusFiles.sortByDescending { it.lastModified() }
                for (file in statusFiles) {
                    val status = Status(file = file, title = file.name, path = file.absolutePath)
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        //   businessImageStatus.add(status)
                        _waBusinessImageStatus.postValue(
                            UIDataState.success(
                                UIState(
                                    status = arrayListOf(status),
                                    errorMessage = null
                                )
                            )
                        )
                    }
                }
//                if (businessImageStatus.size <= 0) {
//                  _waBusinessImageStatus.postValue(UIDataState.failed("No File Found"))
//                }
            } else {
                _waBusinessImageStatus.postValue(
                    UIDataState.failed(
                        UIState(
                            status = null,
                            errorMessage = "No File Found"
                        )
                    )
                )
            }
        }

    }

    private fun executeForBusinessVideo(waFolder: File) {
        //val businessVideoStatus = arrayListOf<Status>()
        viewModelScope.launch {
            val statusFiles = waFolder.listFiles()
            //  businessVideoStatus.clear()
            _waBusinessVideoStatus.postValue(UIDataState.loading())
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                statusFiles.sortByDescending { it.lastModified() }
                for (file in statusFiles) {
                    val status = Status(file, file.name, file.absolutePath)
                    if (status.isVideo) {
                        //       businessVideoStatus.add(status)
                        _waBusinessVideoStatus.postValue(
                            UIDataState.success(
                                UIState(status = arrayListOf(status), null)
                            )
                        )
                    }
                }
                // if (businessVideoStatus.size <= 0) _errorMessageBusiness.postValue("No File Found")

            } else {
                _waBusinessVideoStatus.postValue(UIDataState.failed(UIState(null, "No File Found")))
            }
        }
    }


    //
    private fun executeForWhatsappImage(wAFolder: File) {
        val whatsappImageStatusList = arrayListOf<Status>()
        viewModelScope.launch {
            val statusFiles: Array<File>? = wAFolder.listFiles()
            whatsappImageStatusList.clear()
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                statusFiles.sortByDescending { it.lastModified() }
                for (file in statusFiles) {
                    val status = Status(file = file, title = file.name, path = file.absolutePath)
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        whatsappImageStatusList.add(status)
                        _whatsappImageStatus.postValue(whatsappImageStatusList)
                    }
                }
                if (whatsappImageStatusList.size <= 0) _errorMessage.postValue("No File Found")
            } else {
                _errorMessage.postValue("No File found")
            }
        }
    }

    private fun executeForWhatsappVideo(waFolder: File) {
        val whatsappVideoStatus = arrayListOf<Status>()
        viewModelScope.launch {
            val statusFiles = waFolder.listFiles()
            whatsappVideoStatus.clear()
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                statusFiles.sortByDescending { it.lastModified() }
                for (file in statusFiles) {
                    val status = Status(file, file.name, file.absolutePath)
                    if (status.isVideo) {
                        whatsappVideoStatus.add(status)
                        _whatsappVideoStatus.postValue(whatsappVideoStatus)
                    }
                }
                if (whatsappVideoStatus.size <= 0) _errorMessage.postValue("No File Found")
            } else {
                _errorMessage.postValue("No File found")
            }
        }
    }

    //
    fun getSavedFiles() {
        val savedFilesList = arrayListOf<Status>()
        val appDir = Common.APP_DIR?.let { File(it) }
        if (appDir != null) {
            if (appDir.exists()) {
                viewModelScope.launch {
                    val savedFiles: Array<File>? = appDir.listFiles()
                    savedFilesList.clear()
                    if (savedFiles != null && savedFiles.isNotEmpty()) {
                        savedFiles.sortByDescending { it.lastModified() }
                        for (file in savedFiles) {
                            val status = Status(file, file.name, file.absolutePath)
                            savedFilesList.add(status)
                            _savedStatus.postValue(savedFilesList)
                        }
                    } else _errorMessage.postValue("No File Found")
                }
            } else _errorMessage.postValue("Folder Not Found")
        }
    }
}