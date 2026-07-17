package nicolorsillo.u5w3d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolorsillo.u5w3d5.repositories.EventiRepository;
import nicolorsillo.u5w3d5.repositories.UtentiRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventiService {
    private EventiRepository eventiRepository;
    private UtentiRepository utentiRepository;

    public EventiService(EventiRepository eventiRepository, UtentiRepository utentiRepository) {
        this.eventiRepository = eventiRepository;
        this.utentiRepository = utentiRepository;
    }
}
