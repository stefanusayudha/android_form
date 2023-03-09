/**
 * Design and developed by : St. Ayudha Junior.
 * [mail](stefanus.ayudha@gmail.com)
 * [github](https://github.com/stefanusayudha)
 */
package com.singularity_code.formvalidation.core.pattern

interface FormRule<T> {
    val errorMessage: String
    val rule: (T) -> Boolean
}