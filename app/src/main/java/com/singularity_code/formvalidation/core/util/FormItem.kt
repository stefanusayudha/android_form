/**
 * Design and developed by : St. Ayudha Junior.
 * [mail](stefanus.ayudha@gmail.com)
 * [github](https://github.com/stefanusayudha)
 */
package com.singularity_code.formvalidation.core.util

import com.singularity_code.formvalidation.core.pattern.FormItem


class FormItemImpl<T>(
    /** ID item for serialization **/
    override val id: String,

    /** if false then value "Unset" will be allowed **/
    override val required: Boolean = true,

    /** value **/
    override val value: ValidationStatus<T> = Unset()
) : FormItem<T> {
    /**
     * @return true if value = valid
     * @return false if value = invalid
     * @return true if value = unset and required = false
     * @return false if value != valid and required = true
     */
    override fun isValid(): Boolean =
        when {
            value is Valid -> true
            value is Invalid -> false
            value is Unset && !required -> true
            else -> false
        }

    override fun errorMessage(): String? =
        when {
            value is Valid -> null
            value is Invalid -> value.msg
            value is Unset && !required -> null
            else -> "Cannot be empty!"
        }

}

fun <T> formItem(
    id: String,
    required: Boolean = true
): FormItem<T> =
    FormItemImpl(
        id = id,
        required = required
    )