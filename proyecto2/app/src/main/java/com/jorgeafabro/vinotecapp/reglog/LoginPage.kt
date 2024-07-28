package com.jorgeafabro.vinotecapp.reglog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.jorgeafabro.vinotecapp.MainActivity
import com.jorgeafabro.vinotecapp.R
import com.jorgeafabro.vinotecapp.databinding.ActivityLoginPageBinding
import com.jorgeafabro.vinotecapp.fileutils.ObjectData
import com.jorgeafabro.vinotecapp.fileutils.UserData
import com.jorgeafabro.vinotecapp.fileutils.WineData
import com.jorgeafabro.vinotecapp.navitems.ParaTi
import com.jorgeafabro.vinotecapp.splashscreen.DetailActivity
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

//  Pantalla de Inicio de Sesión, cuando ya estás iniciado ya no hace falta iniciar de nuevo, puedes cerrar sesión desde el menú.

class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        val persona = firebaseAuth.currentUser
        if(persona != null){
            val usuario: FirebaseUser? = firebaseAuth.currentUser

            recogerDatosPersona(usuario?.email)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
        }

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread.sleep(750)
        screenSplash.setKeepOnScreenCondition{false}

        binding.tvReg.setOnClickListener{
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        binding.btnIniciarSesion.setOnClickListener{
            val email = binding.TfLogEmail.text.toString()
            val pwd = binding.TfLogPwd.text.toString()

            if(email.isNotEmpty() && pwd.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener {
                    if (it.isSuccessful) {


                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    }
                    else {
                        Toast.makeText(this, "Error en el email o contraseña.", Toast.LENGTH_SHORT).show()
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

    // Recoge los datos de la persoma
    private fun recogerDatosPersona(email: String?) {
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("personas").document(email.toString())
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {


                    val email = task.result.get("nombre")
                    val pwd = task.result.get("correo")

                    ObjectData.usuario = UserData(email.toString(),
                        pwd.toString()
                    )

                } else {
                }
            } else{

        }}
    }

    private fun recogerDatosVino(){
        lifecycleScope.launch {
            val db = FirebaseFirestore.getInstance()
            val collectionRef = db.collection("vinos")
            val userList: MutableList<MutableMap<String, Any>> = mutableListOf()

            collectionRef.get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        val documentId = document.id
                        val documentData = document.data

                        val data = document.data
                        val userMap: MutableMap<String, Any> = mutableMapOf()
                        userMap.putAll(data)

                        // Agrega el objeto a la lista
                        userList.add(userMap)

                    }
                }
                .addOnFailureListener { exception ->
                }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {

    }
}