package com.sasank.Accounts.Service.impl;

import com.sasank.Accounts.Dto.CustomerDto;
import com.sasank.Accounts.Entity.Accounts;
import com.sasank.Accounts.Entity.Customer;
import com.sasank.Accounts.Repository.AccountsRepo;
import com.sasank.Accounts.Repository.CustomerRepo;
import com.sasank.Accounts.Service.IAccountsService;
import com.sasank.Accounts.constants.AccountsConstants;
import com.sasank.Accounts.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
      Optional<Customer> optionalCustomer=  customerRepo.findByMobileNumber(customerDto.getMobileNumber());
       Customer savedCustomer= customerRepo.save(customer);
        accountsRepo.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        return null;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        return false;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        return false;
    }
}
