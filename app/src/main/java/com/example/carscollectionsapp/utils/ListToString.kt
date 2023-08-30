package com.example.carscollectionsapp.utils

fun <T> List<T>.listToString(): String {
    val text = StringBuilder()
    this.forEach {
        text.append("${it.toString()}\n")
    }
    return text.toString()
}