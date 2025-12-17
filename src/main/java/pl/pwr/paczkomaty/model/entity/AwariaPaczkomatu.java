package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "awaria_paczkomatu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AwariaPaczkomatu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paczkomat_id", nullable = false)
    private Paczkomat paczkomat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zglaszajacy_id")
    private Uzytkownik zglaszajacy;

    @Column(name = "opis", nullable = false, columnDefinition = "TEXT")
    private String opis;

    @Column(name = "data_zgloszenia", nullable = false)
    private LocalDateTime dataZgloszenia = LocalDateTime.now();

    @Column(name = "status_awarii", nullable = false, length = 20)
    private String statusAwarii = "NOWA";

    @Column(name = "wymaga_blokady", nullable = false)
    private Boolean wymagaBlokady = false;

    @OneToMany(mappedBy = "awaria", cascade = CascadeType.ALL)
    private List<BlokadaPaczkomatu> blokady;
}

