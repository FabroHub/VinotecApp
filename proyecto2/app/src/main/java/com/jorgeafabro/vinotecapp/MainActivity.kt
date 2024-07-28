package com.jorgeafabro.vinotecapp

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jorgeafabro.vinotecapp.databinding.ActivityMainBinding
import com.jorgeafabro.vinotecapp.navitems.*
import com.jorgeafabro.vinotecapp.reglog.LoginPage

//  Pantalla principal en el que aparerán todas las pantallas menos el Login y el Register.

enum class ProviderType{
    BASIC
}

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.abrir, R.string.cerrar)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            //  Para que tenga funcionalidad el menu
            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.paraTiItem->{
                        Snackbar.make(fragmentContainer, R.string.pti, Snackbar.LENGTH_SHORT).show()
                        replaceFragment(ParaTi())
                    }
                    R.id.buscadorItem->{
                        Snackbar.make(fragmentContainer, R.string.buscador, Snackbar.LENGTH_SHORT).show()
                        replaceFragment(Buscador())
                    }
                    R.id.LinkedIn->{
                        val url = "https://www.linkedin.com/in/jorge-fabro-garc%C3%ADa-121499289/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return@setNavigationItemSelectedListener true
                    }
                    R.id.Twitch->{
                        val url = "https://www.twitch.tv/fabro324"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return@setNavigationItemSelectedListener true
                    }
                    R.id.YT->{
                        val url = "https://www.youtube.com/@fabr0324"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return@setNavigationItemSelectedListener true
                    }
                    R.id.TikTok->{
                        val url = "https://www.tiktok.com/@faabro15"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return@setNavigationItemSelectedListener true
                    }
                    R.id.Insta->{
                        val url = "https://www.instagram.com/fabro324.tv"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return@setNavigationItemSelectedListener true
                    }
                    R.id.cerrarSesion -> singOut()
                }
                true
            }
        }
        val auth = Firebase.auth
        val user = auth.currentUser
        val email = user?.email
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        tvEmail.text = email

        tvEmail.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", tvEmail.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Email copiado al portapapeles.", Toast.LENGTH_SHORT).show()
        }

        val semen = findViewById<View>(R.id.semen)
        semen.setOnClickListener {
            val url = "https://github.com/FabroHub/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    //  Te permite abrir el menu desplegable
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    //  Cerrar sesión y vuelta a la pestaña de Inicio de Sesión
    private fun singOut(){
        val auth = FirebaseAuth.getInstance()
        auth.signOut()

        val intent = Intent(this@MainActivity, LoginPage::class.java)
        startActivity(intent)

        Toast.makeText(this, R.string.cerrarSesion, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    //para poder cambiar entre Fragments
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    //  Deshabilitar el botón "Atrás"
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}