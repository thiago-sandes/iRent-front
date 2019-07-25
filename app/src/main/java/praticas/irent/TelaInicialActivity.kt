package praticas.irent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TelaInicialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)
    }

    fun chamar_busca(view : View){
        //val intent_busca = Intent(this,TelaBusca::class.java);
    }

    fun chamar_criar_oferta(view : View){
        val intent_ofertar = Intent(this,CadastroOfertaActivity::class.java);

        startActivity(intent_ofertar);
    }

    fun chamar_criar_anuncio(view : View){
        //val intent_anunciar = Intent(this,CadastroAnuncio::class.java);
    }
}
