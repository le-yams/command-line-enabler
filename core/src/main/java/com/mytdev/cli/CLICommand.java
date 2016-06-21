/*
 *  Command.java 
 */
package com.mytdev.cli;

/**
 * A CLI command.
 *
 * @author Yann D'Isanto
 */
public interface CLICommand {

    /**
     *  Executes this command in the given {@link CLI} with the specified arguments.
     *
     * @param cli  the {@link CLI} instance.
     * @param args the command execution arguments.
     * @return the execution result.
     * @throws IllegalArgumentException if the specified arguments are invalid for this command
     * @throws CLICommandExecutionException if the command execution failed
     */
    int execute(CLI cli, String... args) throws IllegalArgumentException, CLICommandExecutionException;

}
