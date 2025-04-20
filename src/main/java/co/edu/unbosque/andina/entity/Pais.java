package co.edu.unbosque.andina.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="pais")
public class Pais {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;


 private String nombre;

}
