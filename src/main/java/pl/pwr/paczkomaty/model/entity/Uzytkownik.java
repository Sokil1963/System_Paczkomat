package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "uzytkownik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Uzytkownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Login nie może być pusty")
    @Pattern(regexp = "^[^-].*", message = "Login nie może zaczynać się od minusa")
    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    @NotBlank(message = "Hasło nie może być puste")
    @Column(name = "haslo_hash", nullable = false, columnDefinition = "TEXT")
    private String hasloHash;

    @NotBlank(message = "Rola musi być określona")
    @Column(name = "rola", nullable = false, length = 20)
    private String rola;

    @OneToMany(mappedBy = "kurier", cascade = CascadeType.ALL)
    private List<ZadanieDystrybucyjne> zadaniaKuriera;

    @OneToMany(mappedBy = "zglaszajacy", cascade = CascadeType.ALL)
    private List<AwariaPaczkomatu> awarieZgloszone;

    @OneToMany(mappedBy = "zglaszajacy", cascade = CascadeType.ALL)
    private List<ZgloszenieNieprawidlowejPrzesylki> zgloszeniaPrzesylek;

    @OneToMany(mappedBy = "uzytkownik", cascade = CascadeType.ALL)
    private List<HistoriaStatusu> historieStatusu;
}

