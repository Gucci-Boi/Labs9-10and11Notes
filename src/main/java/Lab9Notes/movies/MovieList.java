package Lab9Notes.movies;

import java.util.List;

import static input.InputUtils.*;
public class MovieList {
    // global string variable that holds the database path
    private static final String DB_PATH = "jdbc:sqlite:movie_watchlist.sqlite";
    // Database variable
    private static Database database;

    public static void main(String[] args) {
        // makes a database using the database path
        database = new Database(DB_PATH);

        // calls all the methods
        addNewMovies();
        checkIfWatchedAndRate();
        deleteWatchedMovies();
        searchMovie();
        displayAllMovies();
    }
    // method that gets the rating of the movie
    public static int getRating() {
        // asks the user for a rating for the movie and stores it in an integer variable
        int rating = positiveIntInput("Out of 5 stars, how would you rate this movie? ");
        while (rating < 0 || rating > 5) { // loops until a valid number is entered
            // prints a message if invalid data is entered
            System.out.println("Invalid input.");
            // asks the user again
            rating = positiveIntInput("Out of 5 stars, how would you rate this movie? ");
        }
        // returns the rating
        return rating;
    }
    // method for input validation of the movie name
    public static String getNotEmptyMovieName() {
        // asks the user to enter a name for the movie and stores it in a string variable
        String movieName = stringInput("Enter the movie name: ");
        while (movieName.isEmpty()) { // loops until a string is entered
            // prints a message if invalid data is entered
            System.out.println("Invalid input.");
            // asks the user again
            movieName = stringInput("Enter the movie name: ");
        }
        // returns the movie name
        return movieName;
    }
    // method that adds new movies to the table
    public static void addNewMovies() {
        do {
            // calls the movie name validation method
            String movieName = getNotEmptyMovieName();
            // boolean variable that asks the user if they've seen the movie yet
            boolean movieWatched = yesNoInput("Have you seen this movie yet? ");
            // integer variable that will hold the movie's rating
            int movieStars = 0;
            // checks if the user already watched the movie
            if (movieWatched) {
                // calls the method that asks the user for their rating of the movie
                movieStars = getRating();
            }
            // makes a new Movie object with the entered data
            Movie movie = new Movie(movieName, movieStars, movieWatched);
            // adds the new movie to the table
            database.addNewMovie(movie);
            // repeats until the user doesn't want to add a new movie to the table
        } while (yesNoInput("Add a movie to the watchlist? "));
    }
    // method that all the movies from the table
    public static void displayAllMovies() {
        // stores all the movies in a list
        List<Movie> movies = database.getAllMovies();
        // checks if the list is empty
        if (movies.isEmpty()) {
            // if it is empty, a message is printed
            System.out.println("No movies.");
        } else {
            // loops the number of movies that are in the list
            for (Movie movie: movies) {
                // prints each movie from the list
                System.out.println(movie);
            }
        }
    }
    // method that
    public static void checkIfWatchedAndRate() {
        // stores all the unwatched movies from the table in a list
        List<Movie> unwatchedMovies = database.getAllWatchedMovies(false);
        // loops the number of movies in that list
        for (Movie movie: unwatchedMovies) {
            // boolean variable that asks the user if they've seen the movie
            boolean hasWatched = yesNoInput("Have you watched " + movie.getName() + " yet?");
            // checks if the user did watch the current movie
            if (hasWatched) {
                // integer variable that holds the user's rating of the movie
                int stars = positiveIntInput("What is your rating for " + movie.getName() + " out of 5 stars? ");
                // changes the movie's previous info to the new info
                movie.setWatched(true);
                movie.setStars(stars);
                // updates the table
                database.updateMovie(movie);
            }
        }
    }
    // method that deletes watched movies from the table
    public static void deleteWatchedMovies() {
        // prints all the watched movies from the table
        System.out.println("Here are all the movies you've watched: ");
        List<Movie> watchedMovies = database.getAllWatchedMovies(true);
        // loops the number of movies that are in the watchedMovie list
        for (Movie movie: watchedMovies) {
            // boolean variable that stores if the user wants to delete the current movie
            boolean delete = yesNoInput("Delete " + movie.getName() + "?");
            if (delete) {
                // deletes the movie from the table if the user entered yes
                database.deleteMovie(movie);
            }
        }
    }
    // method that searches the table for a specified word
    public static void searchMovie() {
        // string variable that asks the user for a word to search for
        String movieName = stringInput("Enter word to search for: ");
        // puts all the matches into a list
        List<Movie> movieMatches = database.search(movieName);
        // checks if the list is empty
        if (movieMatches.isEmpty()) {
            // prints a message
            System.out.println("No matches.");
        } else {
            // loops through the list
            for (Movie movie: movieMatches) {
                // prints each match in the list
                System.out.println(movie);
            }
        }
    }
}
