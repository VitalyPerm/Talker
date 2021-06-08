 package com.elvitalyatalker.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.elvitalyatalker.MainActivity
import com.elvitalyatalker.R
import com.elvitalyatalker.utilits.APP_ACTIVITY


 open class BaseChangeFragment (layout: Int): Fragment(layout) {
    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        (activity as MainActivity).mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.hideKeyboard()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }
}