package com.example.noteappfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FragmentUpdate : Fragment() {

lateinit var ed:EditText
lateinit var update:Button

lateinit var sharedPreferences: SharedPreferences
    val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        shred()
        ed=view.findViewById(R.id.edNots)
        update=view.findViewById(R.id.btnSubmit)
        update.setOnClickListener {
           val noteId = sharedPreferences.getString("NoteId","").toString()
            update(noteId)

            Navigation.findNavController(view).navigate(R.id.action_fragmentUpdate_to_fragmentHome)
        }
        return view
    }
    fun update(id:String){

var noteed =ed.text.toString()
       if(noteed.isNotEmpty() ){
        hashMapOf("note" to noteed)
        db.collection("users").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.id == id){
                        db.collection("users").document(id).update("note",noteed)
                }}
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }
    }

    }
    fun shred() {
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
    }



}