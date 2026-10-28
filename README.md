# Perfil do Usuário

App Android em Kotlin com tela de **Login usando FirebaseUI** (Firebase Authentication), opção de **cadastrar usuários** e tela de perfil com botão para **sair** e voltar para o login.

## Telas
- **LoginActivity** – tela inicial. Botão **Entrar** abre o login do FirebaseUI (e-mail e senha), igual ao modelo da documentação ([FirebaseUI](https://firebase.google.com/docs/auth/android/firebaseui?hl=pt-br)). Botão **Cadastrar usuário** abre a tela de cadastro.
- **CadastroActivity** – cadastro com nome, e-mail, senha e confirmação de senha (`createUserWithEmailAndPassword`). O nome fica salvo no perfil do usuário.
- **PerfilActivity** – mostra nome e e-mail do usuário logado e tem o botão **Sair** (`AuthUI.getInstance().signOut()`), que volta para a tela de login.

Se o usuário já estiver logado, o app abre direto no perfil.

## O que foi usado
- Kotlin + View Binding
- FirebaseUI Auth 9.0.0 (`AuthUI`, `FirebaseAuthUIActivityResultContract`, `registerForActivityResult`)
- Firebase Authentication (login por e-mail/senha ativado no console)
- Arquivo `app/google-services.json` do projeto Firebase **PerfilUsuario**

## Como rodar
Abrir no Android Studio, esperar o Gradle sincronizar e rodar no emulador ou celular (precisa de internet).
