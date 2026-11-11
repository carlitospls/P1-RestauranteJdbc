package es.daw.restaurantejdbc.repository;

import es.daw.restaurantejdbc.exception.AccesoDatosException;
import es.daw.restaurantejdbc.model.Categoria;
import es.daw.restaurantejdbc.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Capa de datos: la única que escribe SQL.
@Repository
@RequiredArgsConstructor
public class PlatoRepositoryJdbc implements PlatoRepository {

    // SELECT común a findById y findAll (JOIN para traer también el nombre de la categoria)
    private static final String SELECT_BASE = """
            SELECT pl.codigo, pl.nombre, pl.precio, pl.calorias,
                   c.codigo AS c_codigo, c.nombre AS c_nombre
            FROM plato pl
            JOIN categoria c ON pl.codigo_categoria = c.codigo
            """;

    private final DataSource dataSource;

    @Override
    public Optional<Plato> findById(Integer codigo) {
        String sql = SELECT_BASE + "WHERE pl.codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al buscar el plato " + codigo, e);
        }
    }

    @Override
    public List<Plato> findAll() {
        String sql = SELECT_BASE + "ORDER BY pl.codigo";
        List<Plato> platos = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                platos.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar los platos", e);
        }
        return platos;
    }

    @Override
    public void save(Plato plato) {
        String sql = "INSERT INTO plato (nombre, precio, calorias, codigo_categoria) VALUES (?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, plato.getNombre());
            ps.setDouble(2, plato.getPrecio());
            ps.setInt(3, plato.getCalorias());
            ps.setInt(4, plato.getCategoria().getCodigo());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    plato.setCodigo(claves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al guardar el plato", e);
        }
    }

    @Override
    public void update(Plato plato) {
        String sql = "UPDATE plato SET nombre = ?, precio = ?, calorias = ?, codigo_categoria = ? WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, plato.getNombre());
            ps.setDouble(2, plato.getPrecio());
            ps.setInt(3, plato.getCalorias());
            ps.setInt(4, plato.getCategoria().getCodigo());
            ps.setInt(5, plato.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al actualizar el plato " + plato.getCodigo(), e);
        }
    }

    @Override
    public void delete(Integer codigo) {
        String sql = "DELETE FROM plato WHERE codigo = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al borrar el plato " + codigo, e);
        }
    }

    @Override
    public boolean existsByCategoriaCodigo(Integer codigoCategoria) {
        String sql = "SELECT 1 FROM plato WHERE codigo_categoria = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "Error al comprobar los platos de la categoria " + codigoCategoria, e);
        }
    }

    // Fila del ResultSet → objeto Plato
    private Plato mapRow(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria(
                rs.getInt("c_codigo"),
                rs.getString("c_nombre"));

        return new Plato(
                rs.getInt("codigo"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("calorias"),
                categoria);
    }
}
