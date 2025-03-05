package com.sasank.Accounts.Service.impl;

import com.sasank.Accounts.Dto.AccountsDto;
import com.sasank.Accounts.Dto.CustomerDto;
import com.sasank.Accounts.Entity.Accounts;
import com.sasank.Accounts.Entity.Customer;
import com.sasank.Accounts.Repository.AccountsRepo;
import com.sasank.Accounts.Repository.CustomerRepo;
import com.sasank.Accounts.Service.IAccountsService;
import com.sasank.Accounts.constants.AccountsConstants;
import com.sasank.Accounts.exception.ResourceNotFoundException;
import com.sasank.Accounts.mapper.AccountsMapper;
import com.sasank.Accounts.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
      if(optionalCustomer.isPresent()){
            throw new RuntimeException("Customer already exists "+customerDto.getMobileNumber());
      }

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
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepo.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }


    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());
        return true;
    }


}
