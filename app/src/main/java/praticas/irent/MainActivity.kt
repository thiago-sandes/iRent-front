package com.example.irentlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import praticas.irent.R
import praticas.irent.RecuperarSenha

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        this.getWindow()
            .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        retrieveText.setOnClickListener(){
            startActivity<RecuperarSenha>()
        }
    }

    private fun entrar() {
        val email: EditText = findViewById(R.id.emailText)
        val senha: EditText = findViewById(R.id.passText)
        val sb = StringBuilder()
        //sb.append(email).append(" ").append(senha)
       // val str = sb.toString()
       // Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}
