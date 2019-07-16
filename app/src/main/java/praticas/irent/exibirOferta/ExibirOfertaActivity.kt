package praticas.irent.exibirOferta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exibir_oferta.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import praticas.irent.R
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isComentario

class ExibirOfertaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibir_oferta)
        setSupportActionBar(exibirOfertaToolbarOferta)

        exibirOfertaEditTextAddComentario.doAfterTextChanged{
            habilitarPublicar(it)
        }

        exibirOfertaButtonPublicar.setOnClickListener {
            val comentario = exibirOfertaEditTextAddComentario.text.toString()
            publicarComentario(comentario)
        }

        exibirOfertaButtonMaisComentarios.setOnClickListener {
            startActivity<ComentariosActivity>()
        }
        exibirOfertaRatingBar.setOnClickListener(){
            val nota = exibirOfertaRatingBar.rating.toInt()

            toast(nota)
        }
    }

    fun habilitarPublicar(it: String) = it.isComentario().also{
        exibirOfertaButtonPublicar.isEnabled = it
    }
    fun publicarComentario(comentario: String){

    }

}
