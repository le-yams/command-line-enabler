/*
 * CommandExecutionException.java
 */
package com.mytdev.cli;

import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
public class CLICommandExecutionException extends Exception {

    private static final long serialVersionUID = 1L;

    @Getter
    private final int code;

    public CLICommandExecutionException(int code) {
        this.code = code;
    }

    public CLICommandExecutionException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CLICommandExecutionException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CLICommandExecutionException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

}
