package nicolorsillo.u5w3d5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UtentiDTO(
        @NotBlank(message = "Devi inserire uno username, non può essere una stringa vuota")
        @Size(min = 2, max = 40, message = "Lo username deve avere un numero di caratteri compreso tra 2 e 40")
        String username,
        @NotBlank(message = "L'email è obbligatoria, non può neanche essere una stringa vuota")
        @Email(message = "L'email deve essere nel formato corretto")
        String email,
        @NotBlank(message = "La password è obbligatoria, non può neanche essere una stringa vuota")
        @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "La password deve contenere almeno 1 maiuscola e 1 minuscola")
        String password
) {
}
