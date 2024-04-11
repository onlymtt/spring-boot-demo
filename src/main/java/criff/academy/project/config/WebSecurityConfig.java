package criff.academy.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(new AntPathRequestMatcher("/", "GET"),
                        new AntPathRequestMatcher("/home", "GET"),
                        new AntPathRequestMatcher("/register", "GET"),
                        new AntPathRequestMatcher("/register", "POST"),
                        new AntPathRequestMatcher("/css/**", "GET"))
                .permitAll()
                .requestMatchers(new AntPathRequestMatcher("/messages", "GET"),
                        new AntPathRequestMatcher("/messages/send", "POST")) // Assicurati che questo sia configurato correttamente
                .hasAnyAuthority("ROLE_USER")
                .anyRequest().authenticated())
        .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/messages", true)
                .permitAll())
                .logout(logout -> logout
                .logoutUrl("/logout") // specifica l'URL per il logout
                .logoutSuccessUrl("/login?logout") // specifica l'URL a cui reindirizzare dopo il logout
                .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
