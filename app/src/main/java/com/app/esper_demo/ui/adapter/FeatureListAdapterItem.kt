package com.app.esper_demo.ui.adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FeatureListAdapterItem : Parcelable {
    var featureId: String? = null
    var optionId: String? = null
    var name: String? = null
    var logo: String? = null
    var title: String? = null
    var showTitle: Boolean = false
    var isChecked: Boolean = false
}