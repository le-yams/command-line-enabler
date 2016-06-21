package com.mytdev.cli.spring;

import com.mytdev.cli.CLI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Yann D'Isanto
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfigurations.ConsoleConfig.class)
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class SpringCLIConfigWithConsoleTest {

    @Autowired
    CLI cli;

    @Autowired
    SpringConfigurations.ConsoleConfig springConfig;


    @Test
    public void testCLIInjection() {
        assertThat(cli, notNullValue());
    }

    @Test
    public void testCLIConsoleInjection() {
        assertThat(cli.getConsole(), is(springConfig.getConsole()));
    }

}