package praticas.irent

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tela_cadastro.*
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isEmail

class TelaCadastro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)

        edit_email.doAfterTextChanged {
            validarEmail(it)
        }
    }

    fun validarEmail(it: String) = it.isEmail().also {
        if (!it) edit_email.error = "Email inválido"
    }

    fun cadastrar(view : View){
        val nome: String = input_nome.editableText.toString()
        val email: String = edit_email.editableText.toString()
        val telefone: String = edit_telefone.editableText.toString()
        val usuario: String = input_usuario.editableText.toString()
        val senha: String = edit_senha.editableText.toString()
        val confirmar_senha: String = edit_confirmar_senha.editableText.toString()

        val builder = AlertDialog.Builder(this)

        builder.setMessage("Cadastro realizado com sucesso!")
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.ok, Toast.LENGTH_SHORT).show()
        }

        val alert = builder.create()
        val intent_inicial = Intent(this, MainActivity::class.java)

        if(TextUtils.isEmpty(nome)){
            edit_nome.error = (getString(R.string.erro_campo_obrigatorio))
        }else if(TextUtils.isEmpty(email)){
            edit_email.error = (getString(R.string.erro_campo_obrigatorio))
        }else if(TextUtils.isEmpty(telefone)){
            edit_telefone.error = (getString(R.string.erro_campo_obrigatorio))
        }else if(TextUtils.isEmpty(usuario)){
            edit_usuario.error = (getString(R.string.erro_campo_obrigatorio))
        }else if(TextUtils.isEmpty(senha)){
            edit_senha.error = (getString(R.string.erro_campo_obrigatorio))
        }else if(senha != confirmar_senha){
            edit_confirmar_senha.error = (getString(R.string.erro_confirmar_senha))
        }else{
            alert.show()
            startActivity(intent_inicial)
        }
    }

}

