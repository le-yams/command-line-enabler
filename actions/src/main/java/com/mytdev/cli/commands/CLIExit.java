package com.mytdev.cli.commands;

import com.mytdev.cli.CLI;
import com.mytdev.cli.CLICommandProvider;
import com.mytdev.services.ServiceProvider;

/**
 * @author Yann D'Isanto
 */
@ServiceProvider(service = CLICommandProvider.class)
public final class CLIExit extends AbstractCLICommand {

    public CLIExit() {
        super("exit");
    }

    @Override
    public String getCommandDescription() {
        return "Exit this command line interface.";
    }

    @Override
    public int execute(CLI cli, String... args) {
        cli.exit();
        return 0;
    }
}
