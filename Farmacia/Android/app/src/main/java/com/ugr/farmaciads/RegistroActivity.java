package com.ugr.farmaciads;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ugr.farmaciads.data.RegisterService;

import p3.farmacia.modelo.Usuario;

public class RegistroActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText nombreEditText;
    private EditText nickEditText;
    private EditText passwordEditText;
    private Button registroButton;
    private ProgressBar loadingProgressBar;
    private TextView linkRegistro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEditText = findViewById(R.id.email);
        nombreEditText = findViewById(R.id.nombre);
        nickEditText = findViewById(R.id.nick);
        passwordEditText = findViewById(R.id.password);
        registroButton = findViewById(R.id.btn_registro);
        loadingProgressBar = findViewById(R.id.loading);
        linkRegistro = findViewById(R.id.link_login);

        registroButton.setOnClickListener((v) -> {
            registro();
        });

        linkRegistro.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            finish();
            startActivityForResult(intent, 1);
        });
    }

    private void registro() {
        registroButton.setEnabled(false);
        loadingProgressBar.setIndeterminate(true);
        if (!validate()) {
            registroButton.setEnabled(true);
            loadingProgressBar.setIndeterminate(false);
            return;
        }


        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String nombre = nombreEditText.getText().toString();
        String nick = nickEditText.getText().toString();

        RegisterService service = new RegisterService();
        service.register(email, nombre, nick, password, getApplicationContext(), new RegisterService.RegisterCallback() {

            @Override
            public void sucess(Usuario usuario) {
                RegistroActivity.this.runOnUiThread(() -> {
                    registroButton.setEnabled(true);
                    loadingProgressBar.setIndeterminate(false);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    finish();
                    startActivityForResult(intent, 1);
                });
            }

            @Override
            public void error() {
                RegistroActivity.this.runOnUiThread(() -> {
                    registroButton.setEnabled(true);
                    loadingProgressBar.setIndeterminate(false);
                    Toast.makeText(getApplicationContext(), "No se ha podido registrar", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String nombre = nombreEditText.getText().toString();
        String nick = nickEditText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email inválido");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (nombre.isEmpty()) {
            nombreEditText.setError("Nombre inválido");
            valid = false;
        } else {
            nombreEditText.setError(null);
        }

        if (nick.isEmpty()) {
            nickEditText.setError("Nick inválido");
            valid = false;
        } else {
            nickEditText.setError(null);
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