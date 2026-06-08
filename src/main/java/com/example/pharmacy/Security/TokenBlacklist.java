package com.example.pharmacy.Security;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklist {

    private final Set<String> blacklist =
            ConcurrentHashMap.newKeySet();

    public void add(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}