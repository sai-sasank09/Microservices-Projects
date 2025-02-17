package com.sasank.Accounts.Controller;
import com.sasank.Accounts.Dto.CustomerDto;
import com.sasank.Accounts.Dto.ResponseDto;
import com.sasank.Accounts.Service.IAccountsService;
import com.sasank.Accounts.constants.AccountsConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api", produces = "application/json")
public class AccountsController {

    @Autowired
    private IAccountsService accountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
        accountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,"Account created successfully"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber){
        return ResponseEntity.ok(accountsService.fetchAccount(mobileNumber));
    }


}
