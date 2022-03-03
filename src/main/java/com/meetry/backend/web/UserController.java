package com.meetry.backend.web;

import com.meetry.backend.aspect.annotation.Authenticated;
import com.meetry.backend.command.CommandExecutor;
import com.meetry.backend.command.LoginCommand;
import com.meetry.backend.command.RegisterPenelitiCommand;
import com.meetry.backend.command.model.LoginCommandRequest;
import com.meetry.backend.command.model.RegisterPenelitiCommandRequest;
import com.meetry.backend.entity.Session;
import com.meetry.backend.web.model.request.LoginWebRequest;
import com.meetry.backend.web.model.response.BaseResponse;
import com.meetry.backend.web.model.response.LoginWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

    private final CommandExecutor commandExecutor;

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/register/peneliti",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse register(
        @RequestPart("data") String data,
        @RequestPart(value = "fotoProfil", required = false) MultipartFile fotoProfil){

        RegisterPenelitiCommandRequest commandRequest = RegisterPenelitiCommandRequest.builder()
            .data(data)
            .fotoProfil(fotoProfil)
            .build();

        return commandExecutor.execute(RegisterPenelitiCommand.class, commandRequest);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public LoginWebResponse login(@RequestBody LoginWebRequest loginWebRequest, HttpServletResponse httpServletResponse){
        LoginCommandRequest commandRequest = LoginCommandRequest.builder()
            .email(loginWebRequest.getEmail())
            .password(loginWebRequest.getPassword())
            .httpServletResponse(httpServletResponse)
            .build();

        return commandExecutor.execute(LoginCommand.class, commandRequest);
    }
}
