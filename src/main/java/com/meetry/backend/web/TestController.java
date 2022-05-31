package com.meetry.backend.web;

import com.meetry.backend.command.CommandExecutor;
import com.meetry.backend.command.TestCommand;
import com.meetry.backend.command.model.TestCommandRequest;
import com.meetry.backend.web.model.request.RealtimeNotificationWebSocketPayload;
import com.meetry.backend.web.model.request.TestWebRequest;
import com.meetry.backend.web.model.response.TestWebResponse;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TestController {

    private final CommandExecutor commandExecutor;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping(value = "/test")
    public String test(){
        return "String";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/testwebsocket")
    public void testWs(){
        RealtimeNotificationWebSocketPayload payload = new RealtimeNotificationWebSocketPayload(true);
        simpMessagingTemplate.convertAndSend("/notification/625f87318eb28e77769f1630", payload);
    }
}
