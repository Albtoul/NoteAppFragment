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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FragmentHome : Fragment() {
    var TAG = "MaineActivity"
    private lateinit var rc: RecyclerView
    private lateinit var ed: EditText
    private lateinit var notebutton: Button
   lateinit var sharedPreferences: SharedPreferences
    val db = Firebase.firestore

    override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        shred()
        ed=view.findViewById(R.id.editTextTextPersonName)
        notebutton=view.findViewById(R.id.button)
        rc=view.findViewById(R.id.RVv)
        CoroutineScope(IO).launch {
            delay(1000)
            retrive()
        }
        notebutton.setOnClickListener {
            if (ed.text.isNotEmpty()) {
                val user = hashMapOf("note" to ed.text.toString())
                // Add a new document with a generated ID
                db.collection("users").add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        retrive()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            } else
                Toast.makeText(requireContext(), "fill the blank ", Toast.LENGTH_SHORT).show()
            ed.text.clear()
        }
        return view
    }

    fun retrive(){

        // retrive data
        db.collection("users").get().addOnSuccessListener { result ->
            var details = arrayListOf<List<Any>>()
            for (document in result) {
                document.data.map { (key, value) ->
                    details.add(listOf(document.id, value))
                }
            }
            rc.adapter = RV(this,details)
            rc.layoutManager = LinearLayoutManager(requireContext())
        }
            .addOnFailureListener { exception -> Log.w(TAG, "error", exception) }

              }

    fun delete(id:String){
        db.collection("users").document(id).delete()
        retrive()
    }
    fun nextpage() {
        Navigation.findNavController(requireView()).navigate(R.id.action_fragmentHome_to_fragmentUpdate)

    }
    fun shred() {
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
    }

}