package com.antman.extensionmarket42.utils;

public class SystemTimeWrapper {
    /**
     * This wrapper method allows mocking System class. Otherwise mocking of final classes is not allowed
     * @return Long - Current time in milliseconds
     */
    public Long currentTimeMillisSystem() {
        return System.currentTimeMillis();
    }
}
