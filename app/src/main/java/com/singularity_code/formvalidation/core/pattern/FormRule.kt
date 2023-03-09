package com.singularity_code.formvalidation.core.pattern

interface FormRule<T> {
    val errorMessage: String
    val rule: (T) -> Boolean
}