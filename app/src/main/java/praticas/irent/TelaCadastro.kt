package praticas.irent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.provider.MediaStore
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_cadastro_oferta.*
import kotlinx.android.synthetic.main.activity_tela_cadastro.*
import kotlinx.android.synthetic.main.activity_tela_cadastro.view.*
import okhttp3.ResponseBody
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isEmail
import praticas.irent.webservice.ApiUsuario
import praticas.irent.webservice.criarServicoUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TelaCadastro : AppCompatActivity() {

    var service: ApiUsuario? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)

        // Verificar se o email digitado é válido
        edit_email.doAfterTextChanged {
            validarEmail(it)
        }

        // Já possui cadastro e é redirecionado para tela de login
        id_ja_tenho_cadastro.setOnClickListener {
            var intent_cadastrado = Intent(this,TelaInicial::class.java)
            startActivity(intent_cadastrado)
        }

        // Criando ação para pegar foto da galeria do usuário
        id_selecionar_foto.setOnClickListener {
            // Verificando a permissão
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    // Permissão negada
                    val permissao = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    // Mostra popup requirindo a permissão
                    requestPermissions(permissao, TelaCadastro.PERMISSION_CODE)
                }else{
                    pegaImagemGaleria()
                }
            }else{
                // SO é <= Marshmallow
                pegaImagemGaleria()
            }
        }

        // Variáveis para termos de aceitação
        val termos : CheckBox = findViewById(R.id.checkBox)
        var cadastrar : Button = findViewById(R.id.btn_cadastrar)
        //val ver_termos : TextView = findViewById(R.id.id_view_termos)

        // Habilitar e desabilitar botão ao clicar no check box
        termos.setOnCheckedChangeListener { termos, isChecked ->
            if (isChecked) {
                cadastrar.isEnabled = true
            }else{
                cadastrar.isEnabled = false
            }
        }

        /* Mostrar alert com os termos de aceitação ao clicar no link
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Termos de Aceitação")
        builder.setMessage("Os termos são ....")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext, android.R.string.yes, Toast.LENGTH_SHORT).show()
        }

        val alert = builder.create()

        ver_termos.setOnClickListener { ver_termos ->
            alert.show()
        }
        */

        /* Botão para voltar a tela de login
        val intent_img_voltar  = Intent(this, TelaInicial::class.java)
        val imagem_voltar : ImageView = findViewById(R.id.img_view_voltar)

        imagem_voltar.setOnClickListener { startActivity(intent_img_voltar) }
        */

        // Ao clicar no botão cadastrar irei fazer...
        cadastrar.setOnClickListener {
            var username = input_usuario.editableText.toString()
            var email = edit_email.editableText.toString()
            var password = edit_senha.editableText.toString()
            var confirmar_senha = edit_confirmar_senha.editableText.toString()
            var telephone = edit_telefone.editableText.toString()
            var name = input_nome.editableText.toString()
            var grupo_sexo = findViewById(R.id.radio_group_sexo) as RadioGroup
            var foto = findViewById(R.id.id_selecionar_foto) as Button

            val intent_tela_inicial = Intent(this, TelaInicial::class.java)

            var sexo = ""

            // Atribuindo valores de sexo (M ou F)
            if(grupo_sexo.radio_masculino.isChecked){
                sexo = "M"
            }else{
                sexo = "F"
            }

            // Verificando se os campos estão preenchidos antes de cadastrar
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
                cadastrar(username, name, email, password, telephone, sexo, foto.toString())
                //startActivity(intent_tela_inicial)
            }

        }
    }

    // Função para pegar selecionar imagem da galeria
    private fun pegaImagemGaleria() {
        // Intent para pegar imagem
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        // Códio para pegar a imagem
        private val IMAGE_PICK_CODE = 1000;
        // Código da permissão
        private val PERMISSION_CODE = 1001;
    }

    // Exibindo permissão e verificando se a mesma foi aceita ou negada pelo usuário
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Aceitou a permissão
                    pegaImagemGaleria()
                }else{
                    // Permissão negada
                    Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    var UriFotoSelecionada : Uri? = null
    // Se a resposta da activity foi "OK", exiba a imagem selecionada na galeria no ImageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == Activity.RESULT_OK && requestCode == TelaCadastro.IMAGE_PICK_CODE && data != null){
            UriFotoSelecionada = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, UriFotoSelecionada)

            id_circle_view.setImageBitmap(bitmap)
            id_selecionar_foto.alpha = 0f
        }
    }

    fun validarEmail(it: String) = it.isEmail().also {
        if (!it) edit_email.error = "Email inválido!"
    }

    private fun cadastrar(username: String, name: String, email: String, password: String, telephone: String, sex: String, foto:String){
        var req: RequestUsuario = RequestUsuario(username, name, email, password, telephone, sex)
        var service : ApiUsuario = criarServicoUsuario()
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
                    //uploadImagemRetrofit()
                    alerta.show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(getApplicationContext(), "Erro interno no servidor." , Toast.LENGTH_SHORT).show()
            }
        })
    }
}

