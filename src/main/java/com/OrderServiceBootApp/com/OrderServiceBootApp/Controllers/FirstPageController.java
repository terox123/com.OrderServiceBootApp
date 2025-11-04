package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class FirstPageController {



    @GetMapping("/")
    public String home(HttpSession httpSession) throws IOException {
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
