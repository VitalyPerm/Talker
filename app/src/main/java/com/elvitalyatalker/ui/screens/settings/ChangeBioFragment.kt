package com.elvitalyatalker.ui.screens.settings

import com.elvitalyatalker.R
import com.elvitalyatalker.dataBase.USER
import com.elvitalyatalker.dataBase.setBioToDataBase
import com.elvitalyatalker.ui.screens.BaseChangeFragment
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        setBioToDataBase(newBio)
    }
}