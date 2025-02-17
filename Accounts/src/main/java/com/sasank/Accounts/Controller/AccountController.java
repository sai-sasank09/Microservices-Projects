package com.sasank.Accounts.Controller;
import com.sasank.Accounts.Entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {



    @GetMapping("/greet")
    public String greet(){
        return "Hoo World";
    }

}
