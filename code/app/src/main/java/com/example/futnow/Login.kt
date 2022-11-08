package com.example.futnow

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private val TAG = "Login"


    private var email: String? = null
    private var senha: String? = null


    private var tvForgotPassword: TextView? = null
    private var etEmail: EditText? = null
    private var etSenha: EditText? = null
    private var btnLogin: Button? = null
    private var etCadastro: TextView? = null
    private var progressBar: ProgressDialog? = null


    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColorTo(com.google.android.material.R.color.design_default_color_primary)
        }

        initialise()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColorTo(color: Int) {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        this.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

    private fun initialise() {
        tvForgotPassword = findViewById(R.id.esqueceuSenha) as TextView
        etCadastro = findViewById(R.id.cadastrase) as TextView
        etEmail = findViewById(R.id.editTextEmailLogin) as EditText
        etSenha = findViewById(R.id.editTextSenhaLogin) as EditText
        btnLogin = findViewById(R.id.btnLogin) as Button
        progressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!.setOnClickListener{ startActivity(Intent(this@Login, ForgotPassword::class.java))}

        etCadastro!!.setOnClickListener{ startActivity(Intent(this@Login, Cadastro::class.java)) }

        btnLogin!!.setOnClickListener{ loginUser() }
    }

    private fun loginUser() {
        email = etEmail?.text.toString()
        senha = etSenha?.text.toString()

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(senha)) {
            progressBar!!.setMessage("Verificando Usuário...")
            progressBar!!.show()

            Log.d(TAG, "Loggin do usuário")

            mAuth!!.signInWithEmailAndPassword(email!!, senha!!).addOnCompleteListener(this) {
                task ->

                progressBar!!.hide()

                if(task.isSuccessful) {
                    Log.d(TAG, "Logado com sucesso")
                    updateUi()
                } else {
                    Log.d(TAG, "Erro ao logar", task.exception)
                    Toast.makeText(this@Login, "Autenticação falhou", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this@Login, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi() {
        val intent = Intent(this@Login, Inicio::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}