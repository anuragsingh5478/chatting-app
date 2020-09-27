package com.anuragsingh5478.chattingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var editTextMessage: EditText
    lateinit var buttonSend: Button
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager : RecyclerView.LayoutManager
    private var myDataset: Array<String> = arrayOf("anurag hi","hi molu","how are you","i am fine")
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        database = Firebase.database.reference


        viewManager = LinearLayoutManager(this)
        myAdapter = MyAdapter(myDataset)

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
                database.child("chats").push().setValue(message)
                editTextMessage.text.clear()
            }
            else{
                Toast.makeText(this,"enter message",Toast.LENGTH_LONG)
            }
        }

    }
}