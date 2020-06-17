package com.example.sample_mvp.di

import com.example.sample_mvp.presenter.KeyUser

interface UserContract {

    interface View{
        fun updateUI()
        fun updateTextSort(str : String)
    }

    interface Presenter{
        fun mockData()
        fun filterBy( key: KeyUser,selected : Boolean)
        fun sortBy( key : KeyUser ,DESC : Boolean)
        fun cleanUp()

    }
}