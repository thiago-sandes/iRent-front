package praticas.irent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recuperar_senha.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isEmail

class RecuperarSenhaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        setSupportActionBar(recuperarToolbar)

        recuperarSenhaEmailEditText.doAfterTextChanged {
            validateEmail(it)
        }

        recuperarToolbar.setNavigationOnClickListener {
            finish()
        }

        buttonRecuperar.setOnClickListener {

            toast("Email enviado")
            startActivity<LoginActivity>()
        }
    }

    private fun validateEmail(it: String) = it.isEmail().also {
        buttonRecuperar.isEnabled = it
        recuperarSenhaEmailInputLayout.error = if (!it) "Email inválido" else null
    }
}
