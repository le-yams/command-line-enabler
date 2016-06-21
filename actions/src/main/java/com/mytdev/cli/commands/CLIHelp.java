package com.mytdev.cli.commands;

import com.mytdev.cli.CLI;
import com.mytdev.cli.CLICommand;
import com.mytdev.cli.CLICommandProvider;
import com.mytdev.services.ServiceProvider;

/**
 * @author Yann D'Isanto
 */
@ServiceProvider(service = CLICommandProvider.class)
public final class CLIHelp extends AbstractCLICommand {

    public CLIHelp() {
        super("help");
    }

    @Override
    public String getCommandDescription() {
        return "Display the available com.mytdev.cli.commands. Display a specified command help";
    }

    @Override
    public String getUsage() {
        return "help [command]";
    }

    @Override
    public CLICommand getCommand() {
        return this;
    }

    @Override
    public int execute(CLI cli, String... args) {

        switch (args.length) {
            case 0:
                cli.getConsole().println("available com.mytdev.cli.commands:\n");
                for (String command : cli.getAvailableCommands()) {
                    cli.getConsole().printf("- %s%n", command);
                }
                return 0;
            case 1:
                String command = args[0];
                cli.getConsole().println(
                        cli.getUsageOf(command)
                                .orElse(String.format("unknown command '%s'", command)));
                return 0;

            default:
                return 1;
        }
    }
}
