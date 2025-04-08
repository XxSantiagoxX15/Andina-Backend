package co.edu.unbosque.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="ciudad")
public class Ciudad {
    @Id
    private int id ;

    private String nombre;


    private int pais_id;


}
