package nicolorsillo.u5w3d5.repositories;

import nicolorsillo.u5w3d5.entities.RuoloUtente;
import nicolorsillo.u5w3d5.entities.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UtentiRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByUsername(String username);

    Optional<Utente> findByEmail(String email);

    Page<Utente> findAllByRuolo(RuoloUtente ruolo, Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
