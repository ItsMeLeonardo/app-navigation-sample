package com.example.partial.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun convertBitmapToFile(context: Context, bitmap: Bitmap): File {
    val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
    file.createNewFile()


    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
    val bitmapData = bos.toByteArray()

    val fos = FileOutputStream(file)
    fos.write(bitmapData)
    fos.flush()
    fos.close()

    return file
}

@SuppressLint("Recycle")
fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
    file.createNewFile()

    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)

    return file
}
