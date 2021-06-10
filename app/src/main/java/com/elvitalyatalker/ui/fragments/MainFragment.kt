package com.elvitalyatalker.ui.fragments

import androidx.fragment.app.Fragment
import com.elvitalyatalker.R
import com.elvitalyatalker.utilits.APP_ACTIVITY


class MainFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Talker"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }
}