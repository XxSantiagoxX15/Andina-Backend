package co.edu.unbosque.andina.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ciudad")
public class Ciudad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String nombre;

  @ManyToOne
  @JoinColumn(name = "pais_id") // esta es la columna que hace la clave foránea en la tabla 'ciudad'
  private Pais pais;

}
