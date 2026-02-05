package GIOVANNILONGO.BE_GARA.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "piloti",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_gara", "numeroGara"}))
@Getter
@Setter

public class Pilota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroGara;
    private String nomePilota;
    private String nomeCopilota;
    private String team;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_gara")
    private Gara gara;
}
