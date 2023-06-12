package com.yop.pokeyovany.util

// checar si el ultimo index es empty o el numero
// si es empty debes tomar el last index - 1
fun String.getPokemonIdFromUrl(): String {
    val urlList = this.split('/')
    return urlList[urlList.lastIndex - 1]
}