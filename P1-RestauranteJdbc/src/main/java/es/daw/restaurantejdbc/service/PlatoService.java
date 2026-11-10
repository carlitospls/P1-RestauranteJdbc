package es.daw.restaurantejdbc.service;

import es.daw.restaurantejdbc.exception.RecursoNoEncontradoException;
import es.daw.restaurantejdbc.model.Categoria;
import es.daw.restaurantejdbc.model.Plato;
import es.daw.restaurantejdbc.repository.CategoriaRepository;
import es.daw.restaurantejdbc.repository.PlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Capa de lógica: reglas de negocio. No sabe nada de HTTP ni de SQL.
@Service
@RequiredArgsConstructor
public class PlatoService {

    private final PlatoRepository platoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<Plato> listar() {
        return platoRepository.findAll();
    }

    public Plato buscarPorCodigo(Integer codigo) {
        return platoRepository.findById(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el plato con código " + codigo));
    }

    public Plato crear(Plato plato) {
        prepararParaGuardar(plato);
        platoRepository.save(plato);
        return plato;
    }

    public Plato actualizar(Plato plato) {
        buscarPorCodigo(plato.getCodigo());
        prepararParaGuardar(plato);
        platoRepository.update(plato);
        return plato;
    }

    public void borrar(Integer codigo) {
        buscarPorCodigo(codigo);
        platoRepository.delete(codigo);
    }

    // Reglas comunes a crear y actualizar
    private void prepararParaGuardar(Plato plato) {
        plato.setNombre(plato.getNombre().trim());

        Integer codigoCategoria = plato.getCategoria().getCodigo();
        Categoria categoria = categoriaRepository.findById(codigoCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe la categoria con código " + codigoCategoria));
        plato.setCategoria(categoria);
    }
}
