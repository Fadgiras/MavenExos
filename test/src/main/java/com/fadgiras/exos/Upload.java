package com.fadgiras.exos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

//Use controlle here : REST will send the returned obj in the response body
@Controller
public class Upload {

	@GetMapping("/upload")
	public String index() {
		return "upload";
	}

}