package com.jfs415.packetwatcher_core.api.service;

import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    @Override
    public void processCommand() throws ConsoleCommandException {
        // TODO document why this method is empty
    }

    @Override
    public void processQuery() {
        
    }

    @Override
    public OutputStream display() {
        return null;
    }
}
