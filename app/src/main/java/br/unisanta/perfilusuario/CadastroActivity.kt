package br.unisanta.perfilusuario

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unisanta.perfilusuario.databinding.ActivityCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSalvar.setOnClickListener {
            cadastrarUsuario()
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun cadastrarUsuario() {
        val nome = binding.edtNome.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val senha = binding.edtSenha.text.toString()
        val confirmar = binding.edtConfirmarSenha.text.toString()

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }
        if (senha.length < 6) {
            Toast.makeText(this, "A senha precisa ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }
        if (senha != confirmar) {
            Toast.makeText(this, "As senhas não são iguais", Toast.LENGTH_SHORT).show()
            return
        }

        binding.btnSalvar.isEnabled = false

        // cria o usuario no Firebase Authentication
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { tarefa ->
                if (tarefa.isSuccessful) {
                    // salva o nome do usuario no perfil
                    val perfil = UserProfileChangeRequest.Builder()
                        .setDisplayName(nome)
                        .build()
                    auth.currentUser?.updateProfile(perfil)?.addOnCompleteListener {
                        Toast.makeText(this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, PerfilActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    binding.btnSalvar.isEnabled = true
                    Toast.makeText(this, "Erro ao cadastrar: " + tarefa.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}
