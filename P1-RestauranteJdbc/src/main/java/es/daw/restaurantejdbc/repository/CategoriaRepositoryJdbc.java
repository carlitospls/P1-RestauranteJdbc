package es.daw.restaurantejdbc.repository;

import es.daw.restaurantejdbc.exception.AccesoDatosException;
import es.daw.restaurantejdbc.model.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoriaRepositoryJdbc implements CategoriaRepository {

    private final DataSource dataSource;

    @Override
    public Optional<Categoria> findById(Integer codigo) {
        String sql = "SELECT codigo, nombre FROM categoria WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar la categoria " + codigo, e);
        }
    }

    @Override
    public List<Categoria> findAll() {
        String sql = "SELECT codigo, nombre FROM categoria ORDER BY nombre";
        List<Categoria> categorias = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categorias.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar las categorias", e);
        }
        return categorias;
    }

    @Override
    public void save(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre) VALUES (?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, categoria.getNombre());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    categoria.setCodigo(claves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al guardar la categoria", e);
        }
    }

    @Override
    public void update(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre = ? WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar la categoria " + categoria.getCodigo(), e);
        }
    }

    @Override
    public void delete(Integer codigo) {
        String sql = "DELETE FROM categoria WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al borrar la categoria " + codigo, e);
        }
    }

    private Categoria mapRow(ResultSet rs) throws SQLException {
        return new Categoria(rs.getInt("codigo"), rs.getString("nombre"));
    }
}
