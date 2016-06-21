package com.mytdev.cli;

import lombok.Value;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Yann D'Isanto
 */
public class CLITest {

    @Test
    public void testGetConsole() {
        TestCLIConsole console = new TestCLIConsole();
        CLI cli = new CLIBuilder()
                .console(console)
                .build();
        assertThat(cli.getConsole(), is(console));
    }


    @Test
    public void testAvailableCommands() {
        TestCLIConsole console = new TestCLIConsole();
        CLICommandProvider command1 = mock(CLICommandProvider.class);
        CLICommandProvider command2 = mock(CLICommandProvider.class);
        when(command1.getCommandName()).thenReturn("foo");
        when(command2.getCommandName()).thenReturn("bar");
        CLI cli = new CLIBuilder()
                .console(console)
                .commandsManager(new CLICommandsManager(
                        command1,
                        command2
                ))
                .build();
        assertThat(cli.getAvailableCommands().size(), is(2));
        assertThat(cli.getAvailableCommands(), hasItems("foo: null", "bar: null"));
    }

    @Test
    public void testCommandUsage() {
        TestCLIConsole console = new TestCLIConsole();
        CLICommandProvider command1 = mock(CLICommandProvider.class);
        when(command1.getCommandName()).thenReturn("foo");
        when(command1.getUsage()).thenReturn("foo usage");
        CLI cli = new CLIBuilder()
                .console(console)
                .commandsManager(new CLICommandsManager(
                        command1
                ))
                .build();
        assertThat(cli.getUsageOf("foo"), is(Optional.of("foo usage")));
        assertThat(cli.getUsageOf("bar"), is(Optional.empty()));
    }

    @Test
    public void testRunWithNoInputGivesNoOutput() {
        TestCLIConsole console = new TestCLIConsole();
        new CLIBuilder().console(console).build().run();

        assertThat(console.outputs.size(), is(0));
    }

    @Test
    public void testRunWithUnknownCommandDisplaysResourcesMessage() {
        ResourceBundle resources = ResourceBundle.getBundle(CLI.class.getName());

        TestCLIConsole console = new TestCLIConsole(
                "foo"
        );
        new CLIBuilder().console(console).build().run();

        assertThat(console.outputs.size(), is(1));
        assertThat(console.outputs.get(0), is(resources.getString("unknown-command")));
    }

    @Test
    public void testRunWithCommandInvokingCliExitDoStopTheRun() throws CLICommandExecutionException {
        TestCLIConsole console = new TestCLIConsole(
                "exit",
                "foo"
        );
        CLICommand exitCommand = mock(CLICommand.class);
        when(exitCommand.execute(any(CLI.class))).thenAnswer((invocation) -> {
            CLI cli = (CLI) invocation.getArguments()[0];
            cli.exit();
            return 0;
        });

        CLICommandProvider command1 = mock(CLICommandProvider.class);
        when(command1.getCommandName()).thenReturn("exit");
        when(command1.getCommand()).thenReturn(exitCommand);

        CLI cli = new CLIBuilder()
                .console(console)
                .commandsManager(new CLICommandsManager(command1))
                .build();
        cli.run();

        verify(exitCommand).execute(any(CLI.class));
        assertThat(console.outputs.size(), is(0));
    }

    @Test
    public void testInvalidCommandArguments() throws CLICommandExecutionException {
        // Arrange
        TestCLIConsole console = new TestCLIConsole(
                "foo"
        );
        CLICommand command = mock(CLICommand.class);
        when(command.execute(any(CLI.class))).thenThrow(new IllegalArgumentException("wrong args"));
        CLICommandProvider commandProvider = mock(CLICommandProvider.class);
        when(commandProvider.getCommandName()).thenReturn("foo");
        when(commandProvider.getCommand()).thenReturn(command);
        when(commandProvider.getUsage()).thenReturn("foo usage");
        CLI cli = new CLIBuilder()
                .console(console)
                .commandsManager(new CLICommandsManager(commandProvider))
                .build();

        // Act
        cli.run();

        // Assert
        assertThat(console.outputs.size(), is(2));
        assertThat(console.outputs.get(0), is("wrong args"));
        assertThat(console.outputs.get(1), is("foo usage"));
    }

    @Test
    public void testRunWithSpecifiedStartActionDoInvokesIt() {
        // Arrange
        CLIStartAction startAction = mock(CLIStartAction.class);
        ArgumentCaptor<CLI> cliCaptor = ArgumentCaptor.forClass(CLI.class);
        TestCLIConsole console = new TestCLIConsole();

        // Act
        CLI cli = new CLIBuilder().console(console).startAction(startAction).build();
        cli.run();

        // Assert
        verify(startAction).perform(cliCaptor.capture());
        assertThat(cliCaptor.getValue(), is(cli));
    }


    class TestCLIConsole implements CLIConsole {

        private final List<String> inputs;

        private final List<String> outputs = new ArrayList<>();

        public TestCLIConsole(String... inputs) {
            this(asList(inputs));
        }

        public TestCLIConsole(List<String> inputs) {
            this.inputs = new ArrayList<>(inputs);
        }

        @Override
        public void printf(String format, Object... args) {
            outputs.add(String.format(format, args));
        }

        @Override
        public void println(Object object) {
            print(object); // don't care of line separator for tests
        }

        @Override
        public String readLine(String fmt, Object... args) {
            return readLine();
        }

        @Override
        public String readLine() {
            if (inputs.isEmpty()) {
                return null;
            }
            return inputs.remove(0);
        }

        @Override
        public char[] readPassword(String fmt, Object... args) {
            return readPassword();
        }

        @Override
        public char[] readPassword() {
            return readLine().toCharArray();
        }

        @Override
        public PrintWriter getWriter() {
            return null;
        }

        @Value
        class Output {
            String format;
            Object[] args;
        }
    }



}
