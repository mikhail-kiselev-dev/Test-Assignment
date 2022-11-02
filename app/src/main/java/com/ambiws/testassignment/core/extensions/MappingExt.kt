package com.ambiws.testassignment.core.extensions

import com.ambiws.testassignment.core.exceptions.MappingException

fun <T> Any?.getOrThrow(message: String): T =
    this as T ?: throw MappingException(message)

fun <T> Any?.getOrThrowDefaultError(name: String): T =
    this as T ?: throw MappingException("$name can't be null")
