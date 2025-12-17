package pl.pwr.paczkomaty.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paczkomat_id", nullable = false)
    private Paczkomat paczkomat;

    @NotNull
    @Column(name = "numer", nullable = false, length = 10)
    private String numer;

    @NotBlank
    @Column(name = "rozmiar", nullable = false, length = 10)
    private String rozmiar;


    @Column(name = "status", nullable = false, length = 20)
    private String status = "WOLNA";
}

