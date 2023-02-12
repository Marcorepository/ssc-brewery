package guru.sfg.brewery.web;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    private final String PASSWORD = "password";

    @Test
    public void hashingExample() {
        byte[] hashMd5 = DigestUtils.md5Digest(PASSWORD.getBytes());
        System.out.println(hashMd5);

        String salted = PASSWORD + "someSalt";
        hashMd5 = DigestUtils.md5Digest(salted.getBytes());
        System.out.println(hashMd5);

    }
}
