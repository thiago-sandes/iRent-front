package praticas.irent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_cadastro_oferta.*
import okhttp3.ResponseBody
import praticas.irent.model.RequestOferta
import praticas.irent.webservice.ApiUsuario
import praticas.irent.webservice.criarServicoUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroOfertaActivity : AppCompatActivity() {

    var service: ApiUsuario? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_oferta)

        // setOnClickListener parâmetros:
            // (<qual o contexto da aplicação>, <o texto a ser exibido>, <tamanho do texto>)

        // Botão para cadastrar oferta
        btn_ofertar.setOnClickListener {
            var titulo_oferta : String = input_titulo.editableText.toString()
            var descricao : String = edit_descricao.editableText.toString()
            var preco : String = edit_preco.editableText.toString()
            var endereco : String = input_endereco.editableText.toString()
            var restricoes : String = ""
            //var foto : ImageView = findViewById(R.id.id_imgView_oferta)

            if(check_animal.isChecked)
                restricoes = "Animal"
            if(check_meninas.isChecked)
                restricoes = "Meninas"
            if(check_meninos.isChecked)
                restricoes = "Meninos"
            if(check_som_alto.isChecked)
                restricoes = "Som Alto"

            val builder = AlertDialog.Builder(this);

            builder.setMessage("Oferta cadastrada com sucesso!");
            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.ok, Toast.LENGTH_SHORT).show()
            }

            val alert = builder.create();

            // Verificando se campos obrigatórios estão preenchidos
            if(TextUtils.isEmpty(titulo_oferta)){
                edit_titulo_oferta.error = (getString(R.string.erro_campo_obrigatorio))
            }else if(TextUtils.isEmpty(descricao)){
                edit_descricao.error = (getString(R.string.erro_campo_obrigatorio))
            }else if(TextUtils.isEmpty(preco)){
                edit_preco.error = (getString(R.string.erro_campo_obrigatorio))
            }else if(TextUtils.isEmpty(endereco)){
                id_endereco.error = ("Campo obrigatório!")
            }else if(preco.toDouble() <= 0){
                edit_preco.error = (getString(R.string.erro_preco_negativo))
            }else{
                cadastrarOferta(titulo_oferta,descricao,endereco,preco,restricoes)
            }
        }

        // Image View da oferta
        id_imgView_oferta.setOnClickListener {
            // Verificando a permissão
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    // Permissão negada
                    val permissao = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    // Mostra popup requirindo a permissão
                    requestPermissions(permissao, PERMISSION_CODE)
                }else{
                    pegaImagemGaleria()
                }
            }else{
                // SO é <= Marshmallow
                pegaImagemGaleria()
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

    // Se a resposta da activity foi "OK", exiba a imagem selecionada na galeria no ImageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            id_imgView_oferta.setImageURI(data?.data)
        }
    }

    // Função para cadastrar oferta
    private fun cadastrarOferta(titulo: String, descricao: String, endereco: String, preco: String, restricoes: String){
        var req: RequestOferta = RequestOferta(titulo, descricao, endereco, preco, restricoes)
        var service : ApiUsuario = criarServicoUsuario()
        var response: Call<ResponseBody>? = service?.ofertaCadastro(req)

        var intent = Intent(this, TelaInicialActivity::class.java)

        var builder = AlertDialog.Builder(this)

        builder.setTitle("Cadastro de Oferta")
        builder.setMessage("Oferta cadastrada com sucesso!")
        builder.setPositiveButton("Ok"){dialog, which ->
            Toast.makeText(applicationContext, "Ok", Toast.LENGTH_LONG).show()
        }

        val alerta = builder.create()

        // Response
        response?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(!response.isSuccessful){
                    Toast.makeText(getApplicationContext(), "Oferta já cadastrada" , Toast.LENGTH_SHORT).show()
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