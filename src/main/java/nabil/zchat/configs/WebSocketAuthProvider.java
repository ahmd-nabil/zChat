package nabil.zchat.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

/**
 * @author Ahmed Nabil
 */
@RequiredArgsConstructor
public class WebSocketAuthProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails == null) throw new UsernameNotFoundException("User Not Found!");
        if(!userDetails.getPassword().equals(password)) throw new BadCredentialsException("Wrong Password!");
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority("USER")));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(user);
        SecurityContextHolder.setContext(context);
        return user;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
