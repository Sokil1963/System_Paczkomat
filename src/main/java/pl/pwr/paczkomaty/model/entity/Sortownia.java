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
@Table(name = "sortownia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sortownia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nazwa sortowni jest wymagana")
    @Pattern(regexp = "^[^-].*", message = "Nazwa nie może zaczynać się od minusa")
    @Column(name = "nazwa", nullable = false, length = 100)
    private String nazwa;

    @NotBlank(message = "Adres jest wymagany")
    @Column(name = "adres", nullable = false, columnDefinition = "TEXT")
    private String adres;


    @OneToMany(mappedBy = "sortownia", cascade = CascadeType.ALL)
    private List<Paczkomat> paczkomaty;

    @OneToMany(mappedBy = "sortownia", cascade = CascadeType.ALL)
    private List<ZadanieDystrybucyjne> zadania;
}

