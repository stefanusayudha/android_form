package com.singularity_code.formvalidation.core.pattern

class FormRule<T>(
    val errorMessage: String,
    val rule: (T) -> Boolean
)