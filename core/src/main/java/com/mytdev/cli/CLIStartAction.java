package com.mytdev.cli;

/**
 * An action to be called at cli startup.
 *
 * @author Yann D'Isanto
 */
public interface CLIStartAction {

    /**
     * Performs this action at cli startup.
     * @param cli the starting {@link CLI} instance.
     * @return true if the cli can continue starting, false otherwise.
     */
    boolean perform(CLI cli);

}
