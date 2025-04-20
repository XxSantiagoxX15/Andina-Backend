package co.edu.unbosque.andina.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "contrato")
public class Contrato {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int numeroContrato;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_hora_inicio")
  private Date fechaHoraInicio;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_hora_fin")
  private Date fechaHoraFin;

  private Double comision;
}

