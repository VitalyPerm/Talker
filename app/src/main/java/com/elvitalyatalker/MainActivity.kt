package com.elvitalyatalker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.elvitalyatalker.activities.RegisterActivity
import com.elvitalyatalker.databinding.ActivityMainBinding
import com.elvitalyatalker.ui.fragments.ChatsFragment
import com.elvitalyatalker.ui.objects.AppDrawer
import com.elvitalyatalker.utilits.AUTH
import com.elvitalyatalker.utilits.initFirebase
import com.elvitalyatalker.utilits.replaceActivity
import com.elvitalyatalker.utilits.replaceFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(),false)
        } else {
            replaceActivity(RegisterActivity())
        }
    }


    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        initFirebase()

    }

}