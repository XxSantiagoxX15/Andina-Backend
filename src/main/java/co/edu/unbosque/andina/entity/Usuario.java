package co.edu.unbosque.andina.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

  @Id
  @Column(name = "identificacion")
  private Integer identificacion;

  @Column(name = "primer_nombre")
  private String primerNombre;

  @Column(name = "segundo_nombre")
  private String segundoNombre;

  @Column(name = "primer_apellido")
  private String primerApellido;

  @Column(name = "segundo_apellido")
  private String segundoApellido;

  @Column(name = "correo")
  private String correo;

  @Column(name = "contrasena")
  private String contrasena;

  @Column(name = "direccion")
  private String direccion;

  @Column(name = "telefono")
  private String telefono;
  @Column(name= "saldo")
  private Double saldo;

  @Column(name = "numero_licencia")
  private String numeroLicencia;

  @Column(name = "rol_id")
  private int rol;


  @Column(name = "ciudad_id")
  private int ciudad;
  @JsonIgnore
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @JsonIgnore
  @Column(name = "update_at")
  private LocalDateTime updateAt;
@JsonIgnore
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;
}
