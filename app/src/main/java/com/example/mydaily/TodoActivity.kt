package com.example.mydaily

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydaily.adapter.TodoAdapter
import com.example.mydaily.databinding.ActivityTodoBinding
import com.example.mydaily.usecases.TodoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoBinding
    private lateinit var adapter: TodoAdapter
    private val todoUseCase = TodoUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        adapter = TodoAdapter(mutableListOf())
        binding.constainer.layoutManager = LinearLayoutManager(this)
        binding.constainer.adapter = adapter

        // Load data dari Firestore
        loadTodos()
    }

    private fun loadTodos() {
        binding.uiLoading.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val todos = todoUseCase.getTodo()
                withContext(Dispatchers.Main) {
                    adapter.updateData(todos)
                    binding.uiLoading.visibility = View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.uiLoading.visibility = View.GONE
                }
            }
        }
    }
}
