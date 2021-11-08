package com.example.noteappfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappfragment.databinding.NotesRowBinding

class RV(val active :FragmentHome, var list:ArrayList<List<Any>>): RecyclerView.Adapter<RV.ItemBinding>() {
    class ItemBinding (val bin : NotesRowBinding):RecyclerView.ViewHolder(bin.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBinding {
        return ItemBinding(NotesRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: ItemBinding, position: Int) {

        val id = list [position][0]
        val notes = list[position][1]
        holder.bin.apply{
            mSubTitle.text ="Note $position"
            mSubTitle.text = notes.toString()
            updatebt.setOnClickListener {
               with(active.sharedPreferences.edit()) {
                    putString("NoteId", id.toString())
                    apply()
                    active.nextpage()
                }}

            deletebt.setOnClickListener {
                active.delete(id.toString())}
        }



    }
    override fun getItemCount()=list.size
}