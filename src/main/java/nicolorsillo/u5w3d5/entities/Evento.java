package nicolorsillo.u5w3d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "eventi")
@Getter
@Setter
@NoArgsConstructor
@ToString

public class Evento {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String titolo;

    private String descrizione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String luogo;

    @Column(name = "posti_disponibili", nullable = false)
    private int postiDisponibili;

    @JoinColumn(name = "id_creatore_evento")
    @ManyToOne
    private Utente creatoreEvento;

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int postiDisponibili, Utente creatoreEvento) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.postiDisponibili = postiDisponibili;
        this.creatoreEvento = creatoreEvento;
    }
}
