package com.mikitayasiulevich.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

class PluginConfiguration {
    var roles: Set<String> = emptySet()
}

val RoleBasedAuthPlugin = createRouteScopedPlugin(
    name = "RbacPlugin",
    createConfiguration = ::PluginConfiguration
) {
    val roles = pluginConfig.roles

    pluginConfig.apply {
        on(AuthenticationChecked) { call ->
            val tokenRole = getRolesFromToken(call = call)?.split(", ") ?: listOf()

            val authorized = tokenRole.any{ it in roles }

            if(!authorized) {
                println("The user does not have any of the following roles: $roles")
                call.respond(HttpStatusCode.Forbidden)
            }
        }
    }
}

private fun getRolesFromToken(call: ApplicationCall): String? = call.principal<JWTPrincipal>()
    ?.payload
    ?.getClaim("roles")
    ?.asString()