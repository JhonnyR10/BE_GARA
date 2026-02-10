package GIOVANNILONGO.BE_GARA.entities;

import GIOVANNILONGO.BE_GARA.enums.StatoGiornata;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "giorno_gara",
        uniqueConstraints = @UniqueConstraint(columnNames = {"gara_id", "data"}))
@Getter
@Setter
@NoArgsConstructor
public class GiornoGara {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_gara")
    private Gara gara;

    private LocalDate data;
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoGiornata stato;

}
