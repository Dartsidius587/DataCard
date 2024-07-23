package com.example.datacard

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class DataCartActivity : AppCompatActivity() {

    private lateinit var titleDataCardTB: Toolbar
    private lateinit var photoDataIV: ImageView
    private lateinit var firstNameTV: TextView
    private lateinit var secondNameTV: TextView
    private lateinit var ageTV: TextView
    private lateinit var timeOfBirthdayTV: TextView

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_cart)
        init()
        val birthday = addFields()
        ageAndBirthday(birthday)
    }

    private fun addFields(): String? {
        val person = intent.extras?.getSerializable("person") as Person
        val firstName = person.firstName
        val secondName = person.secondName
        val birthday = person.dataBirthday
        val photo: Uri? = Uri.parse(person.photo)

        photoDataIV.setImageURI(photo)
        firstNameTV.text = firstName
        secondNameTV.text = secondName
        return birthday
    }

    @SuppressLint("SimpleDateFormat")
    private fun ageAndBirthday(birthday: String?) {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MM yyyy")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
        val dateToday = dateFormat.format(currentDate).toString()

        val dateBirthday = birthday!!
        val listBirthday = dateToInt(dateBirthday)
        val listToday = dateToInt(dateToday)
        var age = listToday[2] - listBirthday[2]

        val daysToday = listToday[0] + listToday[1] * 30
        val daysBirthday = listBirthday[0] + listBirthday[1] * 30

        val days =
            if (daysToday - daysBirthday < 0) daysBirthday - daysToday else 365 - daysToday + daysBirthday

        val month: Int = days / 30
        val day = days - month * 30

        if (listToday[1] - listBirthday[1] < 0) age--
        else if (listToday[1] - listBirthday[1] == 0)
            if (listToday[0] - listBirthday[0] < 0) age--

        ageTV.text = age.toString()

        val timeOfBirthday = "$day дней и $month месяцев"
        timeOfBirthdayTV.text = timeOfBirthday
    }

    private fun dateToInt(date: String): List<Int> {
        val list: List<String> = date.split(" ")
        val num: MutableList<Int> = mutableListOf()
        for (i in list.indices) num += list[i].toInt()
        return num
    }

    private fun init() {
        titleDataCardTB = findViewById(R.id.titleDataCardTB)
        setSupportActionBar(titleDataCardTB)
        photoDataIV = findViewById(R.id.photoDataIV)
        firstNameTV = findViewById(R.id.firstNameTV)
        secondNameTV = findViewById(R.id.secondNameTV)
        ageTV = findViewById(R.id.ageTV)
        timeOfBirthdayTV = findViewById(R.id.timeOfBirthdayTV)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuExit) finish()
        return super.onOptionsItemSelected(item)
    }
}