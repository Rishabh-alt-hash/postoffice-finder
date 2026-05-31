package com.solara.pofindr.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "post_office")
data class PostOffice(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val Name: String,
    val Description: String? = null,
    val PINCode: String,
    val BranchType: String,
    val DeliveryStatus: String,
    val Taluk: String,
    val Circle: String,
    val District: String,
    val Division: String,
    val Region: String,
    val State: String,
    val Country: String
) : Parcelable