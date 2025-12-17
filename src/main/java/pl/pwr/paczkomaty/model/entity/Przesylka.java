package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "przesylka")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Przesylka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "Numer przesyłki musi być liczbą dodatnią")
    @Column(name = "numer", nullable = false, unique = true)
    private Long numer;

    @NotBlank(message = "Opis jest wymagany")
    @Column(name = "opis", columnDefinition = "TEXT")
    private String opis;

    @Column(name = "gabaryt", length = 10)
    private String gabaryt;

    @Positive(message = "Waga musi być większa od zera")
    @Digits(integer = 6, fraction = 2, message = "Waga musi mieć prawidłowy format")
    @Column(name = "waga", precision = 8, scale = 2)
    private BigDecimal waga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paczkomat_nadania_id")
    private Paczkomat paczkomatNadania;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paczkomat_docelowy_id")
    private Paczkomat paczkomatDocelowy;

    @Column(name = "kod_odbioru", nullable = false, length = 20)
    private String kodOdbioru;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aktualny_status_id")
    private StatusPrzesylki aktualnyStatus;

    @OneToMany(mappedBy = "przesylka", cascade = CascadeType.ALL)
    private List<HistoriaStatusu> historiaStatusu;

    @OneToMany(mappedBy = "przesylka", cascade = CascadeType.ALL)
    private List<ZgloszenieNieprawidlowejPrzesylki> zgloszenia;

    @ManyToMany
    @JoinTable(
        name = "przesylka_zadanie",
        joinColumns = @JoinColumn(name = "przesylka_id"),
        inverseJoinColumns = @JoinColumn(name = "zadanie_id")
    )
    private List<ZadanieDystrybucyjne> zadania;
}

