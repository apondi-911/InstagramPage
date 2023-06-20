package com.example.instapage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class InstAdapter( private val mList: List<InstViewModel>): RecyclerView.Adapter<InstAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstAdapter.ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.insta_feeds_items,parent,false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: InstAdapter.ViewHolder, position: Int) {
        val instViewModel: InstViewModel= this.mList[position]
        Picasso.get().load(instViewModel.webFormatURL).into(holder.imageView)
        Picasso.get().load(instViewModel.userImageUrl).into(holder.imgView)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

   inner class ViewHolder (ItemView: View):RecyclerView.ViewHolder(ItemView){
       val imageView: ImageView = itemView.findViewById(R.id.imgPost)
       val imgView: ImageView =itemView.findViewById(R.id.profile_pic)

   }
}
