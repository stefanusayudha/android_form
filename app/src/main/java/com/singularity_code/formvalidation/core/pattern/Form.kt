/**
 * Design and developed by : St. Ayudha Junior.
 * [mail](stefanus.ayudha@gmail.com)
 * [github](https://github.com/stefanusayudha)
 */
package com.singularity_code.formvalidation.core.pattern

import arrow.core.foldLeft
import com.singularity_code.formvalidation.core.util.*

interface Form {

    companion object {
        fun Create(): Form {
            return FormImpl()
        }
    }

    val formItems: Map<ID, FormItem<*>>
    val formRules: Map<ID, List<FormRule<*>>>

    fun build(): Map<String, ValidationStatus<*>> =
        hashMapOf<String, ValidationStatus<*>>().apply {
            formItems.forEach {
                put(
                    it.key,
                    it.value.value
                )
            }
        }

    fun <T> addItem(
        item: FormItem<T>
    )

    fun <T> addRule(
        id: String,
        formRule: FormRule<T>
    )

    @Suppress("UNCHECKED_CAST")
    fun <T> update(
        id: String,
        value: T
    )

    fun onValidationResult(
        block: (FormItem<*>) -> Unit
    )

    fun allIsValid(): Boolean =
        formItems.foldLeft(true) { prev, result ->

            val isValid = when (result.value.value) {
                is Valid -> true
                is Invalid -> false
                is Unset -> formItems[result.key]?.required ?: true
            }

            prev && isValid
        }
}

