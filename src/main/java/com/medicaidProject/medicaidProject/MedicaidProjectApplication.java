package com.medicaidProject.medicaidProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@Controller

public class MedicaidProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicaidProjectApplication.class, args);
	}
	/*@GetMapping("/hello")
	public String hello(@RequestParam(defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}*/
	@GetMapping("")
	public String index(Model model){
		return "user/index";
	}
}
