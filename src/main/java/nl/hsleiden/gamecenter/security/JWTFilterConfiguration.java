package nl.hsleiden.gamecenter.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.services.ImplementationUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@RequiredArgsConstructor
public class JWTFilterConfiguration {

    private final ImplementationUserDetailsService userDetailsService;
    private final JWTFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeHttpRequests()
                // ALL ALLOWED ENDPOINTS FOR ALL USERS (AUTHENTICATED AND UNAUTHENTICATED)
                .requestMatchers("/api/auth/**", "/api/account/register", "/api/product/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/product").permitAll()
                // USER AND ADMIN ROUTES
                .requestMatchers(
                        "/api/cart_product/transfer_cart",
                        "/api/cart_product/{id}/buy_product",
                        "/api/account/{id}",
                        "/api/account/{id}/change_password",
                        "/api/cart_product",
                        "/api/cart_product/account={id}",
                        "/api/cart_product/{id}/update_count",
                        "/api/cart_product/{id}",
                        "/api/transaction/account={accountId}",
                        "/api/transaction_product/transaction={transactionId}",
                        "/api/transaction_product").hasAnyRole("USER", "ADMINISTRATOR")
                .requestMatchers(HttpMethod.POST, "/api/transaction").hasAnyRole("USER", "ADMINISTRATOR")
                // ADMIN ROUTES
                .requestMatchers("/api/account", "/api/account/{id}/administrator", "/api/product/{id}/edit_stock").hasRole("ADMINISTRATOR")
                .requestMatchers(HttpMethod.POST, "/api/product").hasRole("ADMINISTRATOR")
                .requestMatchers(HttpMethod.GET, "/api/transaction").hasRole("ADMINISTRATOR")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                .and()
                .userDetailsService(userDetailsService)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
