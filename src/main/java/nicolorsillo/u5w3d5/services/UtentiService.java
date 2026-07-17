package nicolorsillo.u5w3d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolorsillo.u5w3d5.repositories.UtentiRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtentiService {
    private final UtentiRepository utentiRepository;
    private final PasswordEncoder bcrypt;

    public UtentiService(UtentiRepository utentiRepository, PasswordEncoder bcrypt) {
        this.utentiRepository = utentiRepository;
        this.bcrypt = bcrypt;
    }
}
