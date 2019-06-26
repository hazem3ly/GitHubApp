package com.itg.githubapp.extensions

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Uri.fileName(context: Context): String {
    val cursor: Cursor? = context.contentResolver.query(
        this, null,
        null,
        null,
        null,
        null
    )
    var displayName = "Unknown"
    cursor?.use {
        // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
        // "if there's anything to look at, look at it" conditionals.
        if (it.moveToFirst()) {
            // Note it's called "Display Name".  This is
            // provider-specific, and might not necessarily be the file name.
            displayName =
                it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))

        } else {
            "Unknown"
        }
    }
    return displayName
}

fun Uri.createTempFile(context: Context): File {
    val f = File(context.cacheDir.parent + File.separator + fileName(context))
    try {
        context.contentResolver.openInputStream(this)?.use { inputStream ->
            val out = FileOutputStream(f)
            val buf = ByteArray(1024)
            while (true) {
                val length = inputStream.read(buf)
                if (length <= 0)
                    break
                out.write(buf, 0, length)
            }
            out.close()
        }
        return f
    } catch (e: IOException) {
        throw IOException()
    }
}

fun TextInputEditText.isValidateEditText(): Boolean {
    return !(this.text?.isNotEmpty() ?: false)
}

fun TextInputEditText.value(): String {
    return this.text.toString()
}


fun ArrayList<String>.save(context: Context): Boolean {
    val sp = context.getSharedPreferences("my_sp", 0)
    val editor = sp.edit()
    editor.putInt(LIST_SIZE, size)
    for (i in 0..size) {
        editor.remove("item_$i")
        editor.putString("item_$i", get(i))
    }
    return editor.commit()
}

const val LIST_SIZE = "list_size"
fun String.saveToSpAndClearIfFive(context: Context): Boolean {
    var size = 0
    val sp = context.getSharedPreferences("my_sp", 0)
    val editor = sp.edit()
    if (sp.contains(LIST_SIZE)) {
        size = sp.getInt(LIST_SIZE, 0)
        size++
    } else {
        size++
    }
    if (size >= 6) {
        editor.clear()
        size = 1
    }
    editor.putInt(LIST_SIZE, size)
    size--
    editor.putString("item_$size", this)
    return editor.commit()
}


fun Context.loadSavedList(): ArrayList<String> {
    val sp = getSharedPreferences("my_sp", 0)
    val list = ArrayList<String>()
    if (sp.contains(LIST_SIZE)) {
        val size = sp.getInt(LIST_SIZE, 0)
        for (i in 0..size) {
            list.add(sp.getString("item_$i", "") ?: "")
        }
    }
    return list
}