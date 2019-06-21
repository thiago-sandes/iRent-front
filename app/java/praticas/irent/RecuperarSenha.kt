package praticas.irent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recuperar_senha.*
import org.jetbrains.anko.toast
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isEmail

class RecuperarSenha : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        textEmail.doAfterTextChanged {
            validateEmail(it)
        }

        buttonRecuperar.setOnClickListener {
            toast("Email enviado")
//            startActivity<MainActivity>()
        }
    }

    private fun validateEmail(it: String) = it.isEmail().also {
        buttonRecuperar.isEnabled = it
        if (!it) textEmail.error = "Email inválido"
    }
}
