package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "status_przesylki")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusPrzesylki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "kod", nullable = false, unique = true, length = 30)
    private String kod;

    @Column(name = "opis", columnDefinition = "TEXT")
    private String opis;

    @OneToMany(mappedBy = "aktualnyStatus", cascade = CascadeType.ALL)
    private List<Przesylka> przesylki;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<HistoriaStatusu> historie;
}

