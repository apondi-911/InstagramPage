package com.example.instapage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class InstAdapter( private val mList: List<InstViewModel>): RecyclerView.Adapter<InstAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstAdapter.ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.insta_feeds_items,parent,false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: InstAdapter.ViewHolder, position: Int) {
        val instViewModel: InstViewModel= this.mList[position]

        val profileUrl = instViewModel.userImageURL.ifEmpty { "https://unsplash.com/photos/WpOMM4uE-F8" }
        val postUrl = instViewModel.webFormatURL.ifEmpty { "Default" }

        Picasso.get().load(postUrl).into(holder.imageView)
        Picasso.get().load(profileUrl).into(holder.imgView)
        holder.tvUser.text=instViewModel.user
        holder.tvViews.text= instViewModel.views.toString()
        holder.tvLikes.text = instViewModel.likes.toString()
        holder.tvComments.text = instViewModel.comments.toString()
        holder.tvCaptions.text =instViewModel.tags


    }

    override fun getItemCount(): Int {
        return mList.size
    }

   inner class ViewHolder (ItemView: View):RecyclerView.ViewHolder(ItemView){
       val imageView: ImageView = itemView.findViewById(R.id.imgPost)
       val imgView: ImageView =itemView.findViewById(R.id.profile_pic)
       val tvUser: TextView = itemView.findViewById(R.id.tvUserName)
       val tvViews: TextView =itemView.findViewById(R.id.tvFollowers)
       val tvLikes: TextView = itemView.findViewById(R.id.likes)
       val tvComments: TextView =itemView.findViewById(R.id.comments)
       val tvCaptions: TextView = itemView.findViewById(R.id.Caption)
       val imgLike: ImageView = itemView.findViewById(R.id.likeIcon)
       val imgComments : ImageView = itemView.findViewById(R.id.commentIcon)

   }
}
