package com.mytdev.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by pvvq7166 on 25/07/2017.
 */
public interface CommandLineParser {

    String[] parse(String commandLine);

    static String[] splitWithSpace(String commandLine) {
        return Stream.of(commandLine.trim().split(" "))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    static String[] backslashIsSpaceEscaper(String commandLine) {
        commandLine = commandLine.trim();
        List<String> args = new ArrayList<>();
//        int startIndex=0;
        StringBuilder argBuilder = new StringBuilder();
        boolean previousIsEscaper = false;
        for (int i = 0; i < commandLine.length(); i++) {
            char c = commandLine.charAt(i);

            switch (c) {
                case '\\':
                    if (previousIsEscaper) {
                        argBuilder.append(c);
                    }
                    previousIsEscaper = !previousIsEscaper;
                    break;
                case ' ':
                    if (previousIsEscaper) {
                        argBuilder.append(c);
                    } else if (argBuilder.length() > 0) {
                        args.add(argBuilder.toString());
                        argBuilder.setLength(0);
                    }
                    previousIsEscaper = false;
                    break;
                default:
                    argBuilder.append(c);
                    previousIsEscaper = false;
                    break;
            }
        }
        if (argBuilder.length() > 0) {
            args.add(argBuilder.toString());
        }
        return args.toArray(new String[args.size()]);
    }
}
