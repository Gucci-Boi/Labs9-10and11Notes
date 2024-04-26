package Lab9Notes.movies;

import java.util.List;

import static input.InputUtils.*;
public class MovieList {
    //
    private static final String DB_PATH = "jdbc:sqlite:movie_watchlist.sqlite";
    //
    private static Database database;

    public static void main(String[] args) {
        //
        database = new Database(DB_PATH);
        // calls the method that adds new movies
        addNewMovies();
        //checkIfWatchedAndRate()
        //deleteWatchedMovies();
        searchMovie();
        //displayAllMovies();
    }

    public static int getRating() {
        int rating = positiveIntInput();
        while (rating < 0 || rating > 5) {
            System.out.println("Invalid input.");
            rating = positiveIntInput("Out of 5 stars, how would you rate this movie? ");
        }
        return rating;
    }
    //
    public static String getNotEmptyMovieName() {
        String movieName = stringInput("Enter the movie name: ");
        while (movieName.isEmpty()) {
            System.out.println("Invalid input.");
            movieName = stringInput("Enter the movie name: ");
        }
        return movieName;
    }
    public static void addNewMovies() {
        do {
            //
            getNotEmptyMovieName();
            boolean movieWatched = yesNoInput("Have you seen this movie yet? ");
            int movieStars = 0;
            //
            if (movieWatched) {
                movieStars = getRating();
            }
            //
            Movie movie = new Movie(movieName, movieStars, movieWatched);
            //
            database.addNewMovie(movie);
        } while (yesNoInput("Add a movie to the watchlist? "));
    }
    //
    public static void displayAllMovies() {
        List<Movie> movies = database.getAllMovies();
        // checks if the list is empty
        if (movies.isEmpty()) {
            System.out.println("No movies.");
        } else {
            //
            for (Movie movie: movies) {
                System.out.println(movie);
            }
        }
    }
    //
    public static void checkIfWatchedAndRate() {
        List<Movie> unwatchedMovies = database.getAllWatchedMovies(false);
        for (Movie movie: unwatchedMovies) {
            boolean hasWatched = yesNoInput("Have you watched " + movie.getName() + " yet?");
            if (hasWatched) {
                int stars = positiveIntInput("What is your rating for " + movie.getName() + " out of 5 stars? ");
                movie.setWatched(true);
                movie.setStars(stars);
                database.updateMovie(movie);
            }
        }
    }
    public static void deleteWatchedMovies() {
        System.out.println("Here are all the movies you've watched: ");
        List<Movie> watchedMovies = database.getAllWatchedMovies(true);

        for (Movie movie: watchedMovies) {
            boolean delete = yesNoInput("Delete " + movie.getName() + "?");
            if (delete) {
                database.deleteMovie(movie);
            }
        }
    }

    public static void searchMovie() {
        String movieName = stringInput("Enter word to search for: ");
        List<Movie> movieMatches = database.search(movieName);

        if (movieMatches.isEmpty()) {
            System.out.println("No matches.");
        } else {
            for (Movie movie: movieMatches) {
                System.out.println(movie);
            }
        }
    }
}
