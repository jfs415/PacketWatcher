package com.jfs415.packetwatcher_core.api.service;

import java.io.OutputStream;

public interface ConsoleService {

    void processCommand();

    void processQuery();

    OutputStream display();
}
