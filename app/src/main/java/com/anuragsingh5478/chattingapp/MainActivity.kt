package com.anuragsingh5478.chattingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var editTextMessage: EditText
    lateinit var buttonSend: Button
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager : RecyclerView.LayoutManager
    private var myDataset: Array<String> = arrayOf("anurag hi","hi molu","how are you","i am fine")
    private  var myList: MutableList<String> = mutableListOf()
    private lateinit var database: DatabaseReference
   // private lateinit var databaseWriting: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        database = Firebase.database.reference
      //  databaseWriting = Firebase.database.reference.child("chats")
      val databaseReading = Firebase.database.reference.child("chats").orderByChild("time")
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                myList.clear()
                snapshot.children.forEach { item ->
                    myList.add(Objects.requireNonNull(item.child("message").value).toString())
                }
                myAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        databaseReading.addValueEventListener(postListener)

        viewManager = LinearLayoutManager(this)
        myAdapter = MyAdapter(myList)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager;
            adapter = myAdapter
        }

        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)
        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if(message.trim() != ""){
                //database.child("chats").push().setValue(message)
                val key = database.child("chats").push().key
                if(key!=null) {
                    database.child("chats").child(key).child("message").setValue(message)
                    database.child("chats").child(key).child("time")
                        .setValue(Date().time.toString())
                    editTextMessage.text.clear()
                }
            }
            else{
                Toast.makeText(this,"enter message",Toast.LENGTH_LONG).show()
            }
        }

    }
}