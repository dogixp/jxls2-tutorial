package com.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author yunpd
 * index page
 *
 */
@Controller
public class IndexController {
	
	
	@GetMapping("/")
	public String index() {
		
		return "index";
	}

}
