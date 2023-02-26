package guru.sfg.brewery.security;

import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    @Transactional // sonst sind die authorities wegen lazy initialization nicht in dem User-Objekt vorhanden
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("userDetailService loadUserByUsername");
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username :" + username + " not found"));

        Collection<GrantedAuthority> grantedAuthorities = convertToSpringAuthorities(user.getAuthorities());

        org.springframework.security.core.userdetails.User springUserDetails =
                new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.isEnabled()
                                                                    , user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                        grantedAuthorities);
        return springUserDetails;
    }

    private Collection<GrantedAuthority> convertToSpringAuthorities(Collection<Authority> authorities) {
        if (authorities == null) {
            return new HashSet<>();
        }
        if (authorities.size() <= 0) {
            return new HashSet<>();
        }
        return authorities.stream()
                .map(Authority::getRole) // .map(authority -> authority.getRole())
                .map(SimpleGrantedAuthority::new) // .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
    }
}
