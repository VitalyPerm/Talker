package com.elvitalyatalker.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.elvitalyatalker.MainActivity
import com.elvitalyatalker.R
import com.elvitalyatalker.activities.RegisterActivity
import com.elvitalyatalker.databinding.FragmentSettingsBinding
import com.elvitalyatalker.utilits.AUTH
import com.elvitalyatalker.utilits.replaceActivity


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
        }
        return true
    }
}