# Esper-Demo
# Base components

-MVVM architecture followed by respository pattern

-dagger2

-room

-retrofit

-glide

-viewmodel

-corountines

-livedata

-kotlin

# Room database table structure

@Entity

data class MobileFeatureDetail(

    @PrimaryKey(autoGenerate = true) val uid: Int,
    
    @ColumnInfo(name = "feature_id") val featureId: String,
    
    @ColumnInfo(name = "feature_name")  val featureName: String,
    
    @ColumnInfo(name = "options") val options: List<OptionDetail>
    
)
  
data class OptionDetail(

    val optionName: String,
    
    val optionIcon: String,
    
    val optionId: String
    
)
  
Above table is used to store all the features/mobile data with options list as json form in db

@Entity

data class ExclusionDetail(

    @PrimaryKey(autoGenerate = true) val uid: Int,
    
    @ColumnInfo(name = "exclusions") val exclusionsDetail: List<ExclusionItem>
    
)
  
data class ExclusionItem(

     val featureId: String,
     
     val optionsId: String
     
)
  
Above table sis used to store all the exclusions list as json form in db

# Feature list view model logic

-created map of "featureId-optionId" as key and AdapterItem(name, title, logo etc.) as value

-created map of "featureId-optionId" as key and list of "featureId-optionId" as value

  --this map contains all the combinations of exclusions

# When any features is selected/deselected from ui

--added selected index as "featureId-optionId" in selected list and removed from deselected list(if available)

--vice-versa of above statement if deselected

--copy original featuresData map

--fetch list of exclusions from exclusionMap on basis of key("featureId-optionId")

--remove list of exclusions from copied map on basis of key("featureId-optionId")

--populate list from modified copied map and send back to ui
