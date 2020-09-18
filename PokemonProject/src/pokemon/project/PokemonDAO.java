package pokemon.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO {
	public List<Pokemon> findAll() {
		List<Pokemon> list = new ArrayList<Pokemon>();
		Connection c = null;
		String sql = "SELECT * FROM pokemon ORDER BY id";
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	protected Pokemon processRow(ResultSet rs) throws SQLException {
		Pokemon pokemon = new Pokemon();
		pokemon.setId(rs.getInt("id"));
		pokemon.setName(rs.getString("name"));
		pokemon.setGeneration(rs.getString("generation"));
		pokemon.setType(rs.getString("type"));
		pokemon.setWeakness(rs.getString("weakness"));
		pokemon.setCategory(rs.getString("category"));
		pokemon.setDescription(rs.getString("description"));
		pokemon.setRegion(rs.getString("region"));
		pokemon.setGender(rs.getString("gender"));
		pokemon.setHeight(rs.getString("height"));
		pokemon.setWeight(rs.getString("weight"));
		pokemon.setAbility(rs.getString("ability"));
		pokemon.setPicture(rs.getString("picture"));
		return pokemon;
	}

	public Pokemon findById(int id) {
		String sql = "SELECT * FROM pokemon WHERE id = ?";
		Pokemon pokemon = null;
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				pokemon = processRow(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return pokemon;
	}

	public List<Pokemon> findByName(String name) {
		List<Pokemon> list = new ArrayList<Pokemon>();
		Connection c = null;
		String sql = "SELECT * FROM pokemon " + "WHERE UPPER(name) LIKE ? " + "ORDER BY name";
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "%" + name.toUpperCase() + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public List<Pokemon> findByRegion(String region) {
		List<Pokemon> list = new ArrayList<Pokemon>();
		Connection c = null;
		String sql = "SELECT * FROM pokemon AS e " + "WHERE UPPER(region) LIKE ? "
				+ "ORDER BY name";
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "%" + region.toUpperCase() + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public Pokemon create(Pokemon pokemon) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = ConnectionHelper.getConnection();
			ps = c.prepareStatement("INSERT INTO pokemon" + " (name, generation, type, weakness, category, description, region, gender, height, weight, ability, picture)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[] { "ID" });
			ps.setString(1, pokemon.getName());
			ps.setString(2, pokemon.getGeneration());
			ps.setString(3, pokemon.getType());
			ps.setString(4, pokemon.getWeakness());
			ps.setString(5, pokemon.getCategory());
			ps.setString(6, pokemon.getDescription());
			ps.setString(7, pokemon.getRegion());
			ps.setString(8, pokemon.getGender());
			ps.setString(9, pokemon.getHeight());
			ps.setString(10, pokemon.getWeight());
			ps.setString(11, pokemon.getAbility());
			ps.setString(12, pokemon.getPicture());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			// Update the id in the returned object. This is important as this value must be
			// returned
			int id = rs.getInt(1);
			pokemon.setId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return pokemon;
	}

	public Pokemon update(Pokemon pokemon) {
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement("UPDATE pokemon SET name = ?, generation = ?, type = ?, "
					+ "weakness = ?, category = ?, description = ?, region = ?, gender = ?, height = ?, weight = ?, ability = ?, picture = ? WHERE id = ?");
			ps.setString(1, pokemon.getName());
			ps.setString(2, pokemon.getGeneration());
			ps.setString(3, pokemon.getType());
			ps.setString(4, pokemon.getWeakness());
			ps.setString(5, pokemon.getCategory());
			ps.setString(6, pokemon.getDescription());
			ps.setString(7, pokemon.getRegion());
			ps.setString(8, pokemon.getGender());
			ps.setString(9, pokemon.getHeight());
			ps.setString(10, pokemon.getWeight());
			ps.setString(11, pokemon.getAbility());
			ps.setString(12,pokemon.getPicture());
			ps.setInt(13, pokemon.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return pokemon;
	}

	public boolean remove(int id) {
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement("DELETE FROM pokemon WHERE id=?");
			ps.setInt(1, id);
			int count = ps.executeUpdate();
			return count == 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
	}
}
