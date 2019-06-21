package praticas.irent

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        setContentView(R.layout.activity_main)

        retrieveText.setOnClickListener {
            startActivity<RecuperarSenha>()
        }
        enterButton.setOnClickListener {
            startActivity<Inicial>()
        }
    }

    private fun entrar() {
        val email: EditText = findViewById(R.id.emailText)
        val senha: EditText = findViewById(R.id.passText)
        val sb = StringBuilder()
        sb.append(email).append(" ").append(senha)
        val str = sb.toString()
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

}
