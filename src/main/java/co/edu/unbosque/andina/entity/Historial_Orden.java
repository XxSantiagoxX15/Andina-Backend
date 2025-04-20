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
@Table(name = "historial_orden")
public class Historial_Orden {

    @Id
    private int id;

    private  Double precio;

    private String tipo_orden;

    private Date fecha_hora;

    private Double comision;

}
