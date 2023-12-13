package com.example.partial.service

import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import java.io.File

fun uploadImageToCloudinary(
    imageFile: File,
    onSuccess: (String) -> Unit,
    onError: (Exception) -> Unit,
) {
    MediaManager.get().upload(imageFile.path).callback(object : UploadCallback {
        override fun onStart(requestId: String?) {
//            TODO("Not yet implemented")
        }

        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
//            TODO("Not yet implemented")
        }

        override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
            val url = resultData?.get("url") as String
            onSuccess(url)
        }

        override fun onError(requestId: String?, error: ErrorInfo?) {
            onError(Exception(error?.description))
        }

        override fun onReschedule(requestId: String?, error: ErrorInfo?) {
//            TODO("Not yet implemented")

        }

    }).dispatch()
}
