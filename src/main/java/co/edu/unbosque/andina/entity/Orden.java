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
@Table(name="orden")
public class Orden {
    @Id
    private int  id;

    private String tipo_orden;
private String simbolo;
    private  Double precio;


    private Date fecha_hora;

    private  String comision;




}
