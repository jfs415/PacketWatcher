package com.jfs415.packetwatcher_core.api.service;

public class ConsoleCommandException extends RuntimeException {
    
    public ConsoleCommandException() {
        super("Unknown error while trying to execute command");
    }
    
    public ConsoleCommandException(String message) {
        super(message);
    }
    
    public static ConsoleCommandException unauthorized() {
        return new ConsoleCommandException("You do not have permission to execute that command");
    }
    
    public static ConsoleCommandException unknown() {
        return new ConsoleCommandException("Unknown Command");
    }

}
