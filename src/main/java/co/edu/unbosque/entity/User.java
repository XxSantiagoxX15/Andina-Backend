package co.edu.unbosque.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="usuario")
public class User {
    @Id
    private int identificacion;

    @Column
    private String primer_nombre;

    @Column
    private String segundo_nombre;

    @Column
    private String primer_apellido;

    @Column
    private String segundo_apellido;

    @Column
    private String correo;

    @Column
    private String contrasena;

    @Column
    private String direccion;

    @Column
    private int telefono;

    @Column
    private int numero_licencia;


    @Column
    private int rol_id;




}




