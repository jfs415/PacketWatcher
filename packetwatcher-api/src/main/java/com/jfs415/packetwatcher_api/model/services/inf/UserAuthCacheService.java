package com.jfs415.packetwatcher_api.model.services.inf;

import com.jfs415.packetwatcher_api.exceptions.args.InvalidArgumentException;

public interface UserAuthCacheService {

    void addUserToken(String token, long loginTime) throws InvalidArgumentException;

    void addUserToken(String token) throws InvalidArgumentException;

    void removeUserToken(String token) throws InvalidArgumentException;

    boolean hasToken(String token);

    int cacheSize();

    void purgeAllExpiredTokens();
}
