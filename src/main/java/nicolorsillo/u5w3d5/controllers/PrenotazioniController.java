package nicolorsillo.u5w3d5.controllers;

import nicolorsillo.u5w3d5.entities.Prenotazione;
import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.payloads.PrenotazioniDTO;
import nicolorsillo.u5w3d5.services.PrenotazioniService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    private final PrenotazioniService prenotazioniService;

    public PrenotazioniController(PrenotazioniService prenotazioniService) {
        this.prenotazioniService = prenotazioniService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Page<Prenotazione> getAllPrenotazioni(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.prenotazioniService.getAll(page, size, sortBy);
    }

    @GetMapping("/{idPrenotazione}")
    public Prenotazione getPrenotazioneById(@PathVariable UUID idPrenotazione) {
        return this.prenotazioniService.findById(idPrenotazione);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione createPrenotazione(
            @RequestBody @Validated PrenotazioniDTO body,
            @AuthenticationPrincipal Utente currentUser
    ) {
        PrenotazioniDTO payloadCompleto = new PrenotazioniDTO(body.evento(), body.utente());

        return this.prenotazioniService.save(payloadCompleto);
    }
}