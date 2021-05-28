package com.app.esper_demo.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/*
* table structure for mobile exclusions data
* */
@Entity
data class ExclusionDetail(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "exclusions") val exclusionsDetail: List<ExclusionItem>
)

/*
* api response model for exclusion item
* */
data class ExclusionItem(
    @field:Json(name = "feature_id") val featureId: String,
    @field:Json(name = "options_id") val optionsId: String
)
