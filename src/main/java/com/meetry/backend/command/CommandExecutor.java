package com.meetry.backend.command;

public interface CommandExecutor {
  <REQUEST, RESPONSE> RESPONSE execute(Class<? extends Command<REQUEST, RESPONSE>> commandClass, REQUEST request);
}
