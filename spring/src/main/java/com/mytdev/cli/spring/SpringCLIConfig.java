package com.mytdev.cli.spring;

import com.mytdev.cli.*;
import com.mytdev.cli.commands.CLIExit;
import com.mytdev.cli.commands.CLIHelp;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * @author Yann D'Isanto
 */
@Getter
@Configuration
public class SpringCLIConfig implements CLIConfig {

    @Autowired(required = false)
    private CLIConsole console;

    @Autowired(required = false)
    private CommandLineParser commandLineParser;

    @Autowired(required = false)
    @Getter(AccessLevel.NONE)
    private CLIStartAction startAction;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        if(console == null) {
            console = CLIConsole.system();
        }
        if(commandLineParser == null) {
            commandLineParser = CommandLineParser::backslashIsSpaceEscaper;
        }
    }

    @Override
    public CLICommandsManager getCommandsManager() {
        return new CLICommandsManager(() -> applicationContext.getBeansOfType(CLICommandProvider.class).values());
    }

    @Override
    public Optional<CLIStartAction> getStartAction() {
        return Optional.ofNullable(startAction);
    }

    @Bean
    public CLI cli() {
        return new CLI(this);
    }

    @Bean
    public CLIExit getExitCommand() {
        return new CLIExit();
    }

    @Bean
    public CLIHelp getHelpCommand() {
        return new CLIHelp();
    }
}
