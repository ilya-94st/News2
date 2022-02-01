package com.example.news2.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.news2.presentation.tools.SnackBar
import com.example.news2.presentation.tools.showAlertDialog
import com.example.news2.presentation.tools.toast
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseFragment<B: ViewBinding> : Fragment() {

    protected lateinit var binding : B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater,container)
        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : B

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    fun inVisibleMenu(menuID: Int) {
        requireActivity().findViewById<BottomNavigationView>(menuID).visibility = View.INVISIBLE
    }
    fun visibleMenu(menuID: Int) {
        requireActivity().findViewById<BottomNavigationView>(menuID).visibility = View.VISIBLE
    }
    fun snackBar(text: String) {
        SnackBar(binding.root ,text)
    }
    fun toast(message: String) {
        requireContext().toast(message)
    }
    fun showAlertDialog(title: String, message: String){
        requireContext().showAlertDialog(title, message)
    }

}
