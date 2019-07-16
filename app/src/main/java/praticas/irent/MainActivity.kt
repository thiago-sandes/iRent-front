package praticas.irent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import praticas.irent.exibirOferta.ExibirOfertaActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbarMain)

        mainToolbarMain.setNavigationOnClickListener {
            startActivity<MainActivity>()
        }

        buttonBuscar.setOnClickListener {
            startActivity<ExibirOfertaActivity>()
        }

        buttonCriarAnuncio.setOnClickListener {
            startActivity<MainActivity>()
        }

        buttonCriarOferta.setOnClickListener {
            startActivity<CadastroOferta>()
        }
    }
}
