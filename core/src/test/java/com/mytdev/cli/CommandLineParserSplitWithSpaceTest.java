package com.mytdev.cli;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Yann D'Isanto
 */
public class CommandLineParserSplitWithSpaceTest {

    static CommandLineParser parser = CommandLineParser::splitWithSpace;

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

}