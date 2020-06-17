package com.example.sample_mvp.di

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.sample_mvp.R
import com.example.sample_mvp.data.User
import com.example.sample_mvp.presenter.KeyUser

class ContactsAdapter(val callback : ContactsAdapterInteface) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>()  {

    var contacts = arrayListOf<User>()
    var filters = arrayListOf<KeyUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(contacts[position])
        holder.itemView.setOnClickListener { callback.onItemClick(contacts[position]) }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvAlphabelt: TextView = view.findViewById(R.id.tvAlphabelt)
        private var tvName: TextView = view.findViewById(R.id.tvName)
        private var tvAge: TextView = view.findViewById(R.id.tvAge)
        private var tvGender: TextView = view.findViewById(R.id.tvGender)
        private var tvPhone: TextView = view.findViewById(R.id.tvPhone)
        private var tgThai: TextView = view.findViewById(R.id.tgThai)

        fun bind(user: User) {
            tvAlphabelt.text = user.name?.substring(0,1)?.toUpperCase()
            tvName.text = "${user.name} ${user.lastname}"
            tvAge.text = "age : ${user.age}"
            tvGender.text = "gender : ${user.gender}"
            tvPhone.text = "tel : ${user.phoneNumber}"
            tgThai.text = if(user.isThaiNationality!!) "Thai" else "No Thai"
            val colorbg = if(user.isThaiNationality!!) Color.BLUE else Color.RED
            tgThai.setBackgroundColor(colorbg)

            tvPhone.visibility = if(filters.contains(KeyUser.PHONENUNBER)) View.VISIBLE else View.INVISIBLE
            tvAge.visibility = if(filters.contains(KeyUser.AGE)) View.VISIBLE else View.INVISIBLE
            tvGender.visibility = if(filters.contains(KeyUser.GENDER)) View.VISIBLE else View.INVISIBLE
            tgThai.visibility = if(filters.contains(KeyUser.ISTHAI)) View.VISIBLE else View.INVISIBLE

        }
    }

}