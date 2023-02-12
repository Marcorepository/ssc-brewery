package guru.sfg.brewery.web;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    private final String PASSWORD = "password";

    @Test
    public void testNoOp() {
        PasswordEncoder noOpPasswordEncoder = NoOpPasswordEncoder.getInstance();
        System.out.println(noOpPasswordEncoder.encode(PASSWORD));
        System.out.println(noOpPasswordEncoder.encode(PASSWORD));
    }

    // not recommended
    @Test
    public void hashingExample() {
        String hashMd5 = DigestUtils.md5DigestAsHex(PASSWORD.getBytes());
        System.out.println(hashMd5);
        hashMd5 = DigestUtils.md5DigestAsHex(PASSWORD.getBytes());
        System.out.println(hashMd5);

        String salted = PASSWORD + "someSalt";
        hashMd5 = DigestUtils.md5DigestAsHex(salted.getBytes());
        System.out.println(hashMd5);

        hashMd5 = DigestUtils.md5DigestAsHex(salted.getBytes());
        System.out.println(hashMd5);

    }
}
