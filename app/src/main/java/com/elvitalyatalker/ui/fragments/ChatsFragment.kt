package com.elvitalyatalker.ui.fragments

import androidx.fragment.app.Fragment
import com.elvitalyatalker.R
import com.elvitalyatalker.databinding.FragmentChatsBinding
import com.elvitalyatalker.utilits.APP_ACTIVITY


class ChatsFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
    }
}