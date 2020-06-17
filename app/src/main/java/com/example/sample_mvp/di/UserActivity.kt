package com.example.sample_mvp.di

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample_mvp.R
import com.example.sample_mvp.data.User
import com.example.sample_mvp.presenter.KeyUser
import com.example.sample_mvp.presenter.UserPresenter
import kotlinx.android.synthetic.main.activity_main.*

class UserActivity : AppCompatActivity(),UserContract.View {

    private lateinit var presenter: UserPresenter

    private val contactsAdapter : ContactsAdapter by lazy{

        ContactsAdapter(object : ContactsAdapterInteface {

            override fun onItemClick(user: User) {
                Toast.makeText(this@UserActivity,"You Choose ${user.name} ${user.lastname}",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = UserPresenter(this)
        presenter.mockData()

        initSortList()
        initFilerList()


    }



    override fun updateUI() {

        if(contactsAdapter.contacts.isEmpty()){

            rvContact.layoutManager = LinearLayoutManager(this)
            rvContact.adapter = contactsAdapter

        }

        contactsAdapter.contacts = presenter.contacts
        contactsAdapter.filters = presenter.filters
        contactsAdapter.notifyDataSetChanged()

    }

    override fun updateTextSort(str: String) {
        swDesc.text = str
    }


    private val onClickFilter = View.OnClickListener{
        when(it.id){
            R.id.fAge -> presenter.filterBy(KeyUser.AGE,fAge.isChecked)
            R.id.fTel -> presenter.filterBy(KeyUser.PHONENUNBER,fTel.isChecked)
            R.id.fNationality -> presenter.filterBy(KeyUser.ISTHAI,fNationality.isChecked)
            R.id.fGender -> presenter.filterBy(KeyUser.GENDER,fGender.isChecked)
        }
    }

    private fun initFilerList() {

        fAge.setOnClickListener(onClickFilter)
        fTel.setOnClickListener(onClickFilter)
        fNationality.setOnClickListener(onClickFilter)
        fGender.setOnClickListener(onClickFilter)
    }

    private fun initSortList() {
        sort.setOnCheckedChangeListener { group, checkedId ->

            val isDesc = swDesc.isChecked
            when(checkedId){
                R.id.sName -> presenter.sortBy(KeyUser.NAME,isDesc)
                R.id.sAge -> presenter.sortBy(KeyUser.AGE,isDesc)
                R.id.sGender -> presenter.sortBy(KeyUser.GENDER,isDesc)
                R.id.sNationality -> presenter.sortBy(KeyUser.ISTHAI,isDesc)
            }
        }

        swDesc.setOnCheckedChangeListener { _, isChecked ->

            when(sort.checkedRadioButtonId){
                R.id.sName -> presenter.sortBy(KeyUser.NAME,isChecked)
                R.id.sAge -> presenter.sortBy(KeyUser.AGE,isChecked)
                R.id.sGender -> presenter.sortBy(KeyUser.GENDER,isChecked)
                R.id.sNationality -> presenter.sortBy(KeyUser.ISTHAI,isChecked)
            }
        }
    }





    override fun onDestroy() {
        super.onDestroy()
        presenter.cleanUp()
    }
}