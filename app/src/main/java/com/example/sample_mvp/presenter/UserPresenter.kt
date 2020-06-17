package com.example.sample_mvp.presenter

import com.example.sample_mvp.data.User
import com.example.sample_mvp.di.UserContract

enum class  KeyUser{
    NAME ,  PHONENUNBER , GENDER , AGE , ISTHAI
}

class UserPresenter(private var view : UserContract.View? = null) : UserContract.Presenter {

    var contacts = arrayListOf<User>()
    var filters = arrayListOf(KeyUser.PHONENUNBER,KeyUser.GENDER,KeyUser.AGE,KeyUser.ISTHAI)

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


    override fun filterBy(key: KeyUser,selected : Boolean) {
        if(selected) filters.add(key)
        else filters.remove(key)

        view?.updateUI()
    }

    override fun sortBy(key: KeyUser,DESC : Boolean) {

        var sortedList =  when(key){
            KeyUser.NAME -> contacts.sortedWith(compareBy { it.name })
            KeyUser.AGE -> contacts.sortedWith(compareBy { it.age })
            KeyUser.GENDER -> contacts.sortedWith(compareBy { it.gender })
            KeyUser.ISTHAI -> contacts.sortedWith(compareBy { it.isThaiNationality })
            else -> contacts
        }


        contacts = ArrayList(  when { DESC -> sortedList.asReversed() else -> sortedList })
        view?.updateUI()
        view?.updateTextSort( when { DESC -> "DESC" else -> "ASC" } )

    }

    override fun cleanUp() {
        view = null
    }


}