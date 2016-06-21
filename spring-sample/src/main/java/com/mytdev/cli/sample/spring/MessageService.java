package com.mytdev.cli.sample.spring;

import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

/**
 * @author Yann D'Isanto
 */
@Service
public class MessageService {

    private final ResourceBundle resources = ResourceBundle.getBundle(MessageService.class.getName());

        public String getMessage(String message) {
        return resources.getString(message);
    }

}
