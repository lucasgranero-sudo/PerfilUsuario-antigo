package br.unisanta.perfilusuario

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unisanta.perfilusuario.databinding.ActivityLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    // Igual a documentacao do FirebaseUI: launcher que recebe o resultado do login
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // se o usuario ja estiver logado vai direto para o perfil
        if (FirebaseAuth.getInstance().currentUser != null) {
            abrirPerfil()
            return
        }

        binding.btnEntrar.setOnClickListener {
            createSignInIntent()
        }

        binding.btnCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }

    private fun createSignInIntent() {
        // Escolhe os provedores de login (e-mail e senha)
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder()
                .setAllowNewAccounts(true) // permite criar conta pela tela do FirebaseUI tambem
                .build()
        )

        // Cria e abre a tela de login do FirebaseUI
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Login feito com sucesso
            Toast.makeText(this, "Login realizado!", Toast.LENGTH_SHORT).show()
            abrirPerfil()
        } else {
            // Login falhou ou o usuario apertou voltar
            if (response == null) {
                Toast.makeText(this, "Login cancelado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro: " + response.error?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun abrirPerfil() {
        startActivity(Intent(this, PerfilActivity::class.java))
        finish()
    }
}
