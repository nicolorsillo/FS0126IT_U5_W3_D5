package nicolorsillo.u5w3d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolorsillo.u5w3d5.entities.Evento;
import nicolorsillo.u5w3d5.entities.Prenotazione;
import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.exceptions.BadRequestException;
import nicolorsillo.u5w3d5.exceptions.NotFoundException;
import nicolorsillo.u5w3d5.payloads.PrenotazioniDTO;
import nicolorsillo.u5w3d5.repositories.PrenotazioniRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PrenotazioniService {
    private final PrenotazioniRepository prenotazioniRepository;
    private final UtentiService utentiService;
    private final EventiService eventiService;

    public PrenotazioniService(PrenotazioniRepository prenotazioniRepository, UtentiService utentiService, EventiService eventiService) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.utentiService = utentiService;
        this.eventiService = eventiService;
    }

    public Prenotazione save(PrenotazioniDTO payload) {
        Evento evento = this.eventiService.findById(payload.evento());
        Utente utente = this.utentiService.findById(payload.utente());

        long prenotazioniAttuali = this.prenotazioniRepository.countByEvento(evento);

        if (prenotazioniAttuali >= evento.getPostiDisponibili()) {
            throw new BadRequestException("I posti per l'evento " + evento.getTitolo() + " sono esauriti");
        }

        Prenotazione newPrenotazione = new Prenotazione(evento, utente);

        Prenotazione prenotazioneSalvata = this.prenotazioniRepository.save(newPrenotazione);

        log.info("Prenotazione " + prenotazioneSalvata.getId() + " salvata");

        return prenotazioneSalvata;
    }

    public Page<Prenotazione> getAll(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.prenotazioniRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID id) {
        return this.prenotazioniRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


}
