package com.example.scanner

import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class MainViewModel: ViewModel() {
    val visiblePermissionDialogQueue = mutableListOf<String>()
    fun dismissDialog(){
        visiblePermissionDialogQueue.removeLast()
    }
    @OptIn(ExperimentalPermissionsApi::class)
    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if (!isGranted){
            visiblePermissionDialogQueue.add(0,permission)
        }
    }}


