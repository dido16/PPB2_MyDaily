package com.example.mydaily.usecases

import com.example.mydaily.entity.Todo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class TodoUseCase {
    val db = Firebase.firestore

    suspend fun getTodo(): List<Todo> {
        try {
            val data = db.collection("todo")
                .get()
                .await()

            if (!data.isEmpty) {
                return data.documents.map {
                    Todo(
                        id = it.id,
                        title = it.getString("title").toString(),
                        description = it.getString("description").toString()
                    )
                }
            }

            return arrayListOf<Todo>();
        } catch (exc: Exception) {
            throw Exception(exc.message)
        }
    }
}