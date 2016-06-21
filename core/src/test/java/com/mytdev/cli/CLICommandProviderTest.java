package com.mytdev.cli;

import lombok.Value;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yann D'Isanto
 */
public class CLICommandProviderTest {


    @Test
    public void testDefaultDescriptionIsName() {
        CLICommandProvider provider = new TestCLICommandProvider("foo", null);
        assertThat(provider.getCommandDescription(), is(provider.getCommandName()));
    }

    @Test
    public void testDefaultUsageIsName() {
        CLICommandProvider provider = new TestCLICommandProvider("foo", null);
        assertThat(provider.getUsage(), is(provider.getCommandName()));
    }

    @Value
    class TestCLICommandProvider implements CLICommandProvider {
        String commandName;
        CLICommand command;
    }
}