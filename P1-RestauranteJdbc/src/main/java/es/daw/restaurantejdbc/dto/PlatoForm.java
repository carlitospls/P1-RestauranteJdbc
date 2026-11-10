package es.daw.restaurantejdbc.dto;

import es.daw.restaurantejdbc.model.Categoria;
import es.daw.restaurantejdbc.model.Plato;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto del formulario de plato (viaja entre la vista y el controlador).
 * El <select> solo envía el código de la categoria, por eso aquí es un Integer.
 */
@Data
@NoArgsConstructor
public class PlatoForm {

    private Integer codigo;            // null = alta, con valor = edición

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "Máximo 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @NotNull(message = "Selecciona una categoría")
    private Integer codigoCategoria;

    @NotNull(message = "Las calorías son obligatorias")
    @NegativeOrZero(message = "Las calorías no pueden ser negativas")
    private Integer calorias;

    /** Plato (modelo) → formulario. Se usa al editar. */
    public static PlatoForm toDto(Plato p) {
        PlatoForm form = new PlatoForm();
        form.setCodigo(p.getCodigo());
        form.setNombre(p.getNombre());
        form.setPrecio(p.getPrecio());
        form.setCalorias(p.getCalorias());
        form.setCodigoCategoria(p.getCategoria().getCodigo());
        return form;
    }

    /** Formulario → Plato (modelo). De la categoria solo tenemos el código. */
    public Plato toModelEntity() {
        Categoria categoria = new Categoria(codigoCategoria, null);
        return new Plato(codigo, nombre, precio, calorias, categoria);
    }
}
