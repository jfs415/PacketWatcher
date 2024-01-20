package com.jfs415.packetwatcher_api.model.services;

import com.jfs415.packetwatcher_api.exceptions.args.InvalidArgumentException;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidLoginTimeArgumentException;
import com.jfs415.packetwatcher_api.exceptions.args.InvalidLoginTokenArgumentException;
import com.jfs415.packetwatcher_api.model.services.inf.UserAuthCacheService;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserAuthCacheServiceImpl implements UserAuthCacheService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthCacheServiceImpl.class);

    @Value("${packetwatcher-api.jwt.token-expiry}")
    private Duration tokenExpiry;

    private final ConcurrentHashMap<String, Long> userTokenCache = new ConcurrentHashMap<>(); // <Token, loginTime>

    @Override
    public void addUserToken(String token, long loginTime) throws InvalidArgumentException {
        userTokenCache.put(token, loginTime);
    }

    @Override
    public void addUserToken(String token) throws InvalidArgumentException {
        long timestamp = System.currentTimeMillis();

        validate(token, timestamp);
        addUserToken(token, timestamp);
    }

    @Override
    public void removeUserToken(String token) throws InvalidArgumentException {
        validateToken(token);
        userTokenCache.remove(token);
    }

    @Override
    public boolean hasToken(String token) {
        validateToken(token);

        return userTokenCache.containsKey(token);
    }

    @Override
    public int cacheSize() {
        return userTokenCache.size();
    }

    @Override
    public void purgeAllExpiredTokens() {
        Long purgeTime = System.currentTimeMillis() - tokenExpiry.toMillis();

        userTokenCache.entrySet().removeIf(e -> e.getValue().compareTo(purgeTime) <= 0);
    }

    private void validate(String token, Long loginTime) throws InvalidArgumentException {
        validateToken(token);
        validateTimestamp(loginTime);
    }

    private void validateTimestamp(long loginTime) throws InvalidArgumentException {
        if (loginTime <= 0) {
            logger.debug("Encountered and invalid token timestamp");
            throw new InvalidLoginTimeArgumentException();
        }
    }

    private void validateToken(String token) throws InvalidArgumentException {
        if (token == null || token.isBlank()) {
            logger.debug("Encountered and invalid token");
            throw new InvalidLoginTokenArgumentException();
        }
    }
}
