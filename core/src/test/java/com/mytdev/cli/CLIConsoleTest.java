package com.mytdev.cli;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Yann D'Isanto
 */
public class CLIConsoleTest {

    private Console sysConsole;

    @Before
    public void setUp() {
        sysConsole = mock(Console.class);
    }

    @Test
    public void testSystemConsolePrintf() {
        CLIConsole.of(sysConsole).printf("%s 1", "test");
        verify(sysConsole).printf("%s 1", "test");
    }

    @Test
    public void testSystemConsolePrint() {
        CLIConsole.of(sysConsole).print("test");
        verify(sysConsole).printf("%s", "test");
    }

    @Test
    public void testSystemConsolePrintln() {
        CLIConsole.of(sysConsole).println("test");
        verify(sysConsole).printf("%s%n", "test");
    }

    @Test
    public void testSystemConsoleGetWriter() {
        PrintWriter pw = new PrintWriter(new ByteArrayOutputStream());
        when(sysConsole.writer()).thenReturn(pw);
        PrintWriter writer = CLIConsole.of(sysConsole).getWriter();
        verify(sysConsole).writer();
        assertThat(writer, is(pw));
    }

    @Test
    public void testSystemConsoleReadLine() {
        when(sysConsole.readLine()).thenReturn("user input");
        String line = CLIConsole.of(sysConsole).readLine();
        verify(sysConsole).readLine();
        assertThat(line, is("user input"));
    }

    @Test
    public void testSystemConsoleReadLineWithArgs() {
        when(sysConsole.readLine(anyString(), anyString())).thenReturn("user input");
        String line = CLIConsole.of(sysConsole).readLine("%s", ">");
        verify(sysConsole).readLine("%s", ">");
        assertThat(line, is("user input"));
    }

    @Test
    public void testSystemConsoleReadPassword() {
        when(sysConsole.readPassword()).thenReturn("1234".toCharArray());
        char[] pwd = CLIConsole.of(sysConsole).readPassword();
        verify(sysConsole).readPassword();
        assertThat(pwd, is("1234".toCharArray()));
    }

    @Test
    public void testSystemConsoleReadPasswordWithArgs() {
        when(sysConsole.readPassword(anyString(), anyString())).thenReturn("1234".toCharArray());
        char[] pwd = CLIConsole.of(sysConsole).readPassword("%s", ">");
        verify(sysConsole).readPassword("%s", ">");
        assertThat(pwd, is("1234".toCharArray()));
    }

}