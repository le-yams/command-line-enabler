package com.mytdev.cli.sample;

import com.mytdev.cli.CLI;
import com.mytdev.cli.CLICommandProvider;
import com.mytdev.cli.commands.AbstractCLICommand;
import com.mytdev.services.ServiceProvider;

import java.util.ResourceBundle;

/**
 * @author Yann D'Isanto
 */
@ServiceProvider(service = CLICommandProvider.class)
public final class VersionCommand extends AbstractCLICommand {

    private final String version;

    public VersionCommand() {
        super("version");
        version = ResourceBundle.getBundle(VersionCommand.class.getName()).getString("version");
    }

    @Override
    public String getCommandDescription() {
        return "Display this command line interface version.";
    }

    @Override
    public int execute(CLI cli, String... args) {
        cli.getConsole().println(version);
        return 0;
    }
}
