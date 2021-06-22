package com.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class NonUniqueBeanTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    ApplicationContext applicationContext;

    @Test
    void testItWorks() {
        assertTrue(application.isRunning());
    }

    @Test
    void nonUniqueBeanException() {
        assertEquals(SampleSingleton.class, applicationContext.getBean(SampleSingleton.class).getClass());
        assertEquals(SampleSingleton.class, applicationContext.getBean(SampleSingleton.class, Qualifiers.byName("Sample")).getClass());

        // This worked with Mn 2.5.x
        assertEquals(SampleSingleton.class, applicationContext.getBean(Object.class, Qualifiers.byName("Sample")).getClass());

    }

}
