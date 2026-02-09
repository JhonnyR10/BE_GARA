package GIOVANNILONGO.BE_GARA.entities;

import GIOVANNILONGO.BE_GARA.enums.StatoGara;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "gare")
@Getter
@Setter
@NoArgsConstructor
public class Gara {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    @Enumerated(EnumType.STRING)
    private StatoGara stato;

    private Integer numeroStazioni;
}
