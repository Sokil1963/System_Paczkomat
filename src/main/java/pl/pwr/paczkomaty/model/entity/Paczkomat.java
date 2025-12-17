package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "paczkomat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paczkomat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Kod paczkomatu jest wymagany")
    @Pattern(regexp = "^[^-].*", message = "Kod nie może zaczynać się od minusa")
    @Column(name = "kod", nullable = false, unique = true, length = 20)
    private String kod;

    @NotBlank(message = "Adres jest wymagany")
    @Column(name = "adres", nullable = false, columnDefinition = "TEXT")
    private String adres;


    @Column(name = "status", nullable = false, length = 20)
    private String status = "AKTYWNY";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sortownia_id")
    private Sortownia sortownia;

    @OneToMany(mappedBy = "paczkomat", cascade = CascadeType.ALL)
    private List<Skrytka> skrytki;

    @OneToMany(mappedBy = "paczkomat", cascade = CascadeType.ALL)
    private List<AwariaPaczkomatu> awarie;

    @OneToMany(mappedBy = "paczkomat", cascade = CascadeType.ALL)
    private List<BlokadaPaczkomatu> blokady;

    @OneToMany(mappedBy = "paczkomatNadania", cascade = CascadeType.ALL)
    private List<Przesylka> przesylkiNadania;

    @OneToMany(mappedBy = "paczkomatDocelowy", cascade = CascadeType.ALL)
    private List<Przesylka> przesylkiDocelowe;
}

