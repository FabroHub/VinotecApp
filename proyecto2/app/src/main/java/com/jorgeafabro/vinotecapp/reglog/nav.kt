package com.jorgeafabro.vinotecapp.reglog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.jorgeafabro.vinotecapp.ProviderType
import com.jorgeafabro.vinotecapp.R
import com.jorgeafabro.vinotecapp.databinding.FragmentNavBinding
import kotlinx.coroutines.runInterruptible

//
/*
class Nav : Fragment() {

    private var _binding: FragmentNavBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.nav_header, container, false)
        // Inflate the layout for this fragment



        val email = firebaseAuth.currentUser
        email?.let {
            val emailUsuario = email.displayName
            val navEmailTextView = view.findViewById<TextView>(R.id.nav_email)
            //navEmailTextView.setText(nombreUsuario)
            navEmailTextView.text = emailUsuario
        }

        //email(view)
        val email2: String? = savedInstanceState?.getString("email")
        setup(email = email2 ?: "")

        return view
    }

    /*private fun email(view: View){

    }*/

    /*private fun showHome(email: String, provider: ProviderType){

    }*/

    private fun setup(email:String){
        val navEmailTextView = view?.findViewById<TextView>(R.id.nav_email)
        navEmailTextView?.text = email
    }

}*/

/*class Nav : Fragment() {

    private var _binding: FragmentNavBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var navEmailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inicializar el TextView utilizando findViewById
        navEmailTextView = view.findViewById(R.id.nav_email)

        val email = firebaseAuth.currentUser?.email
        email?.let {
            // Establecer el correo electrónico en el TextView
            navEmailTextView.text = it
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/

