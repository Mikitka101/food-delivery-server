package com.mikitayasiulevich.utils

class Constants {

    object Role {
        const val ADMIN = "admin"
        const val MODERATOR = "moderator"
        const val CLIENT = "client"
        const val COURIER = "courier"
    }

    object Status {
        const val POST = "post"
        const val COOK = "cook"
        const val DELIVERY = "delivery"
        const val COMPLETED = "completed"
    }

    object Error {
        const val GENERAL = "Something went wrong"
        const val WRONG_EMAIL = "Wrong email address"
        const val INCORRECT_PASSWORD = "Incorrect password"
        const val MISSING_FIELDS = "Some fields are missing"
        const val USER_NOT_FOUND = "User not found"
    }

    object Success {
        const val RESTAURANT_CREATED_SUCCESSFULLY = "Restaurant successfully added"
        const val RESTAURANT_UPDATED_SUCCESSFULLY = "Restaurant successfully updated"
        const val RESTAURANT_DELETED_SUCCESSFULLY = "Restaurant successfully deleted"

        const val DISH_CREATED_SUCCESSFULLY = "Dish successfully added"
        const val DISH_UPDATED_SUCCESSFULLY = "Dish successfully updated"
        const val DISH_DELETED_SUCCESSFULLY = "Dish successfully deleted"
    }

    object Value {
        const val ID = "id"
    }
}