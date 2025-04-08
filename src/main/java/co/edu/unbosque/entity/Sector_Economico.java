package co.edu.unbosque.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="sector_econimico")
public class Sector_Economico {


    @Id
    private int id;


    private String nombre;


    private String descripcion;


}
