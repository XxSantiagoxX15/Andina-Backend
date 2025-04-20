package co.edu.unbosque.andina.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="empresa")
public class Empresa {

  @Id
  private int id;

  private String nombre;

  private String descripcion;

  @ManyToOne
  @JoinColumn(name = "sector_economico_id", referencedColumnName = "id")
  private SectorEconomico sectorEconomico;
}
