package com.app.esper_demo.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/*
* api response model and table structure for mobile features data
* */
@Entity
data class MobileFeatureDetail(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "feature_id") @field:Json(name = "feature_id") val featureId: String,
    @ColumnInfo(name = "feature_name") @field:Json(name = "name") val featureName: String,
    @ColumnInfo(name = "options") @field:Json(name = "options") val options: List<OptionDetail>
)

/*
* Api response model for options detail
* */
data class OptionDetail(
    @field:Json(name = "name") val optionName: String,
    @field:Json(name = "icon") val optionIcon: String,
    @field:Json(name = "id") val optionId: String
)
