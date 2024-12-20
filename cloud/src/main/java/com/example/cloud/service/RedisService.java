package com.example.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // === String Operations ===

    // Save or Update a key-value pair
    public void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Save with expiration time (in seconds)
    public void saveDataWithExpiration(String key, String value, long timeoutInSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutInSeconds, TimeUnit.SECONDS);
    }

    // Get the value of a specific key
    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // Update data (overwrite an existing key's value)
    public void updateData(String key, String newValue) {
        redisTemplate.opsForValue().set(key, newValue);
    }

    // Delete a key
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    // Check if a key exists
    public boolean keyExists(String key) {
        return redisTemplate.hasKey(key);
    }

    // === List Operations ===

    // Add to a list (push to the end)
    public void addToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // Get all elements from a list
    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    // Remove an element from a list
    public void removeFromList(String key, String value) {
        redisTemplate.opsForList().remove(key, 1, value); // Removes the first occurrence
    }

    // === Set Operations ===

    // Add to a set
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    // Get all elements from a set
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // Check if a set contains a value
    public boolean isMemberOfSet(String key, String value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    // Remove a value from a set
    public void removeFromSet(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    // === Hash Operations ===

    // Add a key-value pair to a hash
    public void addToHash(String hashKey, String field, String value) {
        redisTemplate.opsForHash().put(hashKey, field, value);
    }

    // Get a value from a hash by field
    public Object getFromHash(String hashKey, String field) {
        return redisTemplate.opsForHash().get(hashKey, field);
    }

    // Get all key-value pairs from a hash
    public Map<Object, Object> getAllFromHash(String hashKey) {
        return redisTemplate.opsForHash().entries(hashKey);
    }

    // Remove a field from a hash
    public void removeFromHash(String hashKey, String field) {
        redisTemplate.opsForHash().delete(hashKey, field);
    }

    // === Expiration Management ===

    // Set an expiration time for a key
    public void setExpiration(String key, long timeoutInSeconds) {
        redisTemplate.expire(key, timeoutInSeconds, TimeUnit.SECONDS);
    }

    // Get the remaining time to live (TTL) of a key
    public long getTimeToLive(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    // === Utility ===

    // Delete multiple keys
    public void deleteMultipleKeys(List<String> keys) {
        redisTemplate.delete(keys);
    }

    // === Custom Methods for Authentication Attempts ===

    // Get remaining attempts for a given user (email)
    public int getAttempts(String email) {
        String attemptsStr = (String) redisTemplate.opsForValue().get("attempts:" + email);
        return (attemptsStr != null) ? Integer.parseInt(attemptsStr) : 0;
    }

    // Increment the number of attempts for a given user (email)
    public void incrementAttempts(String email) {
        redisTemplate.opsForValue().increment("attempts:" + email, 1);
    }

    // Reset the number of attempts for a given user (email)
    public void resetAttempts(String email) {
        redisTemplate.opsForValue().set("attempts:" + email, "0");
    }

    // Decrease the number of attempts for a given user (email)
    public void decrementAttempts(String email) {
        redisTemplate.opsForValue().increment("attempts:" + email, -1);
    }
}

