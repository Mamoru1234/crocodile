package com.mamoru.dictionary.helper

import java.io.File

fun readWords(dictionaryPath: String) = File(dictionaryPath).bufferedReader().lines()
    .map(String::trim).map(String::lowercase).filter(String::isNotEmpty)
    .toList()
    .distinct()
    .sorted()

fun orderDictionary(dictionaryPath: String) {
    val words = readWords(dictionaryPath)
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
        orderDictionary(it)
    }
    var wordSet = emptySet<String>()
    var duplicates = emptySet<String>()
    dictionaries.forEach {
        val words = readWords(it)
        duplicates = duplicates + words.filter { word -> wordSet.contains(word) }
        wordSet = wordSet + words
    }
    if (duplicates.isNotEmpty()) {
        println("Found duplicates ${duplicates.size}")
        duplicates.forEach {
            println(it)
        }
    }
}