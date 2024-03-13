package ru.dmitriispiridonov.restfulservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;
import ru.dmitriispiridonov.restfulservice.dto.entity.AuditEntity;
import ru.dmitriispiridonov.restfulservice.repository.AuditRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuditFilter extends OncePerRequestFilter {

    private final AuditRepository auditRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        filterChain.doFilter(request, response);

        int status = response.getStatus();
        String username;
        String roles;

        if (status == HttpServletResponse.SC_UNAUTHORIZED || status == HttpServletResponse.SC_FORBIDDEN) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            username = (authentication != null) ? authentication.getName() : "ANONYMOUS";
            roles = (authentication != null)
                    ? authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(", "))
                    : "NONE";

        } else if ((status >= HttpServletResponse.SC_OK && status < HttpServletResponse.SC_MULTIPLE_CHOICES)
                || status == HttpServletResponse.SC_SWITCHING_PROTOCOLS) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            username = (authentication != null) ? authentication.getName() : "ANONYMOUS";
            roles = (authentication != null)
                    ? authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(", "))
                    : "NONE";

        } else {
            username = "anonymous";
            roles = "";
        }

        Map<String, String[]> params = new HashMap<>(request.getParameterMap());
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/ws") && params.containsKey("token")) {
            requestUri = UriComponentsBuilder.fromUriString(requestUri)
                    .replaceQueryParam("token", Collections.emptyList())
                    .toUriString();
            params.remove("token");
        }

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setName(username);
        auditEntity.setRoles(roles);
        auditEntity.setParams(Arrays.toString(params.keySet().toArray()));
        auditEntity.setRequestTime(new Date());
        auditEntity.setRequest(requestUri);
        auditEntity.setMethod(request.getMethod());
        auditEntity.setHttpStatus(status);
        auditRepository.save(auditEntity);
    }
}
