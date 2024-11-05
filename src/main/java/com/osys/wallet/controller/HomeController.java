// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}
