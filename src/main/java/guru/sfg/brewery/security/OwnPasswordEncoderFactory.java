package guru.sfg.brewery.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class OwnPasswordEncoderFactory {

    public static PasswordEncoder createPasswordEncoder() {
        String encodingId = "bcrypt17";
        Map<String, PasswordEncoder> encoders = new HashMap();
        encoders.put("bcrypt", new BCryptPasswordEncoder(15));
        encoders.put("bcrypt17", new BCryptPasswordEncoder(17));
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("sha256", new StandardPasswordEncoder());
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}
