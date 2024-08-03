package ru.tanexc.schoolmenu.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream


fun ImageBitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

fun ByteArray.toImageBitmap(): ImageBitmap =
    BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()