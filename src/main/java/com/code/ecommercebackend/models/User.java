package com.code.ecommercebackend.models;

import com.code.ecommercebackend.models.enums.Gender;
import com.code.ecommercebackend.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel{
    private String username;
    @JsonIgnore
    private String password;
    @Indexed(unique = true)
    private String email;
    private String name;
    @Field(name = "phone_number")
    private String phoneNumber;
    private Gender gender;
    private String avatar;
    private Boolean verify;
    @Field(name = "google_account_id")
    @JsonIgnore
    private String googleAccountId;
    @Field(name = "facebook_account_id")
    @JsonIgnore
    private String facebookAccountId;
    @JsonIgnore
    private String otp;
    private Set<Role> roles;
    private Set<Address> addresses;
}