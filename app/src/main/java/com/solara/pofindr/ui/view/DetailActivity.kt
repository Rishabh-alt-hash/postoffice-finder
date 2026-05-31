package com.solara.pofindr.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solara.pofindr.data.model.PostOffice
import com.solara.pofindr.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up logout button listener
        binding.logoutButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val postOffice = intent.getParcelableExtra<PostOffice>("postOffice")
        postOffice?.let { displayPostOfficeDetails(it) }
    }

    private fun displayPostOfficeDetails(postOffice: PostOffice) {
        binding.apply {
            postOfficeName.text = "Name : ${postOffice.Name}"
            postOfficeDescription.text = "Description : ${postOffice.Description}"
            postOfficePINCode.text = "PinCode : ${postOffice.PINCode}"
            postOfficeBranchType.text = "BranchType : ${postOffice.BranchType}"
            postOfficeDeliveryStatus.text = "DeliveryStatus : ${postOffice.DeliveryStatus}"
            postOfficeTaluk.text = "Taluka : ${postOffice.Taluk}"
            postOfficeCircle.text = "Circle : ${postOffice.Circle}"
            postOfficeDistrict.text = "District : ${postOffice.District}"
            postOfficeDivision.text = "Division : ${postOffice.Division}"
            postOfficeRegion.text = "Region : ${postOffice.Region}"
            postOfficeState.text = "State : ${postOffice.State}"
            postOfficeCountry.text = "Country : ${postOffice.Country}"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}