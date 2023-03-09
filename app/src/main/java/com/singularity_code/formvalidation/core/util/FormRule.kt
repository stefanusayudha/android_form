/**
 * Design and developed by : St. Ayudha Junior.
 * [mail](stefanus.ayudha@gmail.com)
 * [github](https://github.com/stefanusayudha)
 */
package com.singularity_code.formvalidation.core.util

import com.singularity_code.formvalidation.core.pattern.FormRule


fun<T> formRule(
    errorMessage: String,
    rule: (T) -> Boolean
) = object : FormRule<T> {
    override val errorMessage: String = errorMessage
    override val rule: (T) -> Boolean = rule

}