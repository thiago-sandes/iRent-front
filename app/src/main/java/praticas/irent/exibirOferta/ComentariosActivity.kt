package praticas.irent.exibirOferta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_comentarios.*
import praticas.irent.R

class ComentariosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentarios)
        setSupportActionBar(exibirOfertaToolbarComentarios)

        exibirOfertaToolbarComentarios.setNavigationOnClickListener {
            finish()
        }
    }
}
