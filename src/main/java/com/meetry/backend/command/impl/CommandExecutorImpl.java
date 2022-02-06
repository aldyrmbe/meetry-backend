package com.meetry.backend.command.impl;

import com.meetry.backend.command.Command;
import com.meetry.backend.command.CommandExecutor;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandExecutorImpl implements CommandExecutor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public <REQUEST, RESPONSE> RESPONSE execute(Class<? extends Command<REQUEST, RESPONSE>> commandClass, REQUEST request) {
        Command<REQUEST, RESPONSE> bean = applicationContext.getBean(commandClass);
        return bean.execute(request);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
