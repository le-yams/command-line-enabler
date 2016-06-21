package com.mytdev.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

/**
 * @author Yann D'Isanto
 */
public final class CLICommandsManager {

    private static final Logger LOG = LoggerFactory.getLogger(CLICommandsManager.class);

    private final Map<String, CLICommandProvider> commandProviders = new HashMap<>();

    /**
     *
     * @param providers
     * @throws IllegalArgumentException if several providers have the same command name
     */
    public CLICommandsManager(Supplier<Iterable<CLICommandProvider>> providers) {
        this(providers.get());
    }

    /**
     *
     * @param providers
     * @throws IllegalArgumentException if several providers have the same command name
     */
    public CLICommandsManager(CLICommandProvider... providers) {
        this(asList(providers));
    }

    /**
     *
     * @param providers
     * @throws IllegalArgumentException if several providers have the same command name
     */
    public CLICommandsManager(Iterable<CLICommandProvider> providers) {
        LOG.debug("loading command providers...");
        for (CLICommandProvider provider :
                providers) {
            String command = provider.getCommandName();
            CLICommandProvider other = commandProviders.get(command);
            if (other != null) {
                throw new IllegalArgumentException(
                        String.format(
                                "command '%s' already defined by %s: %s",
                                command,
                                other.getClass().getName(),
                                provider.getClass().getName()
                        )
                );
            }
            commandProviders.put(command, provider);
        }
        LOG.debug("{} provider(s) loaded", commandProviders.size());
    }

    public Optional<CLICommandProvider> getProvider(String name) throws IllegalArgumentException {
        return Optional.ofNullable(commandProviders.get(name));
    }

    public Collection<CLICommandProvider> getProviders() {
        return commandProviders.values();
    }


    public static CLICommandsManager usingJavaServiceLoader() {
        return new CLICommandsManager(() -> ServiceLoader.load(CLICommandProvider.class));
    }

}
