package com.meetry.backend.command;

import com.meetry.backend.command.model.TestCommandRequest;
import com.meetry.backend.web.model.response.TestWebResponse;

public interface TestCommand extends Command<TestCommandRequest, TestWebResponse> {
}
