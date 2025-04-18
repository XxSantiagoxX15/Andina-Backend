package co.edu.unbosque.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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


    private int sector_economico_id ;
}
