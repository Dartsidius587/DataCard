package com.example.datacard

import java.io.Serializable

class Person(
    val firstName: String?,
    val secondName: String?,
    val dataBirthday: String?,
    val photo: String?
) : Serializable