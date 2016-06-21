package com.mytdev.cli.commands;

import com.mytdev.cli.CLICommand;
import com.mytdev.cli.CLICommandProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Yann D'Isanto
 */
@AllArgsConstructor
public abstract class AbstractCLICommand implements CLICommand, CLICommandProvider {

    @Getter
    private final String commandName;

    @Override
    public CLICommand getCommand() {
        return this;
    }
}
