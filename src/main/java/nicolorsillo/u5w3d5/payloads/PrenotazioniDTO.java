package nicolorsillo.u5w3d5.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PrenotazioniDTO(
        @NotNull(message = "L'id dell'utente è obbligatorio")
        UUID utente,

        @NotNull(message = "L'id dell'evento è obbligatorio")
        UUID evento
) {
}
