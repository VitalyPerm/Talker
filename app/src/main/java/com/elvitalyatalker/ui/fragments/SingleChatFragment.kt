package com.elvitalyatalker.ui.fragments

import android.view.View
import com.elvitalyatalker.R
import com.elvitalyatalker.models.CommonModel
import com.elvitalyatalker.models.UserModel
import com.elvitalyatalker.utilits.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.toolbar_info.view.*


class SingleChatFragment(private val contact: CommonModel) : BaseFragment(R.layout.fragment_single_chat) {
    private lateinit var mListenerInfoToolBar: AppValueEventListener
    private lateinit var mRecievingUser: UserModel
    private lateinit var mToolBarInfo: View
    private lateinit var mRefUser:DatabaseReference
    override fun onResume() {
        super.onResume()
        mToolBarInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolBarInfo.visibility = View.VISIBLE
        mListenerInfoToolBar = AppValueEventListener {
            mRecievingUser = it.getUserModel()
            initInfoToolBar()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolBar)
    }

    private fun initInfoToolBar() {
        mToolBarInfo.toolbar_image.downloadAndSetImage(mRecievingUser.photoUrl)
        mToolBarInfo.toolbar_contact_chat_fullname.text = mRecievingUser.fullname
        mToolBarInfo.toolbar_contact_chat_status.text = mRecievingUser.state
    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolBar)
    }
}