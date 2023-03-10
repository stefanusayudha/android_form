# Singularity Code Present

Library untuk melakukan validasi formulir android.<br>
Formulir berupa Formulir object yang tidak terintegrasi pada view, hal ini memungkinkan pengaplikasian yang lebih fleksible.<br>
<br>
Penggunaan:<br>

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
  
dependencies {
    implementation 'com.github.stefanusayudha:android_form:1.0.0'
}

```
<br>

Contoh pengaplikasian:

```
    1. Initiate Forms. Dalam kondisi default, form value adalah Unset
    
    private val form: Form by lazy {
        form("user_data").apply {

            /** Form Items **/
            addItem(
                formItem<FORM_USER_NAME_TYPE>(
                    FORM_USER_NAME
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
        }
    }
    
    
    2. Bind on value change event dan update form value
    
    binding.uname.addTextChangedListener {
        form.update<FORM_USER_NAME_TYPE>(
            FORM_USER_NAME,
            it.toString()
        )
    }
    
    
    3. Observe Validation Result
    
    form.onValidationResult {
        updateValidationUI(it)

        if (form.allIsValid()) {
            onFormIsValid()
        } else {
            onFormInValid()
        }
    }
    
    
    4. Build form untuk mendapatkan Map ValidationStatus
    
    val formResult: Map<String, ValidationStatus<*>> = form.build()
    
```
