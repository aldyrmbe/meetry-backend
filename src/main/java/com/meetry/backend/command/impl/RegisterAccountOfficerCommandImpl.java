package com.meetry.backend.command.impl;

import com.meetry.backend.command.RegisterAccountOfficerCommand;
import com.meetry.backend.command.model.RegisterAccountOfficerCommandRequest;
import com.meetry.backend.entity.constant.Role;
import com.meetry.backend.entity.user.AccountOfficer;
import com.meetry.backend.repository.user.AccountOfficerRepository;
import com.meetry.backend.web.exception.BaseException;
import com.meetry.backend.web.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterAccountOfficerCommandImpl implements RegisterAccountOfficerCommand {

    private final AccountOfficerRepository accountOfficerRepository;

    @Override
    public BaseResponse execute(RegisterAccountOfficerCommandRequest commandRequest) {

        register(commandRequest);

        return BaseResponse.builder()
            .code(200)
            .status("OK")
            .message("Account officer berhasil dibuat.")
            .build();
    }

    private void checkEmailAvailability(String email){

        if(accountOfficerRepository.findByEmail(email).isPresent())
            throw new BaseException("Email sudah digunakan.");
    }

    private String getEncryptedPassword(String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    private String generateDefaultProfilePicture(String name){
        return String.format("https://ui-avatars.com/api/?name=%s", name);
    }

    private void register(RegisterAccountOfficerCommandRequest commandRequest){

        checkEmailAvailability(commandRequest.getEmail());
        AccountOfficer accountOfficer = AccountOfficer.builder()
            .email(commandRequest.getEmail())
            .name(commandRequest.getName())
            .password(getEncryptedPassword(commandRequest.getPassword()))
            .role(Role.ACCOUNT_OFFICER)
            .profilePhoto(generateDefaultProfilePicture(commandRequest.getName()))
            .build();

        accountOfficerRepository.save(accountOfficer);
    }
}
