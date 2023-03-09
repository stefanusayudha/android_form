package com.singularity_code.formvalidation.core.util

sealed class ValidationStatus<T>

class Invalid<T>(
    val msg: String
) : ValidationStatus<T>()

class Valid<T>(
    val value: T
) : ValidationStatus<T>()

class Unset<T> : ValidationStatus<T>()
