package co.edu.unbosque.andina.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "historial_orden")
public class Historial_Orden {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private Double precio;

  private String tipo_orden;

  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha_hora;

  private Double comision;

  @Column(name = "orden_id")
  private Integer ordenId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "update_at")
  private Date updateAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "deleted_at")
  private Date deletedAt;
}
