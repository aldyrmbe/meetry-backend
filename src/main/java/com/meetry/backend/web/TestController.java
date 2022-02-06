package com.meetry.backend.web;

import com.meetry.backend.command.CommandExecutor;
import com.meetry.backend.command.TestCommand;
import com.meetry.backend.command.model.TestCommandRequest;
import com.meetry.backend.web.model.request.TestWebRequest;
import com.meetry.backend.web.model.response.TestWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

    private final CommandExecutor commandExecutor;

    @PostMapping(value = "/test")
    public TestWebResponse test(@RequestBody TestWebRequest testWebRequest){
        TestCommandRequest testCommandRequest = TestCommandRequest.builder()
            .name(testWebRequest.getName())
            .className(testWebRequest.getClassName())
            .build();

        return commandExecutor.execute(TestCommand.class, testCommandRequest);
    }
}
