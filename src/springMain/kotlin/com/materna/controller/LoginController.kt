package com.materna.controller

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class LoginController {

    @GetMapping("/login/status")
    @ResponseBody
    fun isLogin(auth : Authentication?) = auth?.isAuthenticated ?: false
}
