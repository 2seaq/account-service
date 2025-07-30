// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}
}
