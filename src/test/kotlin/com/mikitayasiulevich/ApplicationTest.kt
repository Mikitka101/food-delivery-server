package com.mikitayasiulevich

import com.mikitayasiulevich.data.model.requests.RegisterRequest
import com.mikitayasiulevich.domain.usecase.UserUseCase
import com.mikitayasiulevich.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.*

// Интерфейс сервиса регистрации
interface RegistrationService {
    suspend fun register(username: String, email: String): RegistrationResult
}

// Результат регистрации
sealed class RegistrationResult {
    data class Success(val message: String) : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}

// Тестовая реализация сервиса регистрации
class TestRegistrationService : RegistrationService {
    override suspend fun register(username: String, email: String): RegistrationResult {
        return if (username == "testuser" && email == "test@example.com") {
            RegistrationResult.Success("User registered successfully")
        } else {
            RegistrationResult.Error("Invalid username or email")
        }
    }
}


// Модуль приложения
fun Application.registrationModule(registrationService: RegistrationService) {
    routing {
        post("/register") {
            val username = call.receiveParameters()["username"]
            val email = call.receiveParameters()["email"]

            if (username == null || email == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing username or email")
                return@post
            }

            val result = registrationService.register(username, email)
            when (result) {
                is RegistrationResult.Success -> call.respond(HttpStatusCode.OK, result.message)
                is RegistrationResult.Error -> call.respond(HttpStatusCode.BadRequest, result.message)
            }
        }
    }
}

// Тест
class RegistrationTest {
    @Test
    fun testSuccessfulRegistration() = testApplication {
        val registrationService = TestRegistrationService()
        application {
            registrationModule(registrationService)
        }

        val response = client.post("/register") {
            setBody(listOf("username" to "testuser", "email" to "test@example.com").formUrlEncode())
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("User registered successfully", response.bodyAsText())
    }


    @Test
    fun testFailedRegistration() = testApplication {
        val registrationService = TestRegistrationService()
        application {
            registrationModule(registrationService)
        }

        val response = client.post("/register") {
            setBody(listOf("username" to "invaliduser", "email" to "invalid@example.com").formUrlEncode())
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals("Invalid username or email", response.bodyAsText())
    }
}

