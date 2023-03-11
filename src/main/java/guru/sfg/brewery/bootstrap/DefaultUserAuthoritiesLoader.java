package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import guru.sfg.brewery.security.Authority;
import guru.sfg.brewery.security.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultUserAuthoritiesLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadSecurityData();
    }

    private void loadSecurityData() {
        log.info("loading securityData");
        if (authorityRepository.count() == 0) {
            log.info("loading authorityData");
            Authority admin = Authority.builder().role("ROLE_ADMIN").build();
            Authority userRole = Authority.builder().role("ROLE_USER").build();
            Authority customer = Authority.builder().role("ROLE_CUSTOMER").build();
            authorityRepository.saveAll(Arrays.asList(admin, userRole, customer));

            log.info("loading userData");
            User userSpring = User.builder().username("spring").password(passwordEncoder.encode("guru")).authority(admin).build();
            User userUser = User.builder().username("user").password(passwordEncoder.encode("password")).authority(userRole).build();
            User userScott = User.builder().username("Scott").password(passwordEncoder.encode("tiger")).authority(customer).build();
            User userCustomer = User.builder().username("customer").password(passwordEncoder.encode("password")).authority(customer).build();
            userRepository.saveAll(Arrays.asList(userSpring, userUser, userScott, userCustomer));

            log.info("loaded authorities: " + authorityRepository.count() + " users: " + userRepository.count());

        }
    }

}
