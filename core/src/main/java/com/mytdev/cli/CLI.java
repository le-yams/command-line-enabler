/*
 *  CLIEngine.java
 */
package com.mytdev.cli;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @author Yann D'Isanto
 */
@RequiredArgsConstructor
public final class CLI implements Runnable {

    @Getter
    private final CLIConsole console;

    private final CommandLineParser commandLineParser;

    private final CLICommandsManager commandsManager;

    private final Optional<CLIStartAction> startAction;

    private boolean exit = false;

    @Getter
    @Setter
    private String prompt = ">";

    @Getter
    private int lastCommandResult = 0;

    @Getter
    private boolean running = false;

    private final ResourceBundle resources = ResourceBundle.getBundle(CLI.class.getName());

    public CLI(CLIConfig config) {
        this(config.getConsole(), config.getCommandLineParser(), config.getCommandsManager(), config.getStartAction());
    }

    @Override
    public void run() {
        synchronized (this) {
            if (running) {
                return;
            }
            running = true;
        }
        if (!performStartAction()) {
            running = false;
            return;
        }
        while (!exit) {
            String input = console.readLine("%n%s", prompt);
            if (input == null) {
                break;
            }
            if (input.isEmpty()) {
                continue;
            }
            String[] commandLine = commandLineParser.parse(input);
            String commandName = commandLine[0];
            String[] args = Arrays.copyOfRange(commandLine, 1, commandLine.length);

            lastCommandResult = commandsManager.getProvider(commandName).map(provider -> {
                try {
                    CLICommand command = provider.getCommand();
                    return command.execute(this, args);
                } catch (IllegalArgumentException ex) {
                    console.println(ex.getLocalizedMessage());
                    console.println(getUsageOf(commandName).orElse("usage not found"));
                    return -2;
                } catch (CLICommandExecutionException ex) {
                    ex.printStackTrace(console.getWriter());
                    return ex.getCode();
                }
            }).orElseGet(() -> {
                console.println(resources.getString("unknown-command"));
                return -1;
            });
        }
        running = false;
    }

    private boolean performStartAction() {
        return startAction
                .map(action -> action.perform(this))
                .orElse(true);
    }

    public Collection<String> getAvailableCommands() {
        return commandsManager.getProviders().stream()
                .map(p -> String.format("%s: %s", p.getCommandName(), p.getCommandDescription()))
                .sorted()
                .collect(toList());
    }

    public Optional<String> getUsageOf(String command) {
        return commandsManager.getProvider(command).map(CLICommandProvider::getUsage);
    }

    public void exit() {
        exit = true;
    }

}
