package com.mytdev.cli;

import java.util.Optional;

/**
 * @author Yann D'Isanto
 */
public interface CLIConfig {

    CLIConsole getConsole();

    CommandLineParser getCommandLineParser();

    CLICommandsManager getCommandsManager();

    Optional<CLIStartAction> getStartAction();
}
