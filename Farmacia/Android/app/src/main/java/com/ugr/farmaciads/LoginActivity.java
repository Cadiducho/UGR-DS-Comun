package com.ugr.farmaciads;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ugr.farmaciads.data.LoginService;

import p3.farmacia.modelo.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    private TextView linkRegistro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_registro);
        loadingProgressBar = findViewById(R.id.loading);
        linkRegistro = findViewById(R.id.link_registrar);

        loginButton.setOnClickListener((v) -> {
            login();
        });

        linkRegistro.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
            finish();
            startActivityForResult(intent, 1);
        });
    }

    private void login() {
        loginButton.setEnabled(false);
        loadingProgressBar.setIndeterminate(true);
        if (!validate()) {
            loginButton.setEnabled(true);
            loadingProgressBar.setIndeterminate(false);
            return;
        }


        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        LoginService service = new LoginService();
        service.login(email, password, getApplicationContext(), new LoginService.LoginCallback() {

            @Override
            public void sucess(Usuario usuario) {
                SharedPreferences.Editor editor = getSharedPreferences("ds", MODE_PRIVATE).edit();
                editor.putString("email", usuario.getEmail());
                editor.putString("name", usuario.getNombre());
                editor.putString("rol", usuario.getRol());
                editor.putInt("id", usuario.getId());
                editor.apply();

                LoginActivity.this.runOnUiThread(() -> {
                    loginButton.setEnabled(true);
                    loadingProgressBar.setIndeterminate(false);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivityForResult(intent,1);
                });
            }

            @Override
            public void error() {
                LoginActivity.this.runOnUiThread(() -> {
                    loginButton.setEnabled(true);
                    loadingProgressBar.setIndeterminate(false);
                    Toast.makeText(getApplicationContext(), "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email inválido");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Contraseña inválida");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }
}
