package org.sfx.gateway.config;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@Slf4j
public class JWTTest {

    @Test
    public void JWTTest() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJhbnRpIiwic2NvcGUiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiIsIlJPTEVfQVBJIl0sImV4cCI6MTY4NjU4OTI0MSwiYXV0aG9yaXRpZXMiOlsidGVzdCJdLCJqdGkiOiJhNTIxY2ZmZC0wZDg3LTQ5MDktYWEwYi1kMDBiZTM3YTJlOTkiLCJjbGllbnRfaWQiOiJjMSJ9.CUWokL_zlr0aQt6-d86OUrm9Az3_E9rMSdqFOj1jeU0";
        val claimsJws = Jwts.parser()
                .setSigningKey("AntiO2".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token);
        val pause = 0;
    }
}
