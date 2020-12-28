package com.doms;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Component
public class TestCtrl {

	@GetMapping("/api/test")
	public void test() {
		try {
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
