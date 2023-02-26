package guru.sfg.brewery.security;

import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
