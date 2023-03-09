/**
 * Design and developed by : St. Ayudha Junior.
 * [mail](stefanus.ayudha@gmail.com)
 * [github](https://github.com/stefanusayudha)
 */
package com.singularity_code.formvalidation.core.pattern

import arrow.core.foldLeft
import com.singularity_code.formvalidation.core.util.Invalid
import com.singularity_code.formvalidation.core.util.Unset
import com.singularity_code.formvalidation.core.util.Valid
import com.singularity_code.formvalidation.core.util.ValidationStatus

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

typealias ID = String

class FormRule<T>(
    val errorMessage: String,
    val rule: (T) -> Boolean
)

class FormImpl() : Form {
    private val _formItems = hashMapOf<ID, FormItem<*>>()
    override val formItems: Map<ID, FormItem<*>>
        get() = _formItems

    private val _formRules = hashMapOf<ID, List<FormRule<*>>>()
    override val formRules: Map<ID, List<FormRule<*>>>
        get() = _formRules

    override fun <T> addItem(
        item: FormItem<T>
    ) {
        _formItems[item.id] = item
    }

    override fun <T> addRule(
        id: String,
        formRule: FormRule<T>
    ) {
        val rules = (_formRules[id] ?: listOf()).plus(formRule)
        _formRules[id] = rules
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> update(
        id: String,
        value: T
    ) {

        var isValid: Boolean
        val errorMessage: String

        /** Validate.
         * Fold and invoke rule check. Fold into Pair<IsValid,ErrorMessageIfNotValid>
         */
        run {
            val validationResult = formRules[id]?.fold(true to "") { prev, rule ->
                when {
                    !prev.first -> prev
                    (rule.rule as (T) -> Boolean).invoke(value) -> true to ""
                    else -> false to rule.errorMessage
                }
            }

            isValid = validationResult?.first ?: true
            errorMessage = validationResult?.second ?: "Unknown Validation Error"
        }

        /** Update Validation Result **/
        run {

            (formItems[id] as? FormItem<T>)
                ?.copy(
                    value = if (isValid) {
                        Valid(value)
                    } else {
                        Invalid(errorMessage)
                    }
                )?.also {
                    _formItems[id] = it
                }
        }

        /** trigger validation result callback **/
        (formItems[id] as? FormItem<T>)
            ?.apply{
                onValidationResultCallback?.invoke(this)
            }
    }

    private var onValidationResultCallback: ((FormItem<*>) -> Unit)? = null
    override fun onValidationResult(
        block: (FormItem<*>) -> Unit
    ) {
        onValidationResultCallback = block
    }

}