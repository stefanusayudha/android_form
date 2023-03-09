/**
 * Design and developed by : St. Ayudha Junior.
 * [mail](stefanus.ayudha@gmail.com)
 * [github](https://github.com/stefanusayudha)
 */
package com.singularity_code.formvalidation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.singularity_code.formvalidation.core.pattern.Form
import com.singularity_code.formvalidation.core.pattern.FormItem
import com.singularity_code.formvalidation.core.util.*
import com.singularity_code.formvalidation.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    /** # 1. Initiate Forms **/
    private val form: Form by lazy {
        form("user_data").apply {

            /** Form Items **/
            addItem(
                formItem<FORM_USER_NAME_TYPE>(
                    FORM_USER_NAME
                )
            )
            addItem(
                formItem<FORM_AGE_TYPE>(
                    FORM_AGE
                )
            )

            /** Form Rules **/
            addRule<FORM_USER_NAME_TYPE>(
                FORM_USER_NAME,
                formRule(
                    errorMessage = "Cannot be blank",
                ) { value ->
                    value.isNotBlank()
                }
            )
            addRule<FORM_USER_NAME_TYPE>(
                FORM_USER_NAME,
                formRule(
                    errorMessage = "Cannot contain @",
                ) { value ->
                    !value.contains("@")
                }
            )
            addRule<FORM_AGE_TYPE>(
                FORM_AGE,
                formRule(
                    errorMessage = "Cannot be under 18"
                ) { value ->
                    value >= 18
                }
            )
        }
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
        initAction()
        initObserver()
    }

    private fun initUI() {
        /** setup binding **/
        run {
            binding = ActivityMainBinding.inflate(layoutInflater)
        }

        /** setup content view **/
        run {
            val view = binding.root
            setContentView(view)
        }
    }

    /** # 2. Update Form **/
    private fun initAction() {

        /** update form on text user name change **/
        binding.uname.addTextChangedListener {
            form.update<FORM_USER_NAME_TYPE>(
                FORM_USER_NAME,
                it.toString()
            )
        }

        /** update form on text age change **/
        binding.age.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.age.setText("0")
                binding.age.setSelection(1)
            } else if (it.first() == "0".first() && it.length > 1) {
                binding.age.setText(it.substring(1))
                binding.age.setSelection(1)
            } else {
                form.update<FORM_AGE_TYPE>(
                    FORM_AGE,
                    it.toString().toInt()
                )
            }
        }
    }

    /** # 3. Observe Validation Result **/
    private fun initObserver() {

        /** observing form validation result **/
        form.onValidationResult {
            updateValidationUI(it)

            if (form.allIsValid()) {
                onFormIsValid()
            } else {
                onFormInValid()
            }
        }
    }

    private fun updateValidationUI(
        formItem: FormItem<*>
    ) {
        when (formItem.id) {
            FORM_USER_NAME -> updateUnameValidationUI(formItem)
            FORM_AGE -> updateAgeValidationUI(formItem)
        }
    }

    private fun onFormIsValid() {
        enableSubmitButton()
    }

    private fun onFormInValid() {
        disableSubmitButton()
    }

    private fun updateUnameValidationUI(
        formItem: FormItem<*>
    ) {
        binding.uname.error = formItem.errorMessage()
    }

    private fun updateAgeValidationUI(
        formItem: FormItem<*>
    ) {
        binding.age.error = formItem.errorMessage()
    }

    private fun disableSubmitButton() {
        binding.submitBtn.isEnabled = false
    }

    private fun enableSubmitButton() {
        binding.submitBtn.isEnabled = true
    }

}