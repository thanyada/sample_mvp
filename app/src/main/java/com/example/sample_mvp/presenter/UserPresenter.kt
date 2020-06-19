package com.example.sample_mvp.presenter

import com.example.sample_mvp.R
import com.example.sample_mvp.data.User
import com.example.sample_mvp.di.UserContract
import kotlinx.android.synthetic.main.dialog_setting.*

enum class  KeyUser{
    NAME ,  PHONENUNBER , GENDER , AGE , ISTHAI
}

class UserPresenter(private var view : UserContract.View? = null) : UserContract.Presenter {

    var contacts = arrayListOf<User>()
    var filters = arrayListOf(KeyUser.PHONENUNBER,KeyUser.GENDER,KeyUser.AGE,KeyUser.ISTHAI)
    var sort : KeyUser? = null
    var isDesc = false

    override fun mockData() {
         contacts = arrayListOf(
            User().apply {
                name = "Gena"
                lastname = "Aggle"
                age = 12
                gender = "f"
                phoneNumber = "01-2343456"
                isThaiNationality = false
            },
            User().apply {
                name = "Liz"
                lastname = "Smith"
                age = 15
                gender = "m"
                phoneNumber = "02-3343456"
                isThaiNationality = true
            },
            User().apply {
                name = "Elizabeth"
                lastname = "Jones"
                age = 42
                gender = "f"
                phoneNumber = "02-3943456"
                isThaiNationality = false
            },
            User().apply {
                name = "Guerrero"
                lastname = "Williams"
                age = 52
                gender = "m"
                phoneNumber = "09-2343456"
                isThaiNationality = false
            },
            User().apply {
                name = "Ada"
                lastname = "Taylor"
                age = 10
                gender = "f"
                phoneNumber = "08-2343456"
                isThaiNationality = false
            },
            User().apply {
                name = "Alfonso"
                lastname = "Smith"
                age = 15
                gender = "f"
                phoneNumber = "01-23344456"
                isThaiNationality = true
            },
            User().apply {
                name = "Edgar"
                lastname = "Davies"
                age = 16
                gender = "f"
                phoneNumber = "02-53434555"
                isThaiNationality = true
            },
            User().apply {
                name = "Hoover"
                lastname = "Thomas"
                age = 22
                gender = "m"
                phoneNumber = "09-1343456"
                isThaiNationality = true
            },
            User().apply {
                name = "Maria"
                lastname = "Johnson"
                age = 34
                gender = "m"
                phoneNumber = "062-343456"
                isThaiNationality = false
            },
            User().apply {
                name = "Mary"
                lastname = "Roberts"
                age = 30
                gender = "m"
                phoneNumber = "02-0346669"
                isThaiNationality = true
            }
        )

        view?.updateUI()
    }


    override fun filterBy(checkBoxID: Int, selected: Boolean) {

       val key =  when(checkBoxID){
            R.id.fAge -> KeyUser.AGE
            R.id.fTel -> KeyUser.PHONENUNBER
            R.id.fNationality -> KeyUser.ISTHAI
            else  -> KeyUser.GENDER
        }

        if(selected) filters.add(key)
        else filters.remove(key)

    }



    override fun sortList(radioId: Int) {

        sort=  when(radioId){
            R.id.sName -> KeyUser.NAME
            R.id.sAge -> KeyUser.AGE
            R.id.sGender -> KeyUser.GENDER
            else -> KeyUser.ISTHAI
        }


        var sortedList =  when(sort){
            KeyUser.NAME -> contacts.sortedWith(compareBy { it.name })
            KeyUser.AGE -> contacts.sortedWith(compareBy { it.age })
            KeyUser.GENDER -> contacts.sortedWith(compareBy { it.gender })
            KeyUser.ISTHAI -> contacts.sortedWith(compareBy { it.isThaiNationality })
            else -> contacts
        }

        contacts = ArrayList(  when { isDesc -> sortedList.asReversed() else -> sortedList })


    }

    override fun sortBy(DESC : Boolean) {
        isDesc = DESC
        contacts = ArrayList(  when { isDesc -> contacts.asReversed() else -> contacts })
    }

    override fun settingComplete() {
        view?.updateUI()
    }

    override fun cleanUp() {
        view = null
    }

    override fun textSort(): String {
        return if(isDesc) "DESC" else "ASC"
    }


}