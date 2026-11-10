package es.daw.restaurantejdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Fila de la tabla CATEGORIA. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    private Integer codigo;
    private String nombre;
}
