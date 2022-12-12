package com.example.firebasecursogaston.domain.camera

import android.graphics.Bitmap
import com.example.firebasecursogaston.data.remote.camera.CameraDataSource

class CameraRepoImpl(private val dataSource: CameraDataSource) :CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPhoto(imageBitmap, description)
    }
}