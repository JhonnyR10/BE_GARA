package GIOVANNILONGO.BE_GARA.entities;

import GIOVANNILONGO.BE_GARA.enums.Input;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "time_records",
        uniqueConstraints = @UniqueConstraint(columnNames = {
                "id_giornoGara", "id_pilota", "id_stazione"
        })
)
@Getter
@Setter
@NoArgsConstructor
public class TimeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private GiornoGara giornoGara;

    @ManyToOne(optional = false)
    private Pilota pilota;

    @ManyToOne(optional = false)
    private Stazione stazione;

    private LocalDateTime timestampRilevato;

    @Enumerated(EnumType.STRING)
    private Input tipoInserimento;

    private LocalDateTime dataCreazione;
}
