package praticas.irent.filtros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_filtros.*
import praticas.irent.R

class FiltrosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros)
        setSupportActionBar(filtrosToolbar)
    }
}
