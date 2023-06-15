package com.example.viaggi

import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viaggi.databinding.ActivityHomePageBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var  recyclerView: RecyclerView
    private lateinit var adapter: Horizontal_RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        adapter = Horizontal_RecyclerView()
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = adapter

        binding.show.setOnClickListener {
            val intent = Intent(this, CurrentLocation::class.java)
            startActivity(intent)
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView2)
        // bottomNavigationView.selectedItemId = R.id.account

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.account -> {
                    val intent = Intent(this, ProfilePage::class.java)
                    startActivity(intent)
                    true
                }
                R.id.airplane -> {
                    val intent = Intent(this, Registrazione::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        }

       // binding.bgg.setOnClickListener {
         //   val intent = Intent(this, Pagamenti::class.java)
            //startActivity(intent)
        }
   // }
//}






