package com.example.futnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    private val TAG = "ForgotPassword"

    private var etEmail: EditText? = null
    private var btnRecuperar: Button? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initialise()
    }

    private fun initialise() {
        etEmail = findViewById(R.id.editTextEmailRecuperarSenha) as EditText
        btnRecuperar = findViewById(R.id.btnRecuperarSenha) as Button

        mAuth = FirebaseAuth.getInstance()

        btnRecuperar!!.setOnClickListener{ sendPasswordEmail() }
    }

    private fun sendPasswordEmail() {
        val email = etEmail?.text.toString()

        if(!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener{
                    task ->

                    val message = "Email Enviado"
                    if(task.isSuccessful) {

                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        updateUi()
                    } else {
                        Log.w(TAG, message, task.exception)
                        Toast.makeText(this, "Nenhum usuario encontrado, com este email.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Entre com um email válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi() {
        val intent = Intent(this@ForgotPassword, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}