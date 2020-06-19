package com.example.sample_mvp.di

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample_mvp.R
import com.example.sample_mvp.data.User
import com.example.sample_mvp.presenter.KeyUser
import com.example.sample_mvp.presenter.UserPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_setting.*


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


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        showDialogSetting()
        return super.onOptionsItemSelected(item)
    }

    private val onCheckChangeFilter = CompoundButton.OnCheckedChangeListener{ cbId , ischecked ->

        presenter.filterBy(cbId.id,ischecked)

    }

    private fun showDialogSetting() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE )
        dialog.setContentView(R.layout.dialog_setting)
        dialog.setCancelable(true)

        val sort = dialog.findViewById<RadioGroup>(R.id.sort)
        val swDesc = dialog.findViewById<SwitchCompat>(R.id.swDesc)
        val fAge = dialog.findViewById<CheckBox>(R.id.fAge)
        val fTel = dialog.findViewById<CheckBox>(R.id.fTel)
        val fNationality = dialog.findViewById<CheckBox>(R.id.fNationality)
        val fGender = dialog.findViewById<CheckBox>(R.id.fGender)


        presenter.sort?.let {
            when(it){
                KeyUser.NAME -> sort.check(R.id.sName)
                KeyUser.AGE -> sort.check(R.id.sAge)
                KeyUser.GENDER -> sort.check(R.id.sGender)
                KeyUser.ISTHAI -> sort.check(R.id.sNationality)
            }
        }

        swDesc.isChecked = presenter.isDesc
        swDesc.text = presenter.textSort()


        fTel.isChecked = presenter.filters.contains(KeyUser.PHONENUNBER)
        fAge.isChecked = presenter.filters.contains(KeyUser.AGE)
        fNationality.isChecked = presenter.filters.contains(KeyUser.ISTHAI)
        fGender.isChecked = presenter.filters.contains(KeyUser.GENDER)

        fAge.setOnCheckedChangeListener(onCheckChangeFilter)
        fTel.setOnCheckedChangeListener(onCheckChangeFilter)
        fNationality.setOnCheckedChangeListener(onCheckChangeFilter)
        fGender.setOnCheckedChangeListener(onCheckChangeFilter)


        sort.setOnCheckedChangeListener { _, checkedId ->
            presenter.sortList(checkedId)
        }


        swDesc.setOnCheckedChangeListener { _, isChecked ->
            presenter.sortBy(isChecked)
        }


        dialog.setOnCancelListener {
            presenter.settingComplete()
        }

        dialog.show()

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


    override fun onDestroy() {
        super.onDestroy()
        presenter.cleanUp()
    }
}