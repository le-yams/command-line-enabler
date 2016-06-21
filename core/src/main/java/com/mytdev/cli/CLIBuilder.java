package com.mytdev.cli;

import lombok.Getter;

import java.util.Optional;

/**
 * @author Yann D'Isanto
 */
@Getter
//@Setter
//@Accessors(chain = true, fluent = true)
public final class CLIBuilder implements CLIConfig {

    private CLIConsole console = CLIConsole.system();

    private CommandLineParser commandLineParser = CommandLineParser::backslashIsSpaceEscaper;

    private CLICommandsManager commandsManager = CLICommandsManager.usingJavaServiceLoader();

    private Optional<CLIStartAction> startAction = Optional.empty();

    public CLI build() {
        return new CLI(this);
    }

    public CLIBuilder console(CLIConsole console) {
        this.console = console;
        return this;
    }

    public CLIBuilder commandLineParser(CommandLineParser commandLineParser) {
        this.commandLineParser = commandLineParser;
        return this;
    }

    public CLIBuilder commandsManager(CLICommandsManager commandsManager) {
        this.commandsManager = commandsManager;
        return this;
    }

    public CLIBuilder startAction(CLIStartAction startAction) {
        this.startAction = Optional.ofNullable(startAction);
        return this;
    }


}
