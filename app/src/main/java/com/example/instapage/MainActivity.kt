package com.example.instapage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var dataInstFeeds: ArrayList<InstViewModel>? = null

    lateinit var rvInstFeeds: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvInstFeeds = findViewById(R.id.recyclerViewFeeds)

        rvInstFeeds.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getInstFeeds()
    }

    private fun getInstFeeds() {

        val queue = Volley.newRequestQueue(this)
        val url = "https://pixabay.com/api/?key=37540378-c0e882cbc2150bcfad6077fb6&q=people&image_type=photo"

        val request = object : StringRequest(
            Method.GET, url,
            { response ->
                // Handle the response here

                val jsonObject = JSONObject(response)

                val jsonArray = jsonObject.getJSONArray("hits")

                dataInstFeeds = ArrayList() // Initialize the dataIngredient ArrayList

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val userImageUrl = obj.getString("userImageUrl")
                    val webFormatUrl = obj.getString("webformatUrl")
                    val feedComments = obj.getInt("comments")
                    val feedLikes = obj.getInt("likes")
                    val feedViews = obj.getInt("views")
                    val feedTags = obj.getString("tags")

                    dataInstFeeds?.add(
                        InstViewModel(
                            userImageUrl,
                            webFormatUrl,
                            feedComments,
                            feedLikes,
                            feedViews,
                            feedTags
                        )
                    )
                }
                val adapter = dataInstFeeds?.let { InstAdapter(it) }
                rvInstFeeds.adapter = adapter

                Toast.makeText(this, "Data loaded successfully", Toast.LENGTH_SHORT).show()
            },

            { error ->
                val errorMessage = error.message
                // Handle the error here
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
        {
        }
        queue.add(request)


    }
}
