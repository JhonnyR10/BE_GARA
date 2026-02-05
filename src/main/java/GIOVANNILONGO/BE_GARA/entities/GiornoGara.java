package GIOVANNILONGO.BE_GARA.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "giorno_gara")
@Getter
@Setter
public class GiornoGara {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_gara")
    private Gara gara;

    private LocalDate data;
}
