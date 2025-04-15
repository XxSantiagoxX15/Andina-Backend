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
@Table(name="contrato")
public class Contrato {
     @Id
    private int numero_contrato;

     private Date fecha_hora_inicio;

     private Date fecha_hora_fin;

     private Double comision;




}
