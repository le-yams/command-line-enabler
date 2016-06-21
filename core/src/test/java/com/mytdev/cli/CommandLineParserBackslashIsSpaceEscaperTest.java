package com.mytdev.cli;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Yann D'Isanto
 */
public class CommandLineParserBackslashIsSpaceEscaperTest {

    private static CommandLineParser parser = CommandLineParser::backslashIsSpaceEscaper;

    @Test
    public void testEmptyInputReturnsEmptyArray() {
        assertThat(
                parser.parse(""),
                is(new String[]{})
        );
    }

    @Test
    public void testNoSpace() {
        assertThat(
                parser.parse("command"),
                is(new String[]{"command"})
        );
    }

    @Test
    public void testNotEscapedSpaces() {
        assertThat(
                parser.parse("command arg1 arg2"),
                is(new String[]{"command", "arg1", "arg2"})
        );
    }

    @Test
    public void testMultipleNotEscapedSpaces() {
        assertThat(
                parser.parse("command  arg1   arg2"),
                is(new String[]{"command", "arg1", "arg2"})
        );
    }

    @Test
    public void testEscapedSpaces() {
        assertThat(
                parser.parse("command arg\\ 1 arg\\ 2"),
                is(new String[]{"command", "arg 1", "arg 2"})
        );
    }

    @Test
    public void testConsecutiveNotEscapedSpaces() {
        assertThat(
                parser.parse("command arg1  arg2"),
                is(new String[]{"command", "arg1", "arg2"})
        );
    }

    @Test
    public void testConsecutiveEscapedSpaces() {
        assertThat(
                parser.parse("command arg\\ \\ 1"),
                is(new String[]{"command", "arg  1"})
        );
    }

    @Test
    public void testEscapedSpacesFollowingNotEscapedSpace() {
        assertThat(
                parser.parse("command arg1 \\ arg2"),
                is(new String[]{"command", "arg1", " arg2"})
        );
    }

    @Test
    public void testNotEscapedSpacesFollowingEscapedSpace() {
        assertThat(
                parser.parse("command arg1\\  arg2"),
                is(new String[]{"command", "arg1 ", "arg2"})
        );
    }

    @Test
    public void testEscapedEscaper() {
        assertThat(
                parser.parse("command path\\\\to\\\\file"),
                is(new String[]{"command", "path\\to\\file"})
        );
    }

}