package nicolorsillo.u5w3d5.controllers;

import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.services.UtentiService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UtentiController {

    private final UtentiService utentiService;

    public UtentiController(UtentiService utentiService) {
        this.utentiService = utentiService;
    }

    @GetMapping
    public Page<Utente> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idUser") String sortBy
    ) {
        return utentiService.getAll(page, size, sortBy);
    }

    @GetMapping("/{idUtente}")
    public Utente getUserById(@PathVariable UUID idUtente) {
        return utentiService.findById(idUtente);
    }
}