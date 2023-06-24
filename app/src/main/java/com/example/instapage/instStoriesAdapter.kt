package com.example.instapage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class InstStoriesAdapter(private val sList: List<InstStoriesViewModel>): RecyclerView.Adapter<InstStoriesAdapter.ViewHolder>() {

    private var itemStoriesListener : OnItemClickListener?=null


    //creating an interface
    interface OnItemClickListener{

        fun onItemImageClicked(position: Int)

    }

    fun setOnItemClickListener (listener: HomeFragment){
        itemStoriesListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inst_stories_items,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val instStoriesViewModel: InstStoriesViewModel = this.sList[position]

        val profileUrl = instStoriesViewModel.userImageURL.ifEmpty { "https://unsplash.com/photos/WpOMM4uE-F8" }


        Picasso.get().load(profileUrl).placeholder(R.drawable.search).into(holder.imgView)
        holder.tvUser.text=instStoriesViewModel.user
    }
    inner class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
        val imgView: ImageView =itemView.findViewById(R.id.profile_pic)
        val tvUser: TextView = itemView.findViewById(R.id.tvUserName)


        init {
            imgView.setOnClickListener{
                if(itemStoriesListener!=null){
                    @Suppress("DEPRECATION") val  position=adapterPosition
                    if (position!=RecyclerView.NO_POSITION){
                        itemStoriesListener!!.onItemImageClicked(position)
                    }
                }
            }

        }


    }
 }