package nicolorsillo.u5w3d5.controllers;

import nicolorsillo.u5w3d5.entities.Evento;
import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.payloads.EventiDTO;
import nicolorsillo.u5w3d5.services.EventiService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/eventi")
public class EventiController {

    private final EventiService eventiService;

    public EventiController(EventiService eventiService) {
        this.eventiService = eventiService;
    }

    @GetMapping
    public Page<Evento> getAllEventi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idEvento") String sortBy
    ) {
        return eventiService.getAll(page, size, sortBy);
    }

    @GetMapping("/{idEvento}")
    public Evento getEventoById(@PathVariable UUID idEvento) {
        return eventiService.findById(idEvento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento createEvento(
            @RequestBody @Validated EventiDTO body,
            @AuthenticationPrincipal Utente currentUser
    ) {
        return eventiService.save(body, currentUser.getId());
    }

    @PutMapping("/{idEvento}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento updateEvento(
            @PathVariable UUID idEvento,
            @RequestBody @Validated EventiDTO body,
            @AuthenticationPrincipal Utente currentUser
    ) {
        return eventiService.update(idEvento, body, currentUser.getId());
    }

    @DeleteMapping("/{idEvento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public void deleteEvento(@PathVariable UUID idEvento, @AuthenticationPrincipal Utente currentUser) {
        eventiService.delete(idEvento, currentUser.getId());
    }
}