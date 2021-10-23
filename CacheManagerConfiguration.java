package com.twa.flights.api.clusters.caching;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.twa.flights.api.clusters.caching.configuration.CacheConfiguration;

@Configuration
public class CacheManagerConfiguration {

    @Autowired
    private CacheConfiguration configuration;

    @Bean
    public CacheManager getCacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.setCacheNames(Set.of("catalog_cache"));

        return caffeineCacheManager;
    }

    @Bean
    private Caffeine caffeine() {
        return Caffeine.newBuilder().maximumSize(configuration.getMaxSize())
                .expireAfterWrite(configuration.getDuration(), TimeUnit.SECONDS);
    }

}
