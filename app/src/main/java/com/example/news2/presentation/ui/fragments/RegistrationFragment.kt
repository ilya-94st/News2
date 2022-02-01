package com.example.news2.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.news2.R
import com.example.news2.databinding.FragmentRegistrationBinding
import com.example.news2.data.shared.SharedPref
import com.example.news2.presentation.base.BaseFragment
import com.example.news2.presentation.ui.prefs
import com.example.news2.util.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {
    private var saveCheckBox: Boolean = false
    private lateinit var pref: SharedPref

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inVisibleMenu(R.id.botomNavigation)
        pref = SharedPref(requireContext())
        saveCheckBox = prefs.save

        binding.login.setOnClickListener{
            submit()
        }
        passwordFocusListener()
        loginFocusListener()
        saveChoose()
    }

    private fun submit() {
        binding.loginContainer.helperText = validLogin()
        binding.passwordContainer.helperText = validPassword()
        val validLogin = binding.loginContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        if(validLogin && validPassword) {
            binding.etPassword.hideKeyboard()
            positiveResult()
        } else {
            binding.etPassword.hideKeyboard()
            negativeResult()
        }
    }

    private fun positiveResult() {
        val login = binding.etLogin.text.toString()
        val password = binding.etPassword.text.toString()
        val checkBox = binding.checkBox.isChecked
        prefs.login = login
        prefs.pasword = password
        prefs.save = checkBox
        if (prefs.login == Constants.LOGIN && prefs.pasword == Constants.PASSWORD) {
            findNavController().navigate(R.id.action_registrationFragment_to_breackingNewsFragment)
        } else {
            snackBar("error login and password")
            binding.checkBox.isChecked = false
            val editor = pref.preferences.edit()
            editor.clear()
            editor.apply()
        }
    }

    private fun negativeResult() {
        var message = ""
        if(binding.loginContainer.helperText != null) {
            message += "\n\n${"login"}: " + binding.loginContainer.helperText
            binding.loginContainer.error = binding.loginContainer.helperText
        }
        if(binding.passwordContainer.helperText != null) {
            message += "\n\n${"password"}: " + binding.passwordContainer.helperText
            binding.passwordContainer.error = binding.passwordContainer.helperText
        }
    }

    private fun passwordFocusListener() {
        var job: Job? = null
        binding.etPassword.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.TIME_SEARCH)
                binding.passwordContainer.helperText = validLogin()
                binding.passwordContainer.error = ""
            }
        }
    }

    private fun validPassword() : String? {
        val password = binding.etPassword.text.toString()
        if (password.length > 20) {
            return "max symbols 20"
        }
        if(password.isEmpty()) {
            return "password is empty"
        }
        return null
    }

    private fun loginFocusListener() {
        var job: Job? = null
        binding.etLogin.addTextChangedListener {
         job?.cancel()
         job = MainScope().launch {
             delay(Constants.TIME_SEARCH)
             binding.loginContainer.helperText = validLogin()
             binding.loginContainer.error = ""
         }
        }
    }

    private fun validLogin(): String? {
        val login = binding.etLogin.text.toString()
        if(login.isEmpty()) {
            return "login is empty"
        }
        return null
    }

    private fun saveChoose() {
        if(saveCheckBox) {
            findNavController().navigate(R.id.action_registrationFragment_to_breackingNewsFragment)
        }
    }
}