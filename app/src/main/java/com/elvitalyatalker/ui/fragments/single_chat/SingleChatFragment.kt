package com.elvitalyatalker.ui.fragments.single_chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.elvitalyatalker.R
import com.elvitalyatalker.models.CommonModel
import com.elvitalyatalker.models.UserModel
import com.elvitalyatalker.ui.fragments.BaseFragment
import com.elvitalyatalker.utilits.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*


class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {
    private lateinit var mListenerInfoToolBar: AppValueEventListener
    private lateinit var mRecievingUser: UserModel
    private lateinit var mToolBarInfo: View
    private lateinit var mRefUser: DatabaseReference
    private lateinit var mRefMessages:DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: AppValueEventListener
    private var mListMessager = emptyList<CommonModel>()

    override fun onResume() {
        super.onResume()
        initToolBar()
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecyclerView = chat_recycle_view
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID).child(contact.id)
        mRecyclerView.adapter = mAdapter
        mMessagesListener = AppValueEventListener{ dataSnapshot ->
            mListMessager = dataSnapshot.children.map { it.getCommonModel() }
            mAdapter.setList(mListMessager)
            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
        }
        mRefMessages.addValueEventListener(mMessagesListener)
    }

    private fun initToolBar() {
        mToolBarInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolBarInfo.visibility = View.VISIBLE
        mListenerInfoToolBar = AppValueEventListener {
            mRecievingUser = it.getUserModel()
            initInfoToolBar()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolBar)
        chat_btn_send_message.setOnClickListener {
            val message = chat_input_message.text.toString()
            if (message.isEmpty()) {
                showToast("Message is empty")
            } else sendMessage(message, contact.id, TYPE_TEXT) {
                chat_input_message.setText("")
            }
        }
    }


    private fun initInfoToolBar() {
        if (mRecievingUser.fullname.isEmpty()) {
            mToolBarInfo.toolbar_contact_chat_fullname.text = contact.fullname
        } else mToolBarInfo.toolbar_contact_chat_fullname.text = mRecievingUser.fullname

        mToolBarInfo.toolbar_image.downloadAndSetImage(mRecievingUser.photoUrl)
        mToolBarInfo.toolbar_contact_chat_status.text = mRecievingUser.state
    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolBar)
        mRefMessages.removeEventListener(mMessagesListener)
    }
}