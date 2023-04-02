package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@Builder
public class BeerAuthenticationManager {

    public boolean customerIdMatches(Authentication authentication, UUID customerId) {
        User user = (User) authentication.getPrincipal();
        return user.getId().equals(customerId);
    }
}
