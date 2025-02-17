package com.sasank.Accounts.Controller;
import com.sasank.Accounts.Dto.CustomerDto;
import com.sasank.Accounts.Dto.ResponseDto;
import com.sasank.Accounts.constants.AccountsConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class AccountsController {


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,"Account created successfully"));
    }


}
