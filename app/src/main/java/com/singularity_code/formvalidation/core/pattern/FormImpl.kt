package com.singularity_code.formvalidation.core.pattern

import com.singularity_code.formvalidation.core.util.ID
import com.singularity_code.formvalidation.core.util.Invalid
import com.singularity_code.formvalidation.core.util.Valid

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