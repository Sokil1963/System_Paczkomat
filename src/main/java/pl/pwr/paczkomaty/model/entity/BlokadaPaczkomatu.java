package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "blokada_paczkomatu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlokadaPaczkomatu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paczkomat_id", nullable = false)
    private Paczkomat paczkomat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "awaria_id")
    private AwariaPaczkomatu awaria;

    @Column(name = "data_blokady", nullable = false)
    private LocalDateTime dataBlokady = LocalDateTime.now();

    @Column(name = "data_odblokowania")
    private LocalDateTime dataOdblokowania;

    @Column(name = "powod", columnDefinition = "TEXT")
    private String powod;
}

