package com.mikitayasiulevich.domain.repository

import com.mikitayasiulevich.data.model.NutritionValuesDBModel
import java.util.*

interface NutritionValuesRepository {
    suspend fun addValues(calories: String, proteins: String, fats: String, carbohydrates: String): NutritionValuesDBModel

    suspend fun getValuesById(id: UUID): NutritionValuesDBModel

    suspend fun getDefaultValues(): NutritionValuesDBModel
}