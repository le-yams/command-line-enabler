package com.mytdev.cli;

import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Yann D'Isanto
 */
public class CLICommandsManagerTest {

    @Test
    public void testDeclaredCommandProviderServiceAreRetrieved() {
        CLICommandsManager manager = new CLICommandsManager(new TestCommandProvider("test"), new TestCommandProvider("foo"));
        assertThat(manager.getProviders().size(), is(2));
    }

    @Test
    public void testRetrievingExistingCommandReturnsIt() {
        CLICommandsManager manager = new CLICommandsManager(new TestCommandProvider("test"));
        assertThat(manager.getProvider("test").orElse(null), instanceOf(TestCommandProvider.class));
    }

    @Test
    public void testRetrievingUndefinedCommandReturnsEmptyOptional() {
        CLICommandsManager manager = new CLICommandsManager(new TestCommandProvider("test"));
        assertThat(manager.getProvider("foo"), is(Optional.empty()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultipleCommandsWithSameNameFails() {
        CLICommandsManager manager = new CLICommandsManager(new TestCommandProvider("test"), new TestCommandProvider("test"));
        assertThat(manager.getProvider("foo"), is(Optional.empty()));
    }


    @Test
    public void testJavaServiceLoader() throws CLICommandExecutionException {
        CLICommandsManager manager = CLICommandsManager.usingJavaServiceLoader();
        Collection<CLICommandProvider> providers = manager.getProviders();
        assertThat(providers.size(), is(1));
        assertThat(providers, hasItem(new TestCommandProvider()));
        assertThat(providers.iterator().next().getCommandName(), is("test"));
        assertThat(providers.iterator().next().getCommand().execute(null), is(TestCommandProvider.TEST_COMMAND.execute(null)));
    }

}
