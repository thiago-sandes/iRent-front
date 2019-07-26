package praticas.irent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cadastro_usuario.*
import kotlinx.android.synthetic.main.activity_cadastro_usuario.view.*
import okhttp3.ResponseBody
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isEmail
import praticas.irent.model.RequestUsuario
import praticas.irent.webservice.ApiUsuario
import praticas.irent.webservice.criarServicoUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroUsuarioActivity : AppCompatActivity() {

    var service: ApiUsuario? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        // Verificar se o email digitado é válido
        edit_email.doAfterTextChanged {
            validarEmail(it)
        }

        // Já possui cadastro e é redirecionado para tela de login
        id_ja_tenho_cadastro.setOnClickListener {
            var intent_cadastrado = Intent(this,TelaInicialActivity::class.java)
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
                    requestPermissions(permissao, CadastroUsuarioActivity.PERMISSION_CODE)
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

        // Habilitar e desabilitar botão ao clicar no check box
        termos.setOnCheckedChangeListener { termos, isChecked ->
            if (isChecked) {
                cadastrar.isEnabled = true
            }else{
                cadastrar.isEnabled = false
            }
        }


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

            val intent_tela_inicial = Intent(this, TelaInicialActivity::class.java)

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

        if(resultCode == Activity.RESULT_OK && requestCode == CadastroUsuarioActivity.IMAGE_PICK_CODE && data != null){
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
        var req: RequestUsuario =
            RequestUsuario(username, name, email, password, telephone, sex)
        var service : ApiUsuario = criarServicoUsuario()
        var response: Call<ResponseBody>? = service?.userCadastro(req)

        var intent = Intent(this, MainActivity::class.java)

        var builder = AlertDialog.Builder(this)

        builder.setTitle("Cadastro de Usuário")
        builder.setMessage("Cadastro realizado com sucesso!")
        builder.setPositiveButton("Ok"){dialog, which ->
            Toast.makeText(applicationContext, "Ok", Toast.LENGTH_LONG).show()
        }

        val alerta = builder.create()

        response?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(!response.isSuccessful || response.body().toString() == username){
                    Toast.makeText(getApplicationContext(), "Usuário já cadastrado" , Toast.LENGTH_SHORT).show()
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

