package com.zukira.crud_mhs_berita_mi2a.screen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zukira.crud_mhs_berita_mi2a.R
import com.zukira.crud_mhs_berita_mi2a.model.RegisterResponse
import com.zukira.crud_mhs_berita_mi2a.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Tag

class RegisterScreenActivity : AppCompatActivity() {
    //    private lateinit var etEmail : EditText
//    private lateinit var etUsername : EditText
//    private lateinit var etFullname : EditText
//    private lateinit var etPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_screen)

        val etUsername : EditText = findViewById(R.id.etUsername)
        val etFullname : EditText = findViewById(R.id.etFullname)
        val etEmail : EditText = findViewById(R.id.etEmail)
        val etPassword  : EditText = findViewById(R.id.etPassword)
        val btnRegiter : Button =findViewById(R.id.btnRegister)
        val txtLogin : TextView = findViewById(R.id.txtLogin)

        btnRegiter.setOnClickListener(){
            val nUsername = etUsername.text.toString()
            val nFullname = etFullname.text.toString()
            val nPassword = etPassword.text.toString()
            val nEmail = etEmail.text.toString()

            //proses untuk save data ke database
//            val registerRequest = RegisterRequest( nUsername,nFullname,nEmail,nPassword)
            try {
                ApiClient.retrofit.register(nUsername, nFullname,nEmail,nPassword).enqueue(
//                    ApiClient.retrofit.register(nUsername, nFullname,nEmail,nPassword).enqueue(
                    object : Callback<RegisterResponse>{
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if(response.isSuccessful){
                                Toast.makeText(this@RegisterScreenActivity,response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                            }else{
                                val errorMessage = response.errorBody()?.string()?: "Unknown Error"
                                Log.e("Register Error" , errorMessage)
                                Toast.makeText(
                                    this@RegisterScreenActivity,
                                    "Register Failur",
                                    Toast.LENGTH_SHORT).show()

                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(this@RegisterScreenActivity,t.message,
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                )
            }catch (e : Exception){
                Toast.makeText(
                    this@RegisterScreenActivity,"Error Occured : ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG,"Error Occured: ${e.message}",e)
            }
        }

        txtLogin.setOnClickListener{
            val login = Intent(this@RegisterScreenActivity,LoginActivity::class.java)
            startActivity(login)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}