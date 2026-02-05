package GIOVANNILONGO.BE_GARA.entities;

import GIOVANNILONGO.BE_GARA.enums.TipoStazione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stazioni")
@Getter
@Setter
public class Stazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoStazione tipo;

    private Integer ordine;
}
