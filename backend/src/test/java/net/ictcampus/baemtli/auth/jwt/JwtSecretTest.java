package net.ictcampus.baemtli.auth.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.ictcampus.baemtli.security.SecurityConstants;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JwtSecretTest {

    @Test
    public void testSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.SECRET);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        assertNotNull(key);
    }
}
