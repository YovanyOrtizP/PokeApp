package com.yop.pokeyovany.util

class InformationException(message: String = "Info not available"): Exception(message)

class SelectedPokemonException(message: String = "Pokemon not available") : Exception(message)