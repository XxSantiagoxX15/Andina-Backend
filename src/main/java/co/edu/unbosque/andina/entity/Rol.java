package co.edu.unbosque.andina.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Rol")
public class Rol {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;


    private String descripcion;

}
