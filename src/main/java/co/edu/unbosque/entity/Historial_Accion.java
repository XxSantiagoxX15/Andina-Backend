package co.edu.unbosque.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@Table(name = "historial_acicon")
public class Historial_Accion {


    @Id
    private int id;

    private String nombre;

    private Double valor;

    private Date fecha_hora;


}