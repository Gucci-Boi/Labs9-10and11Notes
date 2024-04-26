package Lab9Notes.movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Database {

    private String databasePath;
    // Database constructor
    Database(String databasePath) {
        //
        this.databasePath = databasePath;

        try  // try block
        //
        (Connection connection = DriverManager.getConnection(databasePath);
        //
        Statement statement = connection.createStatement()
        ) {

            // creates a table
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "movies (id INTEGER PRIMARY KEY, " +
                    "name text UNIQUE CHECK(length(name) >= 1), " +
                    "stars INTEGER CHECK(stars >= 0 AND stars <= 5), " +
                    "watched BOOLEAN)");

        } catch (SQLException sqle) {
            System.out.println("Error creating movie DB table: " + sqle);
        }
    }

    //
    public void addNewMovie(Movie movie) {
        //
        String insertSQL = "INSERT INTO movies (name, stars, watched) VALUES (?, ?, ?)";
        //
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)
        ) {
            //
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setInt(2, movie.getStars());
            preparedStatement.setBoolean(3, movie.isWatched());
            //
            preparedStatement.execute();
        } catch (SQLException sqle) {
            System.out.println("Error adding " + movie + ". Problem: " + sqle);
        }
    }
    //
    public List<Movie> getAllMovies() {
        try (Connection connection = DriverManager.getConnection(databasePath);
        Statement statement = connection.createStatement()
        ) {
            //
            ResultSet movieResult = statement.executeQuery("SELECT * FROM movies ORDER BY name");
            //
            List<Movie> movies = new ArrayList<>();

            //
            while (movieResult.next()) {
                int id = movieResult.getInt("id");
                String name = movieResult.getString("name");
                int stars = movieResult.getInt("stars");
                boolean watched = movieResult.getBoolean("watched");

                Movie movie = new Movie(id, name, stars, watched);
                movies.add(movie);
            }
            // returns the list of movies
            return movies;
        } catch (SQLException sqle) {
            System.out.println("Error getting all movies. Problem: " + sqle);
            return null;
        }
    }
    //
    public List<Movie> getAllWatchedMovies(boolean watchedStatus) {
        // try block
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM movies WHERE watched = ?")
        ) {
            preparedStatement.setBoolean(1, watchedStatus);
            ResultSet movieResult = preparedStatement.executeQuery();
            //
            List<Movie> movies = new ArrayList<>();
            //
            while (movieResult.next()) {
                int id = movieResult.getInt("id");
                String name = movieResult.getString("name");
                int stars = movieResult.getInt("stars");
                boolean watched = movieResult.getBoolean("watched");
                Movie movie = new Movie(id, name, stars, watched);
                movies.add(movie);
            }
            // returns the list of movies
            return  movies;
        } catch(SQLException se) {
            System.out.println("Error getting movies: " + se);
            return null;
        }
    }
    public void updateMovie(Movie movie) {
        String sql = "UPDATE movies SET stars = ?, watched = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, movie.getStars());
            preparedStatement.setBoolean(2, movie.isWatched());
            preparedStatement.setInt(3, movie.getId());

            preparedStatement.execute();
        } catch (SQLException sqe) {
            System.out.println("Error updating movie: " + sqe);
        }
    }

    public void deleteMovie(Movie movie) {
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM movies WHERE id = ?")
        ) {
           preparedStatement.setInt(1, movie.getId());
           preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error deleting movie: " + e);
        }
    }

    public List<Movie> search(String searchTerm) {
        String query = "SELECT * FROM movies WHERE UPPER(name) LIKE UPPER(?)";
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, "%" + searchTerm + "%");
            ResultSet movieResult = preparedStatement.executeQuery();

            List<Movie> movies = new ArrayList<>();

            while (movieResult.next()) {
                int id = movieResult.getInt("id");
                String name = movieResult.getString("name");
                int stars = movieResult.getInt("stars");
                boolean watched = movieResult.getBoolean("watched");
                Movie movie = new Movie(id, name, stars, watched);
                movies.add(movie);
            }
            return movies;
        } catch (SQLException sqle) {
            System.out.println("Error finding for " + searchTerm + ". Problem: " + sqle);
            return null;
        }
    }
}
