package com.elvitalyatalker.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.elvitalyatalker.R
import com.elvitalyatalker.utilits.AppTextWatcher
import com.elvitalyatalker.utilits.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*


class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {
    override fun onStart() {
        super.onStart()
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = register_input_code.text.toString()
            if (string.length == 6) {
                verifiCode()
            }
        })
    }

    fun verifiCode() {
        showToast("Ok")
    }

}