package com.example.instapage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject



class HomeFragment : Fragment(), InstStoriesAdapter.OnItemClickListener {

    private var dataInstStories: ArrayList<InstStoriesViewModel>? =null
    private lateinit var rvInstStories: RecyclerView


    private var dataInstFeeds: ArrayList<InstViewModel>? = null


    lateinit var rvInstFeeds: RecyclerView
    lateinit var iconShare: ImageView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState)
        dataInstStories= ArrayList()


        rvInstStories = requireView().findViewById(R.id.recyclerViewStories)
        rvInstStories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        rvInstFeeds = requireView().findViewById(R.id.recyclerViewFeeds)
        rvInstFeeds.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        iconShare.setOnClickListener {


        }

        getInstFeeds()
        getInstStories()

    }


    private fun getInstStories() {

        // Create a new Volley request queue
        val queue = Volley.newRequestQueue(context)

        // Define the URL for your request

        val url = "https://pixabay.com/api/?key=37540378-c0e882cbc2150bcfad6077fb6&q=people&image_type=photo&pretty=true"


        // Create a string request
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                // Handle the response here
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("hits")
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val userImageUrl = obj.getString("userImageURL")
                    val userName = obj.getString("user")
                    dataInstStories?.add(InstStoriesViewModel(userImageUrl,userName))
                }
                val adapter = dataInstStories?.let { InstStoriesAdapter(it) }
                rvInstStories.adapter = adapter

                adapter!!.setOnItemClickListener(this@HomeFragment)

            },
            { error ->
                // Handle the error here
                val errorMessage = error.message


            }
        )

// Add the request to the request queue
        queue.add(request)
    }

    private fun getInstFeeds() {

        val queue = Volley.newRequestQueue(context)
        val url = "https://pixabay.com/api/?key=37540378-c0e882cbc2150bcfad6077fb6&q=people&image_type=photo&pretty=true"

        val request = object : StringRequest(
            Method.GET, url,
            { response ->
                // Handle the response here

                val jsonObject = JSONObject(response)

                val jsonArray = jsonObject.getJSONArray("hits")

                dataInstFeeds = ArrayList() // Initialize the dataIngredient ArrayList
                try {
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val userImageUrl = obj.getString("userImageURL")
                        val webFormatUrl = obj.getString("webformatURL")
                        val userName = obj.getString("user")
                        val feedComments = obj.getInt("comments")
                        val feedLikes = obj.getInt("likes")
                        val feedViews = obj.getInt("views")
                        val feedTags = obj.getString("tags")

                        dataInstFeeds?.add(
                            InstViewModel(
                                userImageUrl,
                                userName,
                                feedViews,
                                webFormatUrl,
                                feedComments,
                                feedLikes,
                                feedTags
                            )
                        )
                    }
                    val adapter = dataInstFeeds?.let { InstAdapter(it) }
                    rvInstFeeds.adapter = adapter

                    Toast.makeText(context, "Data loaded successfully", Toast.LENGTH_SHORT).show()
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            },

            { error ->
                val errorMessage = error.message
                // Handle the error here
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        ){
        }
        queue.add(request)

    }


    override fun onItemImageClicked(position: Int) {
        val clickedItem = dataInstStories?.get(position)


        if (clickedItem != null)
        {

            val intent = Intent(context, ProfileFragment::class.java)
            intent.putExtra("instStoriesViewModel",clickedItem.userImageURL)

            startActivity(intent)

        }
    }
}
