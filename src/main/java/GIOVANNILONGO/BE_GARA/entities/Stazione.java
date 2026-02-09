package GIOVANNILONGO.BE_GARA.entities;

import GIOVANNILONGO.BE_GARA.enums.TipoStazione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stazioni", uniqueConstraints = @UniqueConstraint(columnNames = {"garaId", "ordine"}))
@Getter
@Setter
public class Stazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Gara gara;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoStazione tipo;

    private Integer ordine;

}
