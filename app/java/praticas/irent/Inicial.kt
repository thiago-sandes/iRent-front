package praticas.irent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_inicial.*
import org.jetbrains.anko.startActivity

class Inicial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)

        buttonBuscar.setOnClickListener(){
            startActivity<Inicial>()
        }

        buttonCriarAnuncio.setOnClickListener(){
            startActivity<Inicial>()
        }

        buttonCriarOferta.setOnClickListener(){
            startActivity<Inicial>()
        }
    }
}
