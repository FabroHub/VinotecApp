package com.jorgeafabro.vinotecapp.reglog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.jorgeafabro.vinotecapp.databinding.ActivityRegisterPageBinding

//  Pantalla de registro de la aplicación.

class RegisterPage : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Llamamos al autentificador de Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        // Texto para ir a la pantalla de Registro
        binding.tvIniciarSesion.setOnClickListener{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        // Botón de registro
        binding.btnRegistrarse.setOnClickListener{
            val email = binding.TfRegEmail.text.toString()
            val pwd = binding.TfRegPwd.text.toString()

            if(email.isNotEmpty() && pwd.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if(email.isNotEmpty() && pwd.isEmpty()){
                Toast.makeText(this, "Falta por rellenar el campo 'Contraseña'.", Toast.LENGTH_SHORT).show()
            }
            else if(pwd.isNotEmpty() && email.isEmpty()){
                Toast.makeText(this, "Falta por rellenar el campo 'Email'.", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Por favor, rellene todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}