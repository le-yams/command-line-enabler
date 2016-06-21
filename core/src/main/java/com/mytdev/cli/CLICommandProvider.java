package com.mytdev.cli;

/**
 * Allows to register a command to the {@link CLI} so it can be available at runtime.
 *
 * @author Yann D'Isanto
 */
public interface CLICommandProvider {

    String getCommandName();

    CLICommand getCommand();

    default String getCommandDescription() {
        return getCommandName();
    }

    default String getUsage() {
        return getCommandName();
    }

}
