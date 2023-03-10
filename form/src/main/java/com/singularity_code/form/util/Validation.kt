/**
 * Design and developed by : St. Ayudha Junior.
 * [mail](stefanus.ayudha@gmail.com)
 * [github](https://github.com/stefanusayudha)
 */
package com.singularity_code.form.util

sealed class ValidationStatus<T>

class Invalid<T>(
    val msg: String
) : ValidationStatus<T>()

class Valid<T>(
    val value: T
) : ValidationStatus<T>()

class Unset<T> : ValidationStatus<T>()
