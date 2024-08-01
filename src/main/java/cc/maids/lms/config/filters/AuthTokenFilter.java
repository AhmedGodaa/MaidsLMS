package cc.maids.lms.config.filters;

import cc.maids.lms.config.utils.JwtUtils;
import cc.maids.lms.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final HandlerExceptionResolver handlerExceptionResolver;


    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    public AuthTokenFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(authenticateRequest(request, response), response);
    }

    private HttpServletRequest authenticateRequest(HttpServletRequest request, HttpServletResponse response) {
        String jwt = jwtUtils.getJwtFromHeader(request);
        try {
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getEmailFromToken(jwt);
                UserDetails userDetails = loadByEmail(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
        return request;
    }


    public UserDetails loadByEmail(String email) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email))
                .getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        String password = UUID.randomUUID().toString();
        return new org.springframework.security.core.userdetails.User(email, password, authorities);
    }


}