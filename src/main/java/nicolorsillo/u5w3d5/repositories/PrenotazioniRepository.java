package nicolorsillo.u5w3d5.repositories;

import nicolorsillo.u5w3d5.entities.Prenotazione;
import nicolorsillo.u5w3d5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findByUtente(Utente utente);
}
