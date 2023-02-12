package guru.sfg.brewery.web;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    private final String PASSWORD = "password";

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
