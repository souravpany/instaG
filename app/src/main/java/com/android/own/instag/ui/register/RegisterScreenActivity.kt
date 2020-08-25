package com.android.own.instag.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.android.own.instag.R
import com.android.own.instag.di.component.ActivityComponent
import com.android.own.instag.ui.base.BaseActivity
import com.android.own.instag.ui.login.LoginScreenActivity
import com.android.own.instag.ui.main.MainScreenActivity
import com.android.own.instag.utils.common.Event
import com.android.own.instag.utils.common.Status
import kotlinx.android.synthetic.main.activity_register_screen.*

class RegisterScreenActivity : BaseActivity<RegisterScreenViewModel>(), View.OnClickListener {


    override fun provideLayoutId(): Int = R.layout.activity_register_screen


    override fun injectDependencies(activityComponent: ActivityComponent) {

        activityComponent.inject(this)
    }


    override fun setupView(savedInstanceState: Bundle?) {

        initListener()

        edtName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onNameChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

    }

    private fun initListener() {

        loginWithEmailText.setOnClickListener(this)
        btSignUp.setOnClickListener(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.launchLoginActivity.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, LoginScreenActivity::class.java))
                finish()
            }
        })

        viewModel.launchMain.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, MainScreenActivity::class.java))
                finish()
            }
        })

        viewModel.nameField.observe(this, Observer {
            if (edtName.text.toString() != it) edtName.setText(it)
        })

        viewModel.nameValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> layout_name.error = it.data?.run { getString(this) }
                else -> layout_name.isErrorEnabled = false
            }
        })


        viewModel.emailField.observe(this, Observer {
            if (edtEmail.text.toString() != it) edtEmail.setText(it)
        })

        viewModel.emailValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> layout_email.error = it.data?.run { getString(this) }
                else -> layout_email.isErrorEnabled = false
            }
        })

        viewModel.passwordField.observe(this, Observer {
            if (edtPassword.text.toString() != it) edtPassword.setText(it)
        })

        viewModel.passwordValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> layout_password.error = it.data?.run { getString(this) }
                else -> layout_password.isErrorEnabled = false
            }
        })

        viewModel.registerIn.observe(this, Observer {
            pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {

                R.id.loginWithEmailText -> viewModel.onLoginTextClick()

                R.id.btSignUp -> viewModel.onRegister()
            }
        }

    }

}
