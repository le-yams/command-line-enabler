/*
 *  CLISample.java 
 */
package com.mytdev.cli.sample;

import com.mytdev.cli.CLI;
import com.mytdev.cli.CLIBuilder;
import com.mytdev.cli.CLIStartAction;
import com.mytdev.services.ServiceProvider;

import java.io.IOException;

/**
 *
 * @author Yann D'Isanto
 */
public class CLISample {

    public static void main(String[] args) throws IOException {
        new CLIBuilder().build().run();
    }

    @ServiceProvider(service = CLIStartAction.class)
    public static class WelcomeAction implements CLIStartAction {

        @Override
        public boolean perform(CLI cli) {
            cli.getConsole().println("\nWelcome to your CLI enabler :)\nType 'help' to display the available commands\n");
            return true;
        }
    }
}
