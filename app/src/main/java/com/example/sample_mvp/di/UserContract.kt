package com.example.sample_mvp.di

import com.example.sample_mvp.presenter.KeyUser

interface UserContract {

    interface View{
        fun updateUI()
    }

    interface Presenter{
        fun mockData()
        fun filterBy(checkboxId: Int, selected: Boolean)
        fun sortBy( DESC : Boolean)
        fun sortList( radioId: Int)
        fun settingComplete()
        fun cleanUp()
        fun textSort() :String

    }
}