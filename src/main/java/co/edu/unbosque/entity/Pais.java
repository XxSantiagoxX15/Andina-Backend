package co.edu.unbosque.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="pais")
public class Pais {
 @Id
 private int id;

 private String nombre;

}
