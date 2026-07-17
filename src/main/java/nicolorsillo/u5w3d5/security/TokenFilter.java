package nicolorsillo.u5w3d5.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.exceptions.UnauthorizedException;
import nicolorsillo.u5w3d5.services.UtentiService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UtentiService usersService;

    public TokenFilter(JWTTools jwtTools, UtentiService usersService) {

        this.jwtTools = jwtTools;
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Verifichiamo se la richiesta contiene un authorization header e se nel caso ci fosse, che il formato sia quello atteso
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'authorization header nel formato Bearer ");

        // 2. Estraiamo il token dall'header
        String accessToken = authHeader.replace("Bearer ", "");
        System.out.println(accessToken);

        // 3. Verifichiamo che il token sia OK (che non sia malformato, che non sia scaduto e che la firma sia ok e quindi non manipolato
        this.jwtTools.verifyToken(accessToken);

        // 1. Cerchiamo l'utente nel DB
        UUID utenteID = this.jwtTools.extractIdFromToken(accessToken);
        Utente authenticatedUser = this.usersService.findById(utenteID);

        // 2. Associamo l'utente trovato al Security Context
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Se tutto è  OK --> Andiamo avanti con la catena
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
