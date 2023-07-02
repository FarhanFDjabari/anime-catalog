package com.djabaridev.anicatalog.utils

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale

fun String.snakeToReadable(): String {
    // convert snake case to camel case using regex
    val pattern1 = "_[a-z]".toRegex()
    val camelcase = replace(pattern1) {
        it.value.replace("_", " ").uppercase()
    }
    // split camel case
    return camelcase.capitalize(Locale.current)
}