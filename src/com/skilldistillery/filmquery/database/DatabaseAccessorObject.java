package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	@Override
	public Film findFilmById(int requestedFilmId) {

		List<Actor> actors = findActorsByFilmId(requestedFilmId);

		List<Film> films = new ArrayList<>();
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT f.id, f.title, f.description, f.release_year, f.language_id, f.rental_duration, f.rental_rate, f.length, f.replacement_cost, f.rating, f.special_features, l.name FROM film f JOIN language l ON f.language_id = l.id WHERE f.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, requestedFilmId);
//			System.out.println(stmt);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int filmId = rs.getInt("f.id");
				String title = rs.getString("f.title");
				String desc = rs.getString("f.description");
				int releaseYear = rs.getShort("f.release_year");
				int langId = rs.getInt("f.language_id");
				String language = rs.getString("l.name");
				int rentDur = rs.getInt("f.rental_duration");
				double rate = rs.getDouble("f.rental_rate");
				int length = rs.getInt("f.length");
				double repCost = rs.getDouble("f.replacement_cost");
				String rating = rs.getString("f.rating");
				String features = rs.getString("f.special_features");

				Film film = new Film(filmId, title, desc, releaseYear, langId, language, rentDur, rate, length, repCost,
						rating, features, actors);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films.get(0);
	}

	@Override
	public Actor findActorById(int requestedActorId) {
		List<Actor> actors = new ArrayList<>();

		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * " + " FROM actor WHERE actor_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, requestedActorId);
//			System.out.println(stmt);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int actorId = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors.get(0);
	}

	@Override
	public List<Actor> findActorsByFilmId(int requestedFilmId) {

		List<Actor> actors = new ArrayList<>();
		String user = "student";
		String pass = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, first_name, last_name "
					+ "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id WHERE film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, requestedFilmId);
//		System.out.println(stmt);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int actorId = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {

		List<Film> films = new ArrayList<>();
		String user = "student";
		String pass = "student";

		keyword = "%" + keyword + "%";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT f.id, f.title, f.description, f.release_year, f.language_id, f.rental_duration, f.rental_rate, f.length, f.replacement_cost, f.rating, f.special_features, l.name"
					+ " FROM film f JOIN language l ON f.language_id = l.id WHERE f.title LIKE ? OR description LIKE ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, keyword);
			stmt.setString(2, keyword);
//		System.out.println(stmt);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int filmId = rs.getInt("f.id");
				String title = rs.getString("f.title");
				String desc = rs.getString("f.description");
				int releaseYear = rs.getShort("f.release_year");
				int langId = rs.getInt("f.language_id");
				String language = rs.getString("l.name");
				int rentDur = rs.getInt("f.rental_duration");
				double rate = rs.getDouble("f.rental_rate");
				int length = rs.getInt("f.length");
				double repCost = rs.getDouble("f.replacement_cost");
				String rating = rs.getString("f.rating");
				String features = rs.getString("f.special_features");

//			String langSql = "SELECT name FROM language Where id = ?";
//			
//			PreparedStatement langStmt = conn.prepareStatement(langSql);
//			stmt.setInt(1, langId);
//			
//			ResultSet langRs = langStmt.executeQuery();
//			
//			while (langRs.next()) {
//				language = langRs.getString("name");
//			}

				List<Actor> actors = findActorsByFilmId(filmId);
				Film film = new Film(filmId, title, desc, releaseYear, langId, language, rentDur, rate, length, repCost,
						rating, features, actors);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films;
	}

}
