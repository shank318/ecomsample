package co.turing.security;

import co.turing.error.ApiError;
import co.turing.error.TuringErrors;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException | IllegalArgumentException e) {
            ApiError response = new ApiError(HttpStatus.UNAUTHORIZED);
            response.setMessage( e.getMessage());
            response.setCode(TuringErrors.AUTH_FAILED.getCode());
            response.setField("No Auth");
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            OutputStream out = res.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, response);
            out.flush();
        }

        filterChain.doFilter(req, res);
    }

}
