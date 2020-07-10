package com.mhtmalpani.superextensions.custom

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Converts a file to a Json object of given type
 *
 * @param filePath the path of the file under the assets directory
 * @param clazz the Model that it needs to be converted to
 *
 * Usage:
 *      val fileAsObject = assetToJsonObject("sample.json", LibraryModel::class.java)
 */
fun <T> Activity.assetToJsonObject(
    filePath: String,
    clazz: Class<T>
): T {
    val json = readAssetAsString(filePath)
    return Gson().fromJson(json, clazz)
}


/**
 * Converts a file to a Json object of given type
 *
 * @param filePath the path of the file under the assets directory
 * @param clazz the Model that it needs to be converted to
 *
 * Usage:
 *      val fileAsObject = assetToJsonObject("sample.json", LibraryModel::class.java)
 */
fun <T> Fragment.assetToJsonObject(
    filePath: String,
    clazz: Class<T>
): T {
    val json = readAssetAsString(filePath)
    return Gson().fromJson(json, clazz)
}


/**
 * Converts a file to a String
 *
 * @param filePath the path of the file under the assets directory
 *
 * Usage:
 *      val fileAsObject = readAssetAsString("sample.json")
 */
fun Activity.readAssetAsString(filePath: String): String? {
    return readAssetAsString(this, filePath)
}


/**
 * Converts a file to a String
 *
 * @param filePath the path of the file under the assets directory
 *
 * Usage:
 *      val fileAsObject = readAssetAsString("sample.json")
 */
fun Fragment.readAssetAsString(filePath: String): String? {
    return readAssetAsString(requireContext(), filePath)
}


@Throws(Exception::class)
private fun readAssetAsString(context: Context, filePath: String): String? {

    val inputStream = context.applicationContext.assets.open(filePath)

    val inputStreamReader = InputStreamReader(inputStream)
    val bufferedReader = BufferedReader(inputStreamReader)

    try {
        val text = bufferedReader.readLines()
        return text.joinToString("")

    } catch (e: Exception) {
        inputStream.close()
        inputStreamReader.close()
        bufferedReader.close()

        throw e
    }
}