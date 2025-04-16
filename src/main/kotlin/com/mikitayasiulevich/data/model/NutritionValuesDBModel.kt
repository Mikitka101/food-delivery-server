package com.mikitayasiulevich.data.model

import java.util.*

data class NutritionValuesDBModel(
    val id: UUID,
    val calories: Int,
    val proteins: Int,
    val fats: String,
    val carbohydrates: String
)
