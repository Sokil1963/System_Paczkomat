package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
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

    @Column(name = "nazwa", nullable = false, length = 100)
    private String nazwa;

    @Column(name = "adres", nullable = false, columnDefinition = "TEXT")
    private String adres;

    @OneToMany(mappedBy = "sortownia", cascade = CascadeType.ALL)
    private List<Paczkomat> paczkomaty;

    @OneToMany(mappedBy = "sortownia", cascade = CascadeType.ALL)
    private List<ZadanieDystrybucyjne> zadania;
}

