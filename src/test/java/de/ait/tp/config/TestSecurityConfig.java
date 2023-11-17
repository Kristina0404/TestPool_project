package de.ait.tp.config;

import de.ait.tp.models.User;
import de.ait.tp.security.details.AuthenticatedUser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
@Profile("test")
public class TestSecurityConfig {

    public static final String MOCK_USER = "kristina.romanova@gmail.com";
    public static final String MOCK_ADMIN = "romanova@gmail.com";
    @Bean
    @Primary
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(){
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if(username.equals(MOCK_USER)){
                    return  new AuthenticatedUser(User.builder()
                            .id(1L)
                            .email(MOCK_USER)
                            .role(User.Role.USER)
                            .state(User.State.CONFIRMED)

                            .build());
                }else if(username.equals(MOCK_ADMIN)){
                    return  new AuthenticatedUser(User.builder()
                            .id(1L).email(MOCK_ADMIN).role(User.Role.ADMIN)
                            .state(User.State.CONFIRMED)
                            .build());
                }else throw new UsernameNotFoundException("User not found");
            }
        };
    }

}
