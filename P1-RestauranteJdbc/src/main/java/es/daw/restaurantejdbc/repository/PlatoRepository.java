package es.daw.restaurantejdbc.repository;

import es.daw.restaurantejdbc.model.Plato;

/** Repositorio de platos: CRUD genérico + consultas propias. */
public interface PlatoRepository extends GenericRepository<Plato, Integer> {

    /** ¿Hay algún plato de esta categoria? */
    boolean existsByCategoriaCodigo(Integer codigoCategoria);
}
