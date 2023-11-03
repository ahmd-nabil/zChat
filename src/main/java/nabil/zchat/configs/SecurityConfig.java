package nabil.zchat.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Ahmed Nabil
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        configurer -> configurer
                                .requestMatchers("/ws", "/getAuth/**", "/error").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService inMemoryUsers() {
        UserDetails user1 = User.builder().username("ahmed").password("{noop}password").build();
        UserDetails user2 = User.builder().username("nabil").password("{noop}password").build();
        UserDetails user3 = User.builder().username("ali").password("{noop}password").build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(new WebSocketAuthProvider(inMemoryUsers()));
    }
}
