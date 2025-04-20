package co.edu.unbosque.andina.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "accion")
public class Accion {
    @Id
    private Integer id;  //INTEGER CAMBIA A INT DE UNA VARIABLE PRIMITIVA A UN OBJETO CON METODOS QUE SE PUEDEN USAR
    private String nombre;
    private Double valor;
    private Date fecha_hora;


}
