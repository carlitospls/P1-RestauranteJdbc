package es.daw.restaurantejdbc.service;

import es.daw.restaurantejdbc.model.Categoria;
import es.daw.restaurantejdbc.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }
}
