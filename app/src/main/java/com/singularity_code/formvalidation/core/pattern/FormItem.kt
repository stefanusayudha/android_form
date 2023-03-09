package com.singularity_code.formvalidation.core.pattern

import com.singularity_code.formvalidation.core.util.Invalid
import com.singularity_code.formvalidation.core.util.Unset
import com.singularity_code.formvalidation.core.util.Valid
import com.singularity_code.formvalidation.core.util.ValidationStatus

data class FormItem<T>(

    /** ID item for serialization **/
    val id: String,

    /** if false then value "Unset" will be allowed **/
    val required: Boolean = true,

    /** value **/
    val value: ValidationStatus<T> = Unset()
) {
    /**
     * @return true if value = valid
     * @return false if value = invalid
     * @return true if value = unset and required = false
     * @return false if value != valid and required = true
     */
    fun isValid() : Boolean =
        when {
            value is Valid -> true
            value is Invalid -> false
            value is Unset && !required -> true
            else -> false
        }

    fun errorMessage() : String? =
        when {
            value is Valid -> null
            value is Invalid -> value.msg
            value is Unset && !required -> null
            else -> "Cannot be empty!"
        }

}