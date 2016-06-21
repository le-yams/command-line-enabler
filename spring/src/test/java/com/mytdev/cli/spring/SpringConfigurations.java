package com.mytdev.cli.spring;

import com.mytdev.cli.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Yann D'Isanto
 */
public interface SpringConfigurations {


    @Configuration
    @ComponentScan(
            basePackages = "com.mytdev.cli.spring",
            excludeFilters = @Filter(type = FilterType.REGEX, pattern = "com.mytdev.cli.spring.SpringConfigurations.*")
    )
    public static class FooBarCommandsConfig {

        CLICommandProvider fooCommandProvider = Utils.mockCLICommandProvider();

        CLICommandProvider barCommandProvider = Utils.mockCLICommandProvider();

        {
            when(fooCommandProvider.getCommandName()).thenReturn("foo");
            when(barCommandProvider.getCommandName()).thenReturn("bar");
        }

        @Bean
        public CLICommandProvider getFooCommandProvider() {
            return fooCommandProvider;
        }

        @Bean
        public CLICommandProvider getBarCommandProvider() {
            return barCommandProvider;
        }

    }

    @Configuration
    @ComponentScan(
            basePackages = "com.mytdev.cli.spring",
            excludeFilters = @Filter(type = FilterType.REGEX, pattern = "com.mytdev.cli.spring.SpringConfigurations.*")
    )
    public static class StartActionAndCommandLineParserConfig {

        CLIStartAction startAction = mock(CLIStartAction.class);

        @Bean
        public CLIStartAction getStartAction() {
            return startAction;
        }

        @Bean
        public CommandLineParser getCommandLineParser() {
            return (string) -> string.split("\\+");
        }
    }

    static interface Utils {

        static CLICommandProvider mockCLICommandProvider() {
            CLICommand command = mock(CLICommand.class);
            CLICommandProvider provider = mock(CLICommandProvider.class);
            when(provider.getCommand()).thenReturn(command);
            return provider;
        }
    }


    @Configuration
    @ComponentScan(
            basePackages = "com.mytdev.cli",
            excludeFilters = @Filter(type = FilterType.REGEX, pattern = "com.mytdev.cli.spring.SpringConfigurations.*")
    )
    public static class ConsoleConfig {

        @Bean
        public CLIConsole getConsole() {
            return mock(CLIConsole.class);
        }
    }
}
