package com.example.present_recommendation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class MainActivity : Activity() {


    // 파이어베이스 인증 객체 생성
    private var firebaseAuth: FirebaseAuth? = null

    // 이메일과 비밀번호
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        val intent= Intent(this, StartActivity::class.java)
        startActivity(intent)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.edtId)
        editTextPassword = findViewById(R.id.edtPwd)

    }

    fun singUp(view: View?) {
        email = editTextEmail!!.text.toString()
        password = editTextPassword!!.text.toString()
        if (isValidEmail && isValidPasswd) {
            createUser(email, password)
        }
    }

    fun signIn(view: View?) {
        //fun signIn() {
        email = editTextEmail!!.text.toString()
        password = editTextPassword!!.text.toString()
        if (isValidEmail && isValidPasswd) {
            loginUser(email, password)
        }
    }// 이메일 형식 불일치// 이메일 공백

    // 이메일 유효성 검사
    private val isValidEmail: Boolean
        private get() = if (email.isEmpty()) {
            Toast.makeText(this@MainActivity, "이메일 공백 에러", Toast.LENGTH_SHORT).show()

            // 이메일 공백
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this@MainActivity, "이메일 형식 불일치 에러", Toast.LENGTH_SHORT).show()

            // 이메일 형식 불일치
            false
        } else {
            true
        }// 비밀번호 형식 불일치// 비밀번호 공백

    // 비밀번호 유효성 검사
    private val isValidPasswd: Boolean
        private get() {
            return if (password.isEmpty()) {
                Toast.makeText(this@MainActivity, "비밀번호 공백 에러", Toast.LENGTH_SHORT).show()
                // 비밀번호 공백
                false
            } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                Toast.makeText(this@MainActivity, "비밀번호 형식 불일치 에러", Toast.LENGTH_SHORT).show()

                // 비밀번호 형식 불일치
                false
            } else {
                true
            }
        }

    // 회원가입
    private fun createUser(email: String, password: String) {
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        this
                ) { task ->
                    if (task.isSuccessful) {
                        // 회원가입 성공
                        Toast.makeText(this@MainActivity,R.string.success_signup,Toast.LENGTH_SHORT).show()
                    } else {
                        // 회원가입 실패
                        Toast.makeText(this@MainActivity,R.string.failed_signup, Toast.LENGTH_SHORT).show()
                    }
                }
    }

    // 로그인
    private fun loginUser(email: String, password: String) {
        firebaseAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        this
                ) { task ->
                    if (task.isSuccessful) {

                        // 로그인 성공
                        Toast.makeText(this@MainActivity,R.string.success_login,Toast.LENGTH_SHORT).show()

                        val intent = Intent(application, MenuActivity::class.java)
                        startActivity(intent)


                    } else {
                        // 로그인 실패
                        Toast.makeText(this@MainActivity, R.string.failed_login, Toast.LENGTH_SHORT).show()
                    }
                }
    }

    companion object {
        // 비밀번호 정규식
        private val PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$")
    }
}
