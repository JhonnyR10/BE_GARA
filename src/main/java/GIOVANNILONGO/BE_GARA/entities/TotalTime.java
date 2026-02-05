package GIOVANNILONGO.BE_GARA.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "total_time",
        uniqueConstraints = @UniqueConstraint(columnNames = {
                "id_giornoGara", "id_pilota"
        })
)
@Getter
@Setter

public class TotalTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private GiornoGara giornoGara;

    @ManyToOne(optional = false)
    private Pilota pilota;

    private Long tempoStartIntermedio;
    private Long tempoIntermedioStop;
    private Long tempoTotale;

    private LocalDateTime ultimoAggiornamento;
}
