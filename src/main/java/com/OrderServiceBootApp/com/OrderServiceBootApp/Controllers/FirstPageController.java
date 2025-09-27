package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstPageController {

@GetMapping("/")
    public String home(HttpSession httpSession){
/*
Integer count = (Integer) httpSession.getAttribute("count");
if(count == null){
    count = 0;
}
httpSession.setAttribute("count", count++);
    System.out.println("session " + httpSession.getId() + ", count= " + count);
*/
    return "first/home";
}

}
