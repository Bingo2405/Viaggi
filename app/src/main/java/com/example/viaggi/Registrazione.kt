package com.example.viaggi



import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.viaggi.databinding.ActivityRegistrazioneBinding
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registrazione : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrazioneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrazioneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.acc.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btn1.setOnClickListener {
            val email = binding.email1.text.toString().trim()
            val password = binding.pw1.text.toString().trim()
            val confirmPassword = binding.pippo.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Errore")
                    .setMessage("Completa tutti i campi")
                    .setPositiveButton("OK", null)
                    .create()
                alertDialog.show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Errore")
                    .setMessage("La password di conferma non corrisponde con la password inserita")
                    .setPositiveButton("OK", null)
                    .create()
                alertDialog.show()
            } else {
                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        val registerQuery = "INSERT INTO Utente (email, password) VALUES ('$email', '$password');"

        val registerCall = ClientNetwork.retrofit.insert(registerQuery)
        registerCall.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    val success = data?.get("queryset") as JsonArray

                    if (success.size()>0) {
                        val alertDialog = AlertDialog.Builder(this@Registrazione)
                            .setTitle("Successo")
                            .setMessage("Registrazione completata con successo")
                            .setPositiveButton("OK") { _, _ ->
                                val intent = Intent(this@Registrazione, MainActivity::class.java)
                                startActivity(intent)
                            }
                            .create()
                        alertDialog.show()
                    } else {
                        val alertDialog = AlertDialog.Builder(this@Registrazione)
                            .setTitle("Errore")
                            .setMessage("Errore durante la registrazione")
                            .setPositiveButton("OK", null)
                            .create()
                        alertDialog.show()
                    }
                } else {
                    val alertDialog = AlertDialog.Builder(this@Registrazione)
                        .setTitle("Errore")
                        .setMessage("Errore durante la registrazione")
                        .setPositiveButton("OK", null)
                        .create()
                    alertDialog.show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                val alertDialog = AlertDialog.Builder(this@Registrazione)
                    .setTitle("Errore")
                    .setMessage("Errore di connessione: ${t.message}")
                    .setPositiveButton("OK", null)
                    .create()
                alertDialog.show()
            }
        })
    }
}
