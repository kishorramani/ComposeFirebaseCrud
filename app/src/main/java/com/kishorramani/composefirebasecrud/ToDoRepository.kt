package com.kishorramani.composefirebasecrud

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object ToDoRepository {
    private val db = FirebaseDatabase.getInstance().getReference("ToDo")

    //add - Add data to firebase
    fun addTodo(toDo: ToDo) {
        val id = db.push().key!!
        db.child(id).setValue(toDo.copy(id = id))
    }

    //get - get data from firebase
    fun getToDos(onDataChange: (List<ToDo>) -> Unit) {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val toDoList = mutableListOf<ToDo>()
                snapshot.children.forEach { dataSnapShot ->
                    dataSnapShot.getValue(ToDo::class.java)?.let { toDo -> toDoList.add(toDo) }
                }
                onDataChange(toDoList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //Update - Update created todo
    fun updateTodo(toDo: ToDo) {
        db.child(toDo.id).setValue(toDo)
    }

    //delete - Delete created toDos
    fun deleteTodo(id: String) {
        db.child(id).removeValue()
    }
}