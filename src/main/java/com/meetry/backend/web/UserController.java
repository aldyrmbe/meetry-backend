package com.meetry.backend.web;

import com.meetry.backend.aspect.annotation.Authenticated;
import com.meetry.backend.command.*;
import com.meetry.backend.command.model.*;
import com.meetry.backend.entity.Session;
import com.meetry.backend.helper.AuthHelper;
import com.meetry.backend.web.model.request.LoginWebRequest;
import com.meetry.backend.web.model.response.BaseResponse;
import com.meetry.backend.web.model.response.LoginWebResponse;
import com.meetry.backend.web.model.response.UserWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

    private final CommandExecutor commandExecutor;
    private final AuthHelper authHelper;

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/register/peneliti",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse registerPeneliti(
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
        value = "/register/mitra",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse registerMitra(
        @RequestPart("data") String data,
        @RequestPart(value = "fotoProfil", required = false) MultipartFile fotoProfil){

        RegisterMitraCommandRequest commandRequest = RegisterMitraCommandRequest.builder()
            .data(data)
            .fotoProfil(fotoProfil)
            .build();

        return commandExecutor.execute(RegisterMitraCommand.class, commandRequest);
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

    @RequestMapping(
        method = RequestMethod.GET,
        value = ""
    )
    @Authenticated
    public UserWebResponse getCurrentLoggedInUser(HttpServletRequest httpServletRequest){

        Session session = authHelper.getSessionFromCookie(httpServletRequest);
        GetCurrentLoggedInUserCommandRequest commandRequest = GetCurrentLoggedInUserCommandRequest.builder()
            .session(session)
            .build();

        return commandExecutor.execute(GetCurrentLoggedInUserCommand.class, commandRequest);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/logout"
    )
    @Authenticated
    public BaseResponse logOut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        LogOutCommandRequest commandRequest = LogOutCommandRequest.builder()
            .httpServletRequest(httpServletRequest)
            .httpServletResponse(httpServletResponse)
            .build();

        return commandExecutor.execute(LogOutCommand.class, commandRequest);
    }
}
