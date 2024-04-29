package Lab9Notes.movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Database {

    private String databasePath;
    // Database constructor
    Database(String databasePath) {
        this.databasePath = databasePath;
        try  // try block with a Connection and Statement in the argument
        (Connection connection = DriverManager.getConnection(databasePath);
        Statement statement = connection.createStatement()
        ) {

            statement.execute("CREATE TABLE IF NOT EXISTS " +         // creates a table
                    "movies (id INTEGER PRIMARY KEY, " +                  // gives the id an incrementing primary key
                    "name text UNIQUE CHECK(length(name) >= 1), " +       // makes sure the name is at least a letter long
                    "stars INTEGER CHECK(stars >= 0 AND stars <= 5), " +  // makes sure the stars integer is between 0 and 5
                    "watched BOOLEAN)");                                  // the watched variable is a boolean in the table

        } catch (SQLException sqle) {
            // prints an error message if there's an exception
            System.out.println("Error creating movie DB table: " + sqle);
        }
    }
    // method that adds movies to the list
    public void addNewMovie(Movie movie) {
        // string variable that holds the SQL
        String insertSQL = "INSERT INTO movies (name, stars, watched) VALUES (?, ?, ?)";
        // try block with Connection and a PreparedStatement
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)
        ) {
            // adds movie info to the table
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setInt(2, movie.getStars());
            preparedStatement.setBoolean(3, movie.isWatched());

            preparedStatement.execute();
        } catch (SQLException sqle) {
            // prints error message
            System.out.println("Error adding " + movie + ". Problem: " + sqle);
        }
    }
    // method that puts all the movies in a list
    public List<Movie> getAllMovies() {
        try // try block with Connection and Statement
        (Connection connection = DriverManager.getConnection(databasePath);
        Statement statement = connection.createStatement()
        ) {
            ResultSet movieResult = statement.executeQuery(
                    // gets movies from the list by the name of those movies
                    "SELECT * FROM movies ORDER BY name");
            // list to hold the movies
            List<Movie> movies = new ArrayList<>();
            // loops until there is no more movies
            while (movieResult.next()) {
                // gets the movie's info
                int id = movieResult.getInt("id");
                String name = movieResult.getString("name");
                int stars = movieResult.getInt("stars");
                boolean watched = movieResult.getBoolean("watched");
                // makes a Movie object with the movie info
                Movie movie = new Movie(id, name, stars, watched);
                // adds the movie to the list
                movies.add(movie);
            }
            // returns the list of movies
            return movies;
        } catch (SQLException sqle) {
            // prints an error message
            System.out.println("Error getting all movies. Problem: " + sqle);
            // returns null if there was an exception
            return null;
        }
    }
    // method that gets all the watched movies
    public List<Movie> getAllWatchedMovies(boolean watchedStatus) {
        // try block with Connection and PreparedStatement
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(
                // gets movies by the boolean watched
                "SELECT * FROM movies WHERE watched = ?")
        ) {
            // replaces the ? with the watchedStatus boolean variable
            preparedStatement.setBoolean(1, watchedStatus);
            ResultSet movieResult = preparedStatement.executeQuery();
            // list to hold the movies
            List<Movie> movies = new ArrayList<>();
            // loops until there is no more movies
            while (movieResult.next()) {
                // gets the movie's info
                int id = movieResult.getInt("id");
                String name = movieResult.getString("name");
                int stars = movieResult.getInt("stars");
                boolean watched = movieResult.getBoolean("watched");
                // makes a Movie object with the movie info
                Movie movie = new Movie(id, name, stars, watched);
                // adds the movie to the list
                movies.add(movie);
            }
            // returns the list of movies
            return movies;
        } catch(SQLException se) {
            System.out.println("Error getting movies: " + se);
            return null;
        }
    }
    // method that updates the watched status and rating of movies
    public void updateMovie(Movie movie) {
        // gets the movies info
        String sql = "UPDATE movies SET stars = ?, watched = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // replaces the ?'s with the movie info
            preparedStatement.setInt(1, movie.getStars());
            preparedStatement.setBoolean(2, movie.isWatched());
            preparedStatement.setInt(3, movie.getId());

            preparedStatement.execute();
        } catch (SQLException sqe) {
            // prints error message
            System.out.println("Error updating movie: " + sqe);
        }
    }
    // method that deletes movies from the table
    public void deleteMovie(Movie movie) {
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(
                // deletes movies by unique id
                "DELETE FROM movies WHERE id = ?")
        ) {
            // replaces the ? with the movie's unique id
            preparedStatement.setInt(1, movie.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            // prints error message
            System.out.println("Error deleting movie: " + e);
        }
    }
    // method that searches through the list of Movie objects
    public List<Movie> search(String searchTerm) {
        // gets movie from the movies list and turns the movie's name to uppercase
        String query = "SELECT * FROM movies WHERE UPPER(name) LIKE UPPER(?)";
        try (Connection connection = DriverManager.getConnection(databasePath);
        PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            // replaces the ? with the searchTerm
            preparedStatement.setString(1, "%" + searchTerm + "%");
            ResultSet movieResult = preparedStatement.executeQuery();
            // list to hold the movies
            List<Movie> movies = new ArrayList<>();
            // loops until there is no more movies
            while (movieResult.next()) {
                // gets the movie's info
                int id = movieResult.getInt("id");
                String name = movieResult.getString("name");
                int stars = movieResult.getInt("stars");
                boolean watched = movieResult.getBoolean("watched");
                // makes a Movie object with the movie info
                Movie movie = new Movie(id, name, stars, watched);
                // adds the movie to the list
                movies.add(movie);
            }
            // returns the list of movies
            return movies;
        } catch (SQLException sqle) {
            // prints error message
            System.out.println("Error finding for " + searchTerm + ". Problem: " + sqle);
            return null; // returns null
        }
    }
}
