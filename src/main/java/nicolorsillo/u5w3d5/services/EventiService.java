package nicolorsillo.u5w3d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolorsillo.u5w3d5.entities.Evento;
import nicolorsillo.u5w3d5.entities.RuoloUtente;
import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.exceptions.BadRequestException;
import nicolorsillo.u5w3d5.exceptions.NotFoundException;
import nicolorsillo.u5w3d5.exceptions.UnauthorizedException;
import nicolorsillo.u5w3d5.payloads.EventiDTO;
import nicolorsillo.u5w3d5.repositories.EventiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class EventiService {
    private final EventiRepository eventiRepository;
    private final UtentiService utentiService;

    public EventiService(EventiRepository eventiRepository, UtentiService utentiService) {
        this.eventiRepository = eventiRepository;
        this.utentiService = utentiService;
    }

    public Evento save(EventiDTO payload, UUID organizzatoreId) {
        Utente organizzatore = this.utentiService.findById(organizzatoreId);
        if (!organizzatore.getRuolo().equals(RuoloUtente.ORGANIZZATORE))
            throw new BadRequestException("Solo gli organizzatori possono aggiungere un nuovo evento");

        Evento newEvento = new Evento(payload.titolo(), payload.descrizione(), payload.data(), payload.luogo(), payload.postiDisponibili(), organizzatore);

        Evento evento = eventiRepository.save(newEvento);

        log.info("Evento " + evento.getId() + " salvato");

        return evento;
    }

    public Page<Evento> getAll(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.eventiRepository.findAll(pageable);
    }

    public Evento findById(UUID id) {
        return this.eventiRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Evento update(UUID idEvento, EventiDTO payload, UUID organizzatoreId) {
        Utente organizzatore = this.utentiService.findById(organizzatoreId);
        Evento evento = this.findById(idEvento);

        if (!evento.getCreatoreEvento().getId().equals(organizzatore.getId()))
            throw new UnauthorizedException("Solo il creatore di questo evento può modificarlo");

        evento.setTitolo(payload.titolo());
        evento.setDescrizione(payload.descrizione());
        evento.setData(payload.data());
        evento.setLuogo(payload.luogo());
        evento.setPostiDisponibili(payload.postiDisponibili());

        return eventiRepository.save(evento);
    }

    public void delete(UUID idEvento, UUID organizzatoreId) {
        Utente organizzatore = this.utentiService.findById(organizzatoreId);
        Evento evento = this.findById(idEvento);

        if (!evento.getCreatoreEvento().getId().equals(organizzatore.getId()))
            throw new UnauthorizedException("Solo il creatore di questo evento può eliminarlo");

        eventiRepository.delete(evento);
    }
}
