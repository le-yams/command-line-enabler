package com.mytdev.cli;

import java.io.Console;
import java.io.PrintWriter;

/**
 * This interface allows to read/write in the {@link CLI} input/output.
 *
 * @author Yann D'Isanto
 */
public interface CLIConsole {


    /**
     * A convenience method to write a formatted string to this console's
     * using the specified format string and arguments.
     *
     * @param  format
     *         A format string as described in {@link java.util.Formatter} doc.
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         {@literal null} argument depends on the conversion.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the "Details" section of the
     *          formatter class specification.
     *
     * @see java.util.Formatter
     */
    void printf(String format, Object ... args);

    /**
     * A convenience method to write the given object string representation
     * to this console's.
     *
     * @param  object the object to write in the console.
     *
     * @see java.lang.Object#toString
     */
    default void print(Object object) {
        printf("%s", object);
    }

    /**
     * A convenience method to write the given object string representation
     *  and then terminates the line to this console's.
     *
     * @param  object the object to write in the console.
     *
     * @see java.lang.Object#toString
     */
    default void println(Object object) {
        printf("%s%n", object);
    }

    /**
     * Provides a formatted prompt, then reads a single line of text from the
     * console.
     *
     * @param  fmt
     *         A format string as described in {@link java.util.Formatter} doc.
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the "Details" section
     *          of the formatter class specification.
     *
     * @throws java.io.IOError
     *         If an I/O error occurs.
     *
     * @return  A string containing the line read from the console, not
     *          including any line-termination characters, or {@literal null}
     *          if an end of stream has been reached.
     */
    String readLine(String fmt, Object ... args);

    /**
     * Reads a single line of text from the console.
     *
     * @throws java.io.IOError
     *         If an I/O error occurs.
     *
     * @return  A string containing the line read from the console, not
     *          including any line-termination characters, or {@literal null}
     *          if an end of stream has been reached.
     */
    String readLine();

    /**
     * Provides a formatted prompt, then reads a password or passphrase from
     * the console with echoing disabled.
     *
     * @param  fmt
     *         A format string as described in {@link java.util.Formatter} doc.
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the "Details" section
     *          of the formatter class specification.
     *
     * @throws java.io.IOError
     *         If an I/O error occurs.
     *
     * @return  A character array containing the password or passphrase read
     *          from the console, not including any line-termination characters,
     *          or {@literal null} if an end of stream has been reached.
     */
    char[] readPassword(String fmt, Object ... args);

    /**
     * Reads a password or passphrase from the console with echoing disabled
     *
     * @throws java.io.IOError
     *         If an I/O error occurs.
     *
     * @return  A character array containing the password or passphrase read
     *          from the console, not including any line-termination characters,
     *          or {@literal null} if an end of stream has been reached.
     */
    char[] readPassword();


    /**
     * Retrieves the unique {@link java.io.PrintWriter PrintWriter} object
     * associated with this console.
     *
     * @return  The printwriter associated with this console
     */
    PrintWriter getWriter();

    static CLIConsole system() {
        return of(System.console());
    }

    static CLIConsole of(Console console) {
        if (console == null) {
            return null;//throw new UnsupportedOperationException("no system console available");
        }
        return new CLIConsole() {

            @Override
            public void printf(String format, Object ... args) {
                console.printf(format, args);
            }

            @Override
            public String readLine(String fmt, Object... args) {
                return console.readLine(fmt, args);
            }

            @Override
            public String readLine() {
                return console.readLine();
            }

            @Override
            public char[] readPassword(String fmt, Object... args) {
                return console.readPassword(fmt, args);
            }

            @Override
            public char[] readPassword() {
                return console.readPassword();
            }

            @Override
            public PrintWriter getWriter() {
                return console.writer();
            }
        };
    }
}

