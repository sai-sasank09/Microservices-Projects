package com.sasank.Accounts.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "This class provides the Customer details")
public class CustomerDto {

    @Schema(description = "Name of the Customer", example = "Sasank")
    @NotEmpty(message = "Name is mandatory")
    @Size(min = 2, message = "Name should have atleast 2 characters")
    private String name;


    @Schema(
            description = "Email address of the customer", example = "sanjeevsaisasank9@gmail.com"
    )
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;


    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;


    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
