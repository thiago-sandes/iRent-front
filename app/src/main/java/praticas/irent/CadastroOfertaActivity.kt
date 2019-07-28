package praticas.irent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toFile
import kotlinx.android.synthetic.main.activity_cadastro_oferta.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import praticas.irent.model.RequestOferta
import praticas.irent.webservice.ApiUsuario
import praticas.irent.webservice.criarServicoUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.RequestBody
import okhttp3.MultipartBody
import okhttp3.internal.io.FileSystem
import praticas.irent.model.RequestImages
import java.io.File


class CadastroOfertaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_oferta)

        // setOnClickListener parâmetros:
            // (<qual o contexto da aplicação>, <o texto a ser exibido>, <tamanho do texto>)

        // Botão para chamar a tela de anexo de foto(s)
        anexar_foto.setOnClickListener {
            val intent_imagem_oferta = Intent(this,ImagemOfertaActivity::class.java)
            startActivity(intent_imagem_oferta)
        }

        // Botão para cadastrar oferta
        btn_ofertar.setOnClickListener {
            var titulo_oferta : String = input_titulo.editableText.toString()
            var descricao : String = edit_descricao.editableText.toString()
            var preco : String = edit_preco.editableText.toString()
            var telefone : String = edit_telefone_contato.editableText.toString()
            var restricao : String = input_restricoes.editableText.toString()
            var endereco_id = null

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
            }else if(TextUtils.isEmpty(telefone)){
                edit_telefone_contato.error = (getString(R.string.erro_campo_obrigatorio))
            }else if(TextUtils.isEmpty(preco)){
                edit_preco.error = (getString(R.string.erro_campo_obrigatorio))
            }else if(preco.toDouble() <= 0){
                edit_preco.error = (getString(R.string.erro_preco_negativo))
            }else{
                cadastrarOferta(1,endereco_id,titulo_oferta,telefone,descricao,preco,restricao)
            }
        }
    }

    // Função para cadastrar oferta
    private fun cadastrarOferta(user_id: Int,endereco_id: Int?,titulo: String,telefone: String,descricao: String,preco: String,
                                restricao: String){
        var req: RequestOferta = RequestOferta(user_id,endereco_id,titulo,telefone,descricao,preco,restricao)
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
