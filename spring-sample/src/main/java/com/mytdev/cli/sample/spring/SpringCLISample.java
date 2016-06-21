/*
 *  CLISample.java 
 */
package com.mytdev.cli.sample.spring;

import com.mytdev.cli.CLI;
import com.mytdev.cli.CLIStartAction;
import com.mytdev.cli.spring.SpringCLIConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @author Yann D'Isanto
 */
@Configuration
@Import(SpringCLIConfig.class)
@ComponentScan("com.mytdev.cli.sample.spring")
public class SpringCLISample {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringCLISample.class);
        ctx.getBean(CLI.class).run();
    }

    @Component
    public static class WelcomeAction implements CLIStartAction {

        @Autowired
        private MessageService messageService;

        @Override
        public boolean perform(CLI cli) {
            cli.getConsole().println(messageService.getMessage("yd"));
            cli.getConsole().println(messageService.getMessage("welcome"));
            cli.getConsole().println(messageService.getMessage("start_hint"));
            return true;
        }
    }


}
