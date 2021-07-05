package com.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

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
    void getBean() {
        assertEquals(SampleSingleton.class, applicationContext.getBean(SampleSingleton.class).getClass());
        assertEquals(SampleSingleton.class, applicationContext.getBean(SampleSingleton.class, Qualifiers.byName("Sample")).getClass());

        // This worked with Mn 2.5.x and has been fixed in https://github.com/micronaut-projects/micronaut-core/issues/5639
        assertEquals(SampleSingleton.class, applicationContext.getBean(Object.class, Qualifiers.byName("Sample")).getClass());
    }

    @Test
    void containsBean() {
        assertTrue(applicationContext.containsBean(Object.class, Qualifiers.byName("Sample")));

        // This worked with Mn 2.5.x but is now failing with 3.0.0-M2 and 3.0.0-SNAPSHOT
        assertFalse(applicationContext.containsBean(Object.class, Qualifiers.byName("some-random-non-existing-name")));

    }

}
