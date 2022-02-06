package com.meetry.backend.command;

public interface Command<REQUEST, RESPONSE> {
  RESPONSE execute(REQUEST request);
}
