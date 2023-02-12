package guru.sfg.brewery.web;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import javax.naming.ldap.LdapContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncodingTests {

    private final String PASSWORD = "password";

    @Test
    public void testNoOp() {
        PasswordEncoder noOpPasswordEncoder = NoOpPasswordEncoder.getInstance();
        System.out.println(noOpPasswordEncoder.encode(PASSWORD));
        System.out.println(noOpPasswordEncoder.encode(PASSWORD));
    }

    @Test
    public void testLdapPasswordEncoder() {
        PasswordEncoder ldapPwEncoder = new LdapShaPasswordEncoder();
        String encodedPassword1 = ldapPwEncoder.encode(PASSWORD);
        System.out.println(encodedPassword1);
        String encodedPassword2 = ldapPwEncoder.encode(PASSWORD);
        System.out.println(encodedPassword2);


        assertTrue(ldapPwEncoder.matches(PASSWORD, encodedPassword1));
        assertTrue(ldapPwEncoder.matches(PASSWORD, encodedPassword2));

    }

    @Test
    public void testSha256Encoder() {
        PasswordEncoder pwe = new StandardPasswordEncoder();
        String pwe1 = pwe.encode(PASSWORD);
        String pwe2 = pwe.encode(PASSWORD);

        System.out.println(pwe1);
        System.out.println(pwe2);

        assertTrue(pwe.matches(PASSWORD, pwe1));
        assertTrue(pwe.matches(PASSWORD, pwe2));

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
