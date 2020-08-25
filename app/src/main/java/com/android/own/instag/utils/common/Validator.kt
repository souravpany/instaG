package com.android.own.instag.utils.common


import com.android.own.instag.R
import java.util.regex.Pattern

object Validator {

    private val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private const val MIN_PASSWORD_LENGTH = 6

    private const val MIN_NAME_LENGTH = 4

    fun validateLoginFields(email: String?, password: String?): List<Validation> =
        ArrayList<Validation>().apply {
            when {
                email.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            Resource.error(R.string.email_field_empty)
                        )
                    )
                !EMAIL_ADDRESS.matcher(email).matches() ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            Resource.error(R.string.email_field_invalid)
                        )
                    )
                else ->
                    add(Validation(Validation.Field.EMAIL, Resource.success()))
            }
            when {
                password.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.PASSWORD,
                            Resource.error(R.string.password_field_empty)
                        )
                    )
                password.length < MIN_PASSWORD_LENGTH ->
                    add(
                        Validation(
                            Validation.Field.PASSWORD,
                            Resource.error(R.string.password_field_small_length)
                        )
                    )
                else -> add(Validation(Validation.Field.PASSWORD, Resource.success()))
            }
        }


    fun validateRegisterFields(
        name: String?,
        email: String?,
        password: String?
    ): List<ValidationRegister> =
        ArrayList<ValidationRegister>().apply {
            when {
                email.isNullOrBlank() ->
                    add(
                        ValidationRegister(
                            ValidationRegister.FieldRegister.EMAIL,
                            Resource.error(R.string.email_field_empty)
                        )
                    )
                !EMAIL_ADDRESS.matcher(email).matches() ->
                    add(
                        ValidationRegister(
                            ValidationRegister.FieldRegister.EMAIL,
                            Resource.error(R.string.email_field_invalid)
                        )
                    )
                else ->
                    add(
                        ValidationRegister(
                            ValidationRegister.FieldRegister.EMAIL,
                            Resource.success()
                        )
                    )
            }
            when {
                password.isNullOrBlank() ->
                    add(
                        ValidationRegister(
                            ValidationRegister.FieldRegister.PASSWORD,
                            Resource.error(R.string.password_field_empty)
                        )
                    )
                password.length < MIN_PASSWORD_LENGTH ->
                    add(
                        ValidationRegister(
                            ValidationRegister.FieldRegister.PASSWORD,
                            Resource.error(R.string.password_field_small_length)
                        )
                    )
                else -> add(
                    ValidationRegister(
                        ValidationRegister.FieldRegister.PASSWORD,
                        Resource.success()
                    )
                )
            }
            when {
                name.isNullOrBlank() ->
                    add(
                        ValidationRegister(
                            ValidationRegister.FieldRegister.NAME,
                            Resource.error(R.string.name_field_empty)
                        )
                    )
                name.length < MIN_NAME_LENGTH ->
                    add(
                        ValidationRegister(
                            ValidationRegister.FieldRegister.PASSWORD,
                            Resource.error(R.string.name_field_small_length)
                        )
                    )
                else -> add(
                    ValidationRegister(
                        ValidationRegister.FieldRegister.NAME,
                        Resource.success()
                    )
                )
            }

        }

    data class Validation(val field: Field, val resource: Resource<Int>) {

        enum class Field {
            EMAIL,
            PASSWORD
        }
    }

    data class ValidationRegister(val field: FieldRegister, val resource: Resource<Int>) {

        enum class FieldRegister {
            NAME,
            EMAIL,
            PASSWORD
        }
    }
}