package com.elvitalyatalker.ui.fragments.single_chat

import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import com.elvitalyatalker.R
import com.elvitalyatalker.models.CommonModel
import com.elvitalyatalker.models.UserModel
import com.elvitalyatalker.ui.fragments.BaseFragment
import com.elvitalyatalker.utilits.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: AppChildEventListener
    private var mCountMessages = 10
    private var mIsScrolling = false
    private var mSmoothScrollToPosition = true
    private val mListListeners = mutableListOf<AppChildEventListener>()

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
        mMessagesListener = AppChildEventListener {
            mAdapter.addItem(it.getCommonModel())
            if (mSmoothScrollToPosition) {
                mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
            }
        }


        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)
        mListListeners.add(mMessagesListener)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mIsScrolling && dy < 0) {
                    updateData()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    mIsScrolling = true
            }
        })

    }

    private fun updateData() {
        mSmoothScrollToPosition = false
        mIsScrolling = false
        mCountMessages += 10
        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)
        mListListeners.add(mMessagesListener)

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
            mSmoothScrollToPosition = true
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
        mListListeners.forEach{
            mRefMessages.removeEventListener(it)
        }
    println()
    }
}