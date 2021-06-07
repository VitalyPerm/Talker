package com.elvitalyatalker.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import com.elvitalyatalker.R
import com.elvitalyatalker.databinding.FragmentSettingsBinding


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private lateinit var mBinding: FragmentSettingsBinding

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }
}