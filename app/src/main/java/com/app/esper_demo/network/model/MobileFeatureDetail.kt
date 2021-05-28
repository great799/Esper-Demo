package com.app.esper_demo.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MobileFeatureDetail(
    @PrimaryKey(autoGenerate = true) val uid: Int
)
