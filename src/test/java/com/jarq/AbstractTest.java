package com.jarq;

import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

public abstract class AbstractTest {

    @Rule
    public MethodRule watchman = new TestWatchman() {
        public void starting(FrameworkMethod method) {
            System.out.println("Starting test: " + method.getName());
        }
    };

}
