package com.elvitalyatalker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.elvitalyatalker.activities.RegisterActivity
import com.elvitalyatalker.databinding.ActivityMainBinding
import com.elvitalyatalker.models.User
import com.elvitalyatalker.ui.fragments.ChatsFragment
import com.elvitalyatalker.ui.objects.AppDrawer
import com.elvitalyatalker.utilits.*
import com.theartofdev.edmodo.cropper.CropImage

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFields()
        initFunc()
    }


    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }
    }


    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        initFirebase()
        initUser()

    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
            .addListenerForSingleValueEvent(AppValueEventListener {
                USER = it.getValue(User::class.java) ?: User()
            })
    }




}