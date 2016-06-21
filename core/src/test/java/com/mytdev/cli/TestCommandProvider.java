package com.mytdev.cli;

import com.mytdev.services.ServiceProvider;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author Yann D'Isanto
 */
@Value
@AllArgsConstructor
@ServiceProvider(service = CLICommandProvider.class)
public class TestCommandProvider implements CLICommandProvider {

    String commandName;
    CLICommand command;

    public TestCommandProvider() {
        this("test");
    }

    public TestCommandProvider(String commandName) {
        this(commandName, TEST_COMMAND);
    }

    public static final CLICommand TEST_COMMAND = new CLICommand() {

        @Override
        public int execute(CLI cli, String... args) throws IllegalArgumentException, CLICommandExecutionException {
            return 42;
        }
    };

    public static TestCommandProvider command(String name) {
        return new TestCommandProvider(name);
    }

//    public static TestCommandProvider command(String name, String description, String usage) {
//        return new TestCommandProvider(name);
//    }
}
