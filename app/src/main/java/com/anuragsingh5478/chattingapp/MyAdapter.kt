package com.anuragsingh5478.chattingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.android.synthetic.main.list_item.view.*

//class MyAdapter(private val myDataset : MutableList<String>,private val myUser : MutableList<String>)
class MyAdapter(private val myDataset : MutableList<SingleMessage>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val myData : SingleMessage = myDataset[position]
        /*holder.view.textViewMessage.text = myDataset.[position]
        holder.view.textViewUser.text = myUser[position]*/
        holder.view.textViewMessage.text = myData.message
        holder.view.textViewUser.text = myData.user
    }

    override fun getItemCount(): Int {

        return myDataset.size
    }
}