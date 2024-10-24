package com.mikitayasiulevich.utils

import com.mikitayasiulevich.plugins.RoleBasedAuthPlugin
import io.ktor.server.routing.*

fun Route.authorized(
    vararg hasAnyRole: String,
    build: Route.() -> Unit
) {
    install(RoleBasedAuthPlugin) { roles = hasAnyRole.toSet() }
    build()
}
