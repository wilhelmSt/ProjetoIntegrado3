package com.example.futnow

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Cadastro : AppCompatActivity() {

    private var etNome: EditText? = null
    private var etEmail: EditText? = null
    private var etSenha: EditText? = null
    private var etRSenha: EditText? = null
    private var etCPF: EditText? = null
    private var btnCadastro: Button? = null
    private var progressBar: ProgressDialog? = null


    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null


    private var TAG = "Cadastro"

    private var Nome: String? = null
    private var Email: String? = null
    private var Senha: String? = null
    private var RSenha: String? = null
    private var CPF: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        initialise()
    }

    private fun initialise() {
        etNome = findViewById(R.id.editTextNomeCadastro) as EditText
        etEmail = findViewById(R.id.editTextEmailCadastro) as EditText
        etSenha = findViewById(R.id.editTextSenhaCadastro) as EditText
        etRSenha = findViewById(R.id.editTextRSenhaCadastro) as EditText
        etCPF = findViewById(R.id.editTextCPFCadastro) as EditText
        btnCadastro = findViewById(R.id.btnCadastro) as Button
        progressBar = ProgressDialog(this)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnCadastro!!.setOnClickListener{ createNewAccount() }

    }

    private fun createNewAccount() {
        Nome = etNome?.text.toString()
        Email = etEmail?.text.toString()
        Senha = etSenha?.text.toString()
        RSenha = etRSenha?.text.toString()
        CPF = etCPF?.text.toString()

        if (
            !TextUtils.isEmpty(Nome) &&
            !TextUtils.isEmpty(Email) &&
            !TextUtils.isEmpty(Senha) &&
            !TextUtils.isEmpty(RSenha) &&
            !TextUtils.isEmpty(CPF)
        ) {
            Toast.makeText(this, "Informações preenchidas corretamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Entre com mais Detalhes", Toast.LENGTH_SHORT).show()
        }

        progressBar!!.setMessage("Registrando usuário...")
        progressBar!!.show()

        mAuth!!.createUserWithEmailAndPassword(Email!!, Senha!!).addOnCompleteListener(this) { task->
            progressBar!!.hide()

            if(task.isSuccessful) {
                Log.d(TAG, "CreateUserWithEmail: Sucess")

                val userId = mAuth!!.currentUser!!.uid

                verifyEmail()

                val currentUserDb = mDatabaseReference!!.child(userId)
                currentUserDb.child("Nome").setValue(Nome)
                currentUserDb.child("CPF").setValue(CPF)

                updateUserInfoAndUi()
            } else {
                Log.w(TAG, "CreateUserWithEmail: Failed", task.exception)
                Toast.makeText(this@Cadastro, "Autenticação falhou.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserInfoAndUi() {
        val intent = Intent(this@Cadastro, Inicio::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification().addOnCompleteListener(this) { task ->
            if(task.isSuccessful) {
                Toast.makeText(this@Cadastro, "Verificação de email enviada para" + mUser.getEmail(),
                    Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "SendEmailVerification", task.exception)
                Toast.makeText(this@Cadastro, "Falha ao enviar verificação de email",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}