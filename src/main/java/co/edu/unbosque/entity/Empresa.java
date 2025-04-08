package co.edu.unbosque.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="empresa")
public class Empresa {

    @Id
    private int id;

    private String nombre;


    private String descripcion;


    private int sector_economico_id ;
}
