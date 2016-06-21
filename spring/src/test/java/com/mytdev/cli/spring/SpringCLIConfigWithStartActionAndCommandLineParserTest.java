package com.mytdev.cli.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yann D'Isanto
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfigurations.StartActionAndCommandLineParserConfig.class)
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class SpringCLIConfigWithStartActionAndCommandLineParserTest {

    @Autowired
    SpringCLIConfig cliConfig;

    @Autowired
    SpringConfigurations.StartActionAndCommandLineParserConfig springConfig;

    @Test
    public void testStartActionInjection() {
        assertThat(
                cliConfig.getStartAction(),
                is(Optional.of(springConfig.getStartAction())));
    }

    @Test
    public void testCommandLineParserInjection() {
        assertThat(
                cliConfig.getCommandLineParser(),
                is(springConfig.getCommandLineParser())
        );
    }

}