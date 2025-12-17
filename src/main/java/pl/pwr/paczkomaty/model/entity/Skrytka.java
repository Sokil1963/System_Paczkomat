package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skrytka", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"paczkomat_id", "numer"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skrytka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paczkomat_id", nullable = false)
    private Paczkomat paczkomat;

    @Column(name = "numer", nullable = false, length = 10)
    private String numer;

    @Column(name = "rozmiar", nullable = false, length = 10)
    private String rozmiar;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "WOLNA";
}

