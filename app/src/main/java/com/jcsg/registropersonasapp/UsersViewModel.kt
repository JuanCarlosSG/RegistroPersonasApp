package com.jcsg.registropersonasapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class User(val name: String, val lastName: String, val gender: Boolean)

class UsersViewModel:ViewModel() {
    val users = arrayListOf<User>()
    val eventTracker = MutableLiveData<List<User>>()

    fun newUser(user: User) {
        users.add(user)
        eventTracker.postValue(users.toList())
    }
}