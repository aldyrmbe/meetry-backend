package com.meetry.backend.command.impl;

import com.meetry.backend.command.TestCommand;
import com.meetry.backend.command.model.TestCommandRequest;
import com.meetry.backend.web.model.response.TestWebResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestCommandImpl implements TestCommand {

    @Override
    public TestWebResponse execute(TestCommandRequest testCommandRequest) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return TestWebResponse.builder()
            .name(bCryptPasswordEncoder.encode(testCommandRequest.getName()))
            .className(testCommandRequest.getClassName())
            .build();
    }
}
