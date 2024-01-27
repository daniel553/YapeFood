package com.yape.data

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

// Just read the json as a mock response
fun MockResponse.fromJson(jsonFile: String): MockResponse =
    setBody(readJsonFile(jsonFile))

private fun readJsonFile(jsonFile: String): String {
    val context = InstrumentationRegistry.getInstrumentation().context

    var br: BufferedReader? = null
    val result = StringBuilder()

    return try {
        br = BufferedReader(
            InputStreamReader(context.assets.open(jsonFile), StandardCharsets.UTF_8)
        )

        var line: String? = null
        do {
            line = br.readLine()
            line?.let { result.append(it) }
        } while (!line.isNullOrEmpty())

        result.toString()
    } catch (e: Exception) {
        result.toString()
    } finally {
        br?.close()
    }

}