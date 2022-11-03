package com.eazybytes.config;

import com.eazybytes.repository.CustomerRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EazyBankWithUserDetailsServiceAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final UserDetailsService userDetailsService;

    public EazyBankWithUserDetailsServiceAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                                CustomerRepository customerRepository,
                                                                UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        String dbPwd = userDetails.getPassword();

        if (passwordEncoder.matches(((String) authentication.getCredentials()), dbPwd)) {

            return new UsernamePasswordAuthenticationToken(userDetails, authentication, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Pwd not match.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
