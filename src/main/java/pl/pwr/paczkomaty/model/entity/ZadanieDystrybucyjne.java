package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "zadanie_dystrybucyjne")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZadanieDystrybucyjne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "typ", nullable = false, length = 20)
    private String typ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sortownia_id")
    private Sortownia sortownia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kurier_id")
    private Uzytkownik kurier;

    @Column(name = "data_startu")
    private LocalDateTime dataStartu;

    @Column(name = "data_konca")
    private LocalDateTime dataKonca;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ZAPLANOWANE";

    @ManyToMany(mappedBy = "zadania")
    private List<Przesylka> przesylki;
}

