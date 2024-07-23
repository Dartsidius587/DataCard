package com.example.datacard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    private var photoUri: Uri? = null
    private var person: Person? = null

    private lateinit var photoIV: ImageView
    private lateinit var firstNameET: EditText
    private lateinit var secondNameET: EditText
    private lateinit var dateBirthdayET: EditText
    private lateinit var saveBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        photoIV.setOnClickListener {
            val photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener {
            createPerson()
            val intent = Intent(this, DataCartActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
            finish()
        }
    }

    private fun createPerson() {
        val firstName = firstNameET.text.toString()
        val secondName = secondNameET.text.toString()
        val dataBirthday = dateBirthdayET.text.toString()
        val photo = photoUri.toString()
        person = Person(firstName, secondName, dataBirthday, photo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        photoIV = findViewById(R.id.photoIV)

        when(requestCode){
            GALLERY_REQUEST -> if(resultCode === RESULT_OK){
                photoUri = data?.data
                photoIV.setImageURI(photoUri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun init() {
        photoIV = findViewById(R.id.photoIV)
        firstNameET = findViewById(R.id.firstNameET)
        secondNameET = findViewById(R.id.secondNameET)
        dateBirthdayET = findViewById(R.id.dateBirthdayET)
        saveBTN = findViewById(R.id.saveBTN)
    }
}