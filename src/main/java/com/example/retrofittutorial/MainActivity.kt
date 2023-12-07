package com.example.retrofittutorial

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.retrofittutorial.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()

        binding.btnNewMeme.setOnClickListener {
            getData()
        }
    }

    private fun getData() {
        binding.progress.visibility = View.GONE
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetch")
        progressDialog.show()
        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<ResponseDataClass?> {
            override fun onResponse(
                call: Call<ResponseDataClass?>,
                response: Response<ResponseDataClass?>
            ) {
                progressDialog.hide()
                binding.memeAuthor.text = response.body()?.author ?: ""
                binding.memeTitle.text = response.body()?.title ?: ""
                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.memeImage)
            }

            override fun onFailure(call: Call<ResponseDataClass?>, t: Throwable) {
                progressDialog.hide()
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}