package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historia_statusu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaStatusu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "przesylka_id", nullable = false)
    private Przesylka przesylka;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusPrzesylki status;

    @Column(name = "czas_zmiany", nullable = false)
    private LocalDateTime czasZmiany = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    @Column(name = "komentarz", columnDefinition = "TEXT")
    private String komentarz;
}

