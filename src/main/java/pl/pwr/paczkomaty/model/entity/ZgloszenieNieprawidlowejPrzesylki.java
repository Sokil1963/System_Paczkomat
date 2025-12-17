package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "zgloszenie_nieprawidlowej_przesylki")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZgloszenieNieprawidlowejPrzesylki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "przesylka_id", nullable = false)
    private Przesylka przesylka;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zglaszajacy_id")
    private Uzytkownik zglaszajacy;

    @Column(name = "typ", nullable = false, length = 30)
    private String typ;

    @Column(name = "opis", columnDefinition = "TEXT")
    private String opis;

    @Column(name = "data_zgloszenia", nullable = false)
    private LocalDateTime dataZgloszenia = LocalDateTime.now();
}

