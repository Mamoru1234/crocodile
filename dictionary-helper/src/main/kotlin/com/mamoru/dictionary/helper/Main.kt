package com.mamoru.dictionary.helper

import java.io.File

fun main() {
    val dictionaryPath = "/home/oleksii/AndroidStudioProjects/Crocodile/app/src/main/assets/dictionaries/easy.txt"
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