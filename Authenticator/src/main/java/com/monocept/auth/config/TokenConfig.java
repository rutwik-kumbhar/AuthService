package com.monocept.auth.config;

import java.time.ZonedDateTime;
import java.time.Instant;

public class TokenConfig {
    public static final ZonedDateTime EXPIRATION_TIME = ZonedDateTime.now().plusHours(12); // ✅ 12 hours
    public static final Instant EXPIRATION_INSTANT = Instant.now().plusSeconds(12 * 60 * 60); // ✅ 12 hours
}
