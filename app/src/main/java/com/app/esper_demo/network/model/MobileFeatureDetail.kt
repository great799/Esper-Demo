package com.app.esper_demo.network.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/*
* Api response model for features
* */
data class MobileFeatureDetailApi(
    @field:Json(name = "feature_id") val featureId: String,
    @field:Json(name = "name") val featureName: String,
    @field:Json(name = "options") val options: List<OptionDetail>
)

/*
* table structure for mobile features data
* */
@Entity
data class MobileFeatureDetail(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "feature_id") val featureId: String,
    @ColumnInfo(name = "feature_name") val featureName: String,
    @Embedded @ColumnInfo(name = "option") val option: OptionDetail
)

/*
* Api response model for options detail
* */
data class OptionDetail(
    @field:Json(name = "name") val optionName: String,
    @field:Json(name = "icon") val optionIcon: String,
    @field:Json(name = "id") val optionId: String
)
