package nicolorsillo.u5w3d5.controllers;

import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.exceptions.ValidationException;
import nicolorsillo.u5w3d5.payloads.*;
import nicolorsillo.u5w3d5.services.AuthService;
import nicolorsillo.u5w3d5.services.UtentiService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtentiService utentiService;

    public AuthController(AuthService authService, UtentiService utentiService) {
        this.authService = authService;
        this.utentiService = utentiService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public UtentiResponseDTO saveUser(@RequestBody @Validated UtentiDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errorsList);
        }
        Utente saved = this.utentiService.saveUtente(body);
        return new UtentiResponseDTO(saved.getId());
    }

    @PostMapping("/register-organizzatore")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Utente registerOrganizzatore(@RequestBody @Validated OrganizzatoriDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errorsList);
        }
        return this.utentiService.saveOrganizzatore(body);
    }
}