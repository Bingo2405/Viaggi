package com.example.viaggi

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import javax.net.ssl.SSLSessionBindingEvent
import com.example.viaggi.databinding.ActivityMainBinding
import com.example.viaggi.Registrazione
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.security.auth.login.LoginException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signup.setOnClickListener {
            val i = Intent(this, Registrazione::class.java)
            startActivity(i)
        }

        binding.btn.setOnClickListener {
            validateEmail()
        }
    }
    private fun loginRequest(email: String, password: String) {
        val loginQuery = "SELECT * FROM Utente WHERE email = '$email' AND password = '$password';"

        val loginCall = ClientNetwork.retrofit.select(loginQuery)
        loginCall.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if ((data?.get("queryset") as JsonArray).size() == 1) {
                        showMessage("Login effettuato con successo")

                        // Avvia la HomePage dopo il login riuscito
                        val intent = Intent(this@MainActivity, HomePage::class.java)
                        startActivity(intent)
                    } else {
                        showErrorMessage("Credenziali non valide")
                    }
                } else {
                    showErrorMessage("Errore durante il login")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                showErrorMessage("Errore di connessione: ${t.message}")
            }
        })
    }



    private fun validateEmail() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (email.isEmpty()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Campo email vuoto")
            builder.setMessage("Il campo email non può essere vuoto.")
            builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        } else if (password.isEmpty()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Campo password vuoto")
            builder.setMessage("Il campo password non può essere vuoto.")
            builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        } else {
            loginRequest(email, password)
        }
    }


    private fun showErrorMessage(message: String) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Errore")
            .setMessage(message)
            .setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

    private fun showMessage(message: String) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Informazione")
            .setMessage(message)
            .setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()

            }
            .create()
        alertDialog.show()
    }

}










   // fun password(): Boolean {
     //   var error: String? = null
       // val value = binding.password.text.toString()
     //   if (value.isEmpty()) {
     //       error = "Password è richiesta"
     //   } else if (value.length < 6) {
     //       error = "La password deve essere di 6 caratteri "
     //   }
      //  return error == null

/*
*/