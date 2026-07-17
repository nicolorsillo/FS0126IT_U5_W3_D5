package nicolorsillo.u5w3d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolorsillo.u5w3d5.repositories.EventiRepository;
import nicolorsillo.u5w3d5.repositories.PrenotazioniRepository;
import nicolorsillo.u5w3d5.repositories.UtentiRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrenotazioniService {
    private PrenotazioniRepository prenotazioniRepository;
    private UtentiRepository utentiRepository;
    private EventiRepository eventiRepository;

    public PrenotazioniService(PrenotazioniRepository prenotazioniRepository, UtentiRepository utentiRepository, EventiRepository eventiRepository) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.utentiRepository = utentiRepository;
        this.eventiRepository = eventiRepository;
    }
}
