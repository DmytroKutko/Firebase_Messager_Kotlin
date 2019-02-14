package com.example.firebasenetworkkotlin.model

class User(val uid: String, val username: String, val profileImage: String) {
    constructor():this("","","")
}