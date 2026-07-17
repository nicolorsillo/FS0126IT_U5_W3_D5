package nicolorsillo.u5w3d5.services;

import lombok.extern.slf4j.Slf4j;
import nicolorsillo.u5w3d5.entities.RuoloUtente;
import nicolorsillo.u5w3d5.entities.Utente;
import nicolorsillo.u5w3d5.exceptions.BadRequestException;
import nicolorsillo.u5w3d5.exceptions.NotFoundException;
import nicolorsillo.u5w3d5.payloads.OrganizzatoriDTO;
import nicolorsillo.u5w3d5.payloads.UtentiDTO;
import nicolorsillo.u5w3d5.repositories.UtentiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UtentiService {
    private final UtentiRepository utentiRepository;
    private final PasswordEncoder bcrypt;

    public UtentiService(UtentiRepository utentiRepository, PasswordEncoder bcrypt) {
        this.utentiRepository = utentiRepository;
        this.bcrypt = bcrypt;
    }

    public Utente saveUtente(UtentiDTO payload) {
        if (this.utentiRepository.existsByEmail(payload.email()))
            throw new BadRequestException("L'indirizzo email " + payload.email() + " è già utilizzato!");

        if (this.utentiRepository.existsByUsername(payload.username()))
            throw new BadRequestException("L'username " + payload.username() + " é già utilizzato!");

        Utente newUtente = new Utente(payload.username(), payload.email(), this.bcrypt.encode(payload.password()));

        Utente utente = this.utentiRepository.save(newUtente);

        log.info("Utente " + utente.getId() + " è stato salvato");

        return utente;
    }

    public Utente saveOrganizzatore(OrganizzatoriDTO payload) {
        if (this.utentiRepository.existsByEmail(payload.email()))
            throw new BadRequestException("L'indirizzo email " + payload.email() + " è già utilizzato!");

        if (this.utentiRepository.existsByUsername(payload.username()))
            throw new BadRequestException("L'username " + payload.username() + " é già utilizzato!");

        Utente newUtente = new Utente(payload.username(), payload.email(), this.bcrypt.encode(payload.password()), RuoloUtente.ORGANIZZATORE);

        Utente utente = this.utentiRepository.save(newUtente);

        log.info("Organizzatore " + utente.getId() + " è stato salvato");

        return utente;
    }

    public Page<Utente> getAll(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.utentiRepository.findAll(pageable);
    }

    public Page<Utente> getAllUtenti(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.utentiRepository.findAllByRuolo(RuoloUtente.UTENTE, pageable);
    }

    public Page<Utente> getAllOrganizzatori(int page, int size, String orderBy) {
        if (size > 50) size = 50;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.utentiRepository.findAllByRuolo(RuoloUtente.ORGANIZZATORE, pageable);
    }

    public Utente findById(UUID id) {
        return this.utentiRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Utente findByUsername(String username) {
        return this.utentiRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(username));
    }

    public Utente findByEmail(String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

}
