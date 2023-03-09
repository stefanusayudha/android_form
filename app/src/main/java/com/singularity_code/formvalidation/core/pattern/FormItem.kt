/**
 * Design and developed by : St. Ayudha Junior.
 * [mail](stefanus.ayudha@gmail.com)
 * [github](https://github.com/stefanusayudha)
 */
package com.singularity_code.formvalidation.core.pattern

import com.singularity_code.formvalidation.core.util.ValidationStatus

interface FormItem<T> {
    /** ID item for serialization **/
    val id: String

    /** if false then value "Unset" will be allowed **/
    val required: Boolean

    /** value **/
    val value: ValidationStatus<T>

    /**
     * @return true if value = valid
     * @return false if value = invalid
     * @return true if value = unset and required = false
     * @return false if value != valid and required = true
     */
    fun isValid() : Boolean

    fun errorMessage() : String?
}