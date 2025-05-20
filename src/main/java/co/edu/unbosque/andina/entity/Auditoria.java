package co.edu.unbosque.andina.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String accion;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private LocalDateTime fechaHora;


    private String  usuario;

    // Getters, setters, constructores
}
