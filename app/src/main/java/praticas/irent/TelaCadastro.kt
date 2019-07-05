package praticas.irent

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_tela_cadastro.*
import kotlinx.android.synthetic.main.activity_tela_cadastro.view.*
import okhttp3.ResponseBody
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isEmail
import praticas.irent.webservice.ApiUsuario
import praticas.irent.webservice.createUserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TelaCadastro : AppCompatActivity() {

    var service: ApiUsuario? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)

        edit_email.doAfterTextChanged {
            validarEmail(it)
        }

        btn_cadastrar.setOnClickListener {
            var username = input_usuario.editableText.toString()
            var email = edit_email.editableText.toString()
            var password = edit_senha.editableText.toString()
            var confirmar_senha = edit_confirmar_senha.editableText.toString()
            var telephone = edit_telefone.editableText.toString()
            var name = input_nome.editableText.toString()
            var grupo_sexo = findViewById(R.id.radio_group_sexo) as RadioGroup

            var sexo = ""

            if(grupo_sexo.radio_masculino.isChecked){
                sexo = "M"
            }else{
                sexo = "F"
            }

            if(TextUtils.isEmpty(name)){
                edit_nome.error = "Campo obrigatório!"
            }else if(TextUtils.isEmpty(email)) {
                edit_email.error = "Campo obrigatório!"
            }else if(TextUtils.isEmpty(telephone)) {
                edit_telefone.error = "Campo obrigatório!"
            }else if(TextUtils.isEmpty(username)) {
                edit_usuario.error = "Campo obrigatório!"
            }else if(TextUtils.isEmpty(password)) {
                edit_senha.error = "Campo obrigatório!"
            }else if(password != confirmar_senha) {
                edit_confirmar_senha.error = "Senha não confere!"
            }else{
                cadastrar(username, name, email, password, telephone, sexo)
            }

        }
    }

    fun validarEmail(it: String) = it.isEmail().also {
        if (!it) edit_email.error = "Email inválido!"
    }

    private fun cadastrar(username: String, name: String, email: String, password: String, telephone: String, sex: String){
        var req: RequestUsuario = RequestUsuario(username, name, email, password, telephone, sex)
        var service : ApiUsuario = createUserService()
        var response: Call<ResponseBody>? = service?.userCadastro(req)

        var intent = Intent(this, TelaInicial::class.java)

        var builder = AlertDialog.Builder(this)

        builder.setTitle("Cadastro de Usuário")
        builder.setMessage("Cadastro realizado com sucesso!")
        builder.setPositiveButton("Ok"){dialog, which ->
            Toast.makeText(applicationContext, "Ok", Toast.LENGTH_LONG).show()
        }

        val alerta = builder.create()

        response?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(!response.isSuccessful){
                    Toast.makeText(getApplicationContext(), "ERRO: "+response.code() , Toast.LENGTH_SHORT).show()
                }else{
                    alerta.show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(getApplicationContext(), "Erro interno no servidor." , Toast.LENGTH_SHORT).show()
            }
        })
    }

}

