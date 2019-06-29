package praticas.irent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_cadastro_oferta.*

class CadastroOferta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_oferta)
    }

    fun criar_oferta(view : View){
        val titulo_oferta : String = input_titulo.editableText.toString();
        val descricao : String = edit_descricao.editableText.toString();
        val preco : String = edit_preco.editableText.toString();

        val builder = AlertDialog.Builder(this);

        builder.setMessage("Oferta cadastrada com sucesso!");
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.ok, Toast.LENGTH_SHORT).show()
        }

        val alert = builder.create();

        if(TextUtils.isEmpty(titulo_oferta)){
            edit_titulo_oferta.error = (getString(R.string.erro_campo_obrigatorio));
        }else if(TextUtils.isEmpty(descricao)){
            edit_descricao.error = (getString(R.string.erro_campo_obrigatorio));
        }else if(TextUtils.isEmpty(preco)){
            edit_preco.error = (getString(R.string.erro_campo_obrigatorio));
        }else if(preco.toDouble() <= 0){
            edit_preco.error = (getString(R.string.erro_preco_negativo))
        }else{
            alert.show();
        }
    }
}
