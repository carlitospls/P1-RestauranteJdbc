package es.daw.restaurantejdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Fila de la tabla PLATO.
 * En la tabla hay una columna codigo_categoria (FK); en Java guardamos el objeto Categoria completo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {

    private Integer codigo;
    private String nombre;
    private double precio;
    private Integer calorias;
    private Categoria categoria;


}
