package nicolorsillo.u5w3d5.services;

import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.exceptions.UnauthorizedException;
import nicolorsillo.u5w3d5.payloads.LoginDTO;
import nicolorsillo.u5w3d5.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtentiService utentiService;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UtentiService utentiService, JWTTools jwtTools, PasswordEncoder bcrypt) {
        this.utentiService = utentiService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {

        // 1. Controllo credenziali
        // 1.1 Controllo se esiste un utente con quella email
        Utente found = this.utentiService.findByEmail(body.email());

        // 1.2 Controllo se le password corrispondono
        if (this.bcrypt.matches(body.password(), found.getPassword())) {
            // 2. Se tutto è OK --> Generiamo un Access Token per l'utente e lo ritorniamo
            return this.jwtTools.generateToken(found);
        } else {
            // 3. Altrimenti --> 401 ("Credenziali Sbagliate")
            throw new UnauthorizedException("Credenziali Sbagliate");
        }
    }
}