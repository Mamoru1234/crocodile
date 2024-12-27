package com.mamoru.dictionary.helper

import java.io.File

fun fixDictionary(dictionaryPath: String) {
    val words = File(dictionaryPath).bufferedReader().lines()
        .map(String::trim).map(String::lowercase).filter(String::isNotEmpty)
        .toList()
        .distinct()
        .sorted()
    File(dictionaryPath).bufferedWriter().use { out ->
        words.forEach {
            out.write(it)
            out.newLine()
        }
    }
}

fun main() {
    val baseDir = "/home/oleksii/AndroidStudioProjects/Crocodile/app/src/main/assets/dictionaries"
    val dictionaries = listOf("$baseDir/easy.txt", "$baseDir/medium.txt")
    dictionaries.forEach {
        fixDictionary(it)
    }
}