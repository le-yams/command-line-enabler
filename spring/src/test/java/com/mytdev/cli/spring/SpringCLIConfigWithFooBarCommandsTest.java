package com.mytdev.cli.spring;

import com.mytdev.cli.CLICommandProvider;
import com.mytdev.cli.commands.CLIExit;
import com.mytdev.cli.commands.CLIHelp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yann D'Isanto
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfigurations.FooBarCommandsConfig.class)
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class SpringCLIConfigWithFooBarCommandsTest {

    @Autowired
    private SpringCLIConfig cliConfig;

    @Test
    public void testCLIDefaultCommandProvidersInjection() {
        List<String> providers = cliConfig.getCommandsManager().getProviders().stream()
                .map(CLICommandProvider::getCommandName).collect(toList());
        assertThat(providers, hasItems(new CLIHelp().getCommandName(), new CLIExit().getCommandName()));
    }

    @Test
    public void testCLICommandProvidersInjection() {
        List<String> providers = cliConfig.getCommandsManager().getProviders().stream()
                .map(CLICommandProvider::getCommandName).collect(toList());

        assertThat(providers.size(), is(4));
        assertThat(providers, hasItems("foo", "bar"));
    }

    @Test
    public void testCLIStartActionDefaultValue() {
        assertThat(cliConfig.getStartAction(), is(Optional.empty()));
    }

}