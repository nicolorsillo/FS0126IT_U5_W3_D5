package nicolorsillo.u5w3d5.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@NoArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties({"password", "credentialsNonExpired", "enabled", "accountNonExpired", "authorities", "accountNonLocked"})
public class Utente implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RuoloUtente ruolo;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public Utente(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.ruolo = RuoloUtente.UTENTE;
    }

    public Utente(String username, String email, String password, RuoloUtente ruolo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
