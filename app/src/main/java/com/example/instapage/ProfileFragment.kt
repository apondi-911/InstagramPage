package com.example.instapage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class ProfileFragment : Fragment() {

    private var dataInstFeeds: ArrayList<InstViewModel>? = null


    lateinit var rvInstFeeds: RecyclerView


    private lateinit var imgArrow: ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInstFeeds = ArrayList()


        rvInstFeeds = requireView().findViewById(R.id.recyclerViewFeeds)

        rvInstFeeds.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        imgArrow = requireView().findViewById(R.id.backArrow)

        // Set the click listener for the back arrow
        imgArrow.setOnClickListener {
            // Navigate back to the home fragment
            requireActivity().supportFragmentManager.popBackStack()
        }

            getInstFeeds()


        }

        private fun getInstFeeds() {

            val queue = Volley.newRequestQueue(context)
            val url =
                "https://pixabay.com/api/?key=37540378-c0e882cbc2150bcfad6077fb6&q=people&image_type=photo&pretty=true"

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

                        Toast.makeText(context, "Data loaded successfully", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },

                { error ->
                    val errorMessage = error.message
                    // Handle the error here
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            ) {

            }

            queue.add(request)


        }
    }



