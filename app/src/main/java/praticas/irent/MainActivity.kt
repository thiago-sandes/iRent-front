package praticas.irent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbarMain)

        mainToolbarMain.setNavigationOnClickListener {
            startActivity<MainActivity>()
        }

        buttonBuscar.setOnClickListener(){
            startActivity<MainActivity>()
        }

        buttonCriarAnuncio.setOnClickListener(){
            startActivity<MainActivity>()
        }

        buttonCriarOferta.setOnClickListener(){
            startActivity<MainActivity>()
        }
    }
}
