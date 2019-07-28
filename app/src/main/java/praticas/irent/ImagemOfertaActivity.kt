package praticas.irent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_imagem_oferta.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import praticas.irent.R
import praticas.irent.model.RequestImages
import praticas.irent.webservice.ApiUsuario
import praticas.irent.webservice.criarServicoUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import androidx.core.net.toFile
import praticas.irent.model.RequestOferta
import android.content.Context
import android.database.Cursor


class ImagemOfertaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagem_oferta)

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

        // Botão para cadastrar imagens de oferta
        btn_imagem_oferta.setOnClickListener {
            uploadFile(10,"1564095867487-2013-01-15-095338")
        }
    }

    // Função para pegar selecionar imagem da galeria
    private fun pegaImagemGaleria() {
        // Intent para pegar imagem
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = (Intent.ACTION_GET_CONTENT)

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

    fun getImagePath(contentUri: Uri): String {
        val campos = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, campos, null, null, null)
        cursor!!.moveToFirst()
        val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
        cursor.close()
        return path
    }

    var UriFotoSelecionada: Uri? = null
    // Se a resposta da activity foi "OK", exiba a imagem selecionada na galeria no ImageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == ImagemOfertaActivity.IMAGE_PICK_CODE && data != null){
            UriFotoSelecionada = data?.data
            id_imgView_oferta.setImageURI(UriFotoSelecionada)
            Toast.makeText(applicationContext, "File: "+UriFotoSelecionada, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } catch (e: Exception) {
            //Log.e(FragmentActivity.TAG, "getRealPathFromURI Exception : $e")
            return ""
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }

    private fun uploadFile(id: Int, path: String){
        //val file = getRealPathFromURI(this,fileUri)\

        var service : ApiUsuario = criarServicoUsuario()
        val req : RequestImages = RequestImages(10,"1564095867487-2013-01-15-095338")
        var response: Call<ResponseBody>? = service?.upload(req)

        //Toast.makeText(applicationContext,"File path: "+file, Toast.LENGTH_SHORT).show()

        response?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>){
                if(!response.isSuccessful)
                    Toast.makeText(applicationContext, "Erro no upload!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(applicationContext, "Foto cadastrada", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext, "Erro!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
