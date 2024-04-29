package Lab9Notes.movies;

public class Movie {
    // variables for the constructor
    private int id;
    private String name;
    private int stars;
    private boolean watched;
    // constructor for movie objects
    Movie(String name, int stars, boolean watched) {
        this.name = name;
        this.stars = stars;
        this.watched = watched;
    }
    // constructor with the id
    Movie(int id, String name, int stars, boolean watched) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.watched = watched;
    }
    @Override // replaces the default toString method with this custom one
    public String toString() {
        // custom string
        return "ID: " + id + ", Movie name: " + name + ". It was rated " + stars + " stars. "
                + "You watched this movie- " + watched;
    }
    // getters and setters for the Movie constructors
    public String getName() { return name; }
    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }
    public boolean isWatched() { return watched; }
    public void setWatched(boolean watched) { this.watched = watched; }
    public int getId() { return id; }

}
