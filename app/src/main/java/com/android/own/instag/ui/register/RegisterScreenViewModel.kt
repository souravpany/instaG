package com.android.own.instag.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.own.instag.data.repository.RegisterUserRepository
import com.android.own.instag.ui.base.BaseViewModel
import com.android.own.instag.utils.common.Event
import com.android.own.instag.utils.common.Resource
import com.android.own.instag.utils.common.Status
import com.android.own.instag.utils.common.Validator
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class RegisterScreenViewModel(
    schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    compositeDisposable: CompositeDisposable,
    private val registerUserRepository: RegisterUserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val launchLoginActivity: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    val launchMain: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    private val validationsList: MutableLiveData<List<Validator.ValidationRegister>> =
        MutableLiveData()
    val nameField: MutableLiveData<String> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val registerIn: MutableLiveData<Boolean> = MutableLiveData()

    val emailValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.ValidationRegister.FieldRegister.EMAIL)
    val passwordValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.ValidationRegister.FieldRegister.PASSWORD)
    val nameValidation: LiveData<Resource<Int>> =
        filterValidation(Validator.ValidationRegister.FieldRegister.NAME)

    private fun filterValidation(field: Validator.ValidationRegister.FieldRegister) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }

    override fun onCreate() {}

    fun onNameChange(name: String) = nameField.postValue(name)

    fun onEmailChange(email: String) = emailField.postValue(email)

    fun onPasswordChange(password: String) = passwordField.postValue(password)


    fun onRegister() {
        val email = emailField.value
        val password = passwordField.value
        val name = nameField.value

        val validations = Validator.validateRegisterFields(name, email, password)
        validationsList.postValue(validations)

        if (validations.isNotEmpty() && email != null && password != null && name != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                registerIn.postValue(true)
                compositeDisposable.addAll(
                    registerUserRepository.doUserRegister(name, email, password)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                            {
                                registerUserRepository.saveCurrentUser(it)
                                registerIn.postValue(false)
                                launchMain.postValue(Event(emptyMap()))
                            },
                            {
                                handleNetworkError(it)
                                registerIn.postValue(false)
                            }
                        )
                )
            }
        }
    }


    fun onLoginTextClick() {
        launchLoginActivity.postValue(Event(emptyMap()))
    }
}