package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.core.utils.Utils
import com.example.lesson.LessonActivity

class MainActivity: AppCompatActivity(), View.OnClickListener {
    private val usernameKey = "username"
    private val passwordKey = "password"

    private lateinit var et_username: EditText
    private lateinit var et_password: EditText
    private lateinit var et_code: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_username = findViewById<EditText>(R.id.et_username).apply {
            setText(CacheUtils.get(usernameKey))
        }
        et_password = findViewById<EditText?>(R.id.et_password).apply {
            setText(CacheUtils.get(passwordKey))
        }
        et_code = findViewById(R.id.et_code)


        findViewById<Button>(R.id.btn_login).apply {
            setOnClickListener(this@MainActivity)
        }
        findViewById<CodeView>(R.id.code_view).apply {
            setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View) {
        when(v) {
            is CodeView -> v.updateCode()
            is Button -> login()
        }
    }

    private fun login() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        val code = et_code.text.toString()
        val user = User(username, password, code)
        if (verify(user)) {
            CacheUtils.save(usernameKey, username)
            CacheUtils.save(passwordKey, password)
            startActivity(Intent(this, LessonActivity::class.java))
        }
    }

    private fun verify(user: User): Boolean {
        user.username?.let {
            if (it.length < 4) {
                Utils.toast("??????????????????")
                return false
            }
        }
        user.password?.let {
            if(it.length < 4) {
                Utils.toast("???????????????")
                return false
            }
        }
        return true
    }

}