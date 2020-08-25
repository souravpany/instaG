package com.android.own.instag.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.own.instag.data.repository.UserRepository
import com.android.own.instag.ui.base.BaseViewModel
import com.android.own.instag.utils.common.*
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class LoginScreenViewModel(
    schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val launchRegisterActivity: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    val launchMain: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    private val validationsList: MutableLiveData<List<Validator.Validation>> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val loggingIn: MutableLiveData<Boolean> = MutableLiveData()

    val emailValidation: LiveData<Resource<Int>> = filterValidation(Validator.Validation.Field.EMAIL)
    val passwordValidation: LiveData<Resource<Int>> = filterValidation(Validator.Validation.Field.PASSWORD)

    private fun filterValidation(field: Validator.Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }

    override fun onCreate() {}

    fun onEmailChange(email: String) = emailField.postValue(email)

    fun onPasswordChange(password: String) = passwordField.postValue(password)


    fun onLogin() {
        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateLoginFields(email, password)
        validationsList.postValue(validations)

        if (validations.isNotEmpty() && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                loggingIn.postValue(true)
                compositeDisposable.addAll(
                    userRepository.doUserLogin(email, password)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                            {
                                userRepository.saveCurrentUser(it)
                                loggingIn.postValue(false)
                                launchMain.postValue(Event(emptyMap()))
                            },
                            {
                                handleNetworkError(it)
                                loggingIn.postValue(false)
                            }
                        )
                )
            }
        }
    }


    fun onRegisterClick() {
        launchRegisterActivity.postValue(Event(emptyMap()))
    }
}