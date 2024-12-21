package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Abstract class for booking functionality
abstract class Booking {
    String movie;
    String showtime;
    List<String> seats;

    public Booking(String movie, String showtime, List<String> seats) {
        this.movie = movie;
        this.showtime = showtime;
        this.seats = seats;
    }

    public abstract void confirmBooking() throws Exception;
}

// Interface for displaying booking details
interface Displayable {
    void displayDetails();
}

// Concrete booking implementation
class MovieBooking extends Booking implements Displayable {
    private Map<String, List<String>> bookings;

    public MovieBooking(String movie, String showtime, List<String> seats, Map<String, List<String>> bookings) {
        super(movie, showtime, seats);
        this.bookings = bookings;
    }

    @Override
    public void confirmBooking() throws Exception {
        String bookingKey = movie + " - " + showtime;
        List<String> bookedSeats = bookings.getOrDefault(bookingKey, new ArrayList<>());

        // Check for seat conflicts
        for (String seat : seats) {
            if (bookedSeats.contains(seat)) {
                throw new Exception("Seat " + seat + " is already booked for this showtime.");
            }
        }

        // Add seats to the booking record
        bookedSeats.addAll(seats);
        bookings.put(bookingKey, bookedSeats);

        writeBookingToFile();
    }

    @Override
    public void displayDetails() {
        System.out.println("Movie: " + movie);
        System.out.println("Showtime: " + showtime);
        System.out.println("Seats: " + seats);
    }

    // Write booking details to a file
    private void writeBookingToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("bookings.txt", true))) {
            writer.println("Movie: " + movie);
            writer.println("Showtime: " + showtime);
            writer.println("Seats: " + seats);
            writer.println("-------------------------");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}

public class MovieBookingSystem extends Application {
    private Stage primaryStage;
    private ComboBox<String> movieComboBox, showtimeComboBox;
    private ListView<String> seatList;
    private Button bookButton, displayButton, backButton, clearButton, recommendButton;
    private TextArea displayArea;

    // Use a thread-safe ConcurrentHashMap for real-time booking updates
    private Map<String, List<String>> bookings = new ConcurrentHashMap<>();

    private ArrayList<String> bollywoodMovies = new ArrayList<>(Arrays.asList("Bollywood Movie 1", "Bollywood Movie 2", "Bollywood Movie 3"));
    private ArrayList<String> hollywoodMovies = new ArrayList<>(Arrays.asList("Hollywood Movie 1", "Hollywood Movie 2", "Hollywood Movie 3"));
    private ArrayList<String> showtimes = new ArrayList<>(Arrays.asList("10:00 AM", "1:00 PM", "4:00 PM", "7:00 PM"));
    private ArrayList<String> seats = new ArrayList<>(Arrays.asList("A1", "A2", "A3", "A4", "A5", "B1", "B2", "B3", "B4", "B5"));

    // Stack for managing scene transitions
    private Stack<Scene> pageStack = new Stack<>();

    // Custom doubly linked list for category navigation
    class PageNode {
        String pageType;
        PageNode next;
        PageNode prev;

        public PageNode(String pageType) {
            this.pageType = pageType;
        }
    }

    private PageNode bollywoodPage = new PageNode("Bollywood");
    private PageNode hollywoodPage = new PageNode("Hollywood");

    {
        bollywoodPage.next = hollywoodPage;
        hollywoodPage.prev = bollywoodPage;
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Movie Booking System");

        // Login Scene
        GridPane loginPane = new GridPane();
        loginPane.setPadding(new Insets(10));
        loginPane.setVgap(10);
        loginPane.setHgap(10);

        TextField userField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginPane.add(new Label("Username:"), 0, 0);
        loginPane.add(userField, 1, 0);
        loginPane.add(new Label("Password:"), 0, 1);
        loginPane.add(passwordField, 1, 1);
        loginPane.add(loginButton, 1, 2);

        Scene loginScene = new Scene(loginPane, 300, 200);

        loginButton.setOnAction(e -> {
            String user = userField.getText();
            String password = passwordField.getText();

            if (user.equals("Ahmed") && password.equals("Iqra")) {
                showSelectionPage();
            } else {
                showAlert("Invalid credentials");
            }
        });

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showSelectionPage() {
        pageStack.push(primaryStage.getScene());

        VBox selectionBox = new VBox(10);
        selectionBox.setPadding(new Insets(10));

        Label questionLabel = new Label("What type of movie would you like to see?");
        Button bollywoodButton = new Button("Bollywood Movies");
        Button hollywoodButton = new Button("Hollywood Movies");

        bollywoodButton.setOnAction(e -> showBookingPage(bollywoodMovies));
        hollywoodButton.setOnAction(e -> showBookingPage(hollywoodMovies));

        selectionBox.getChildren().addAll(questionLabel, bollywoodButton, hollywoodButton);

        Scene selectionScene = new Scene(selectionBox, 300, 200);
        primaryStage.setScene(selectionScene);
    }

    private void showBookingPage(ArrayList<String> movies) {
        pageStack.push(primaryStage.getScene());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        movieComboBox = new ComboBox<>(FXCollections.observableArrayList(movies));
        showtimeComboBox = new ComboBox<>(FXCollections.observableArrayList(showtimes));

        seatList = new ListView<>();
        seatList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        seatList.setItems(FXCollections.observableArrayList(seats));

        bookButton = new Button("Book");
        displayButton = new Button("Display Bookings");
        backButton = new Button("Back");
        clearButton = new Button("Clear Bookings");
        recommendButton = new Button("Recommend Seats");

        displayArea = new TextArea();
        displayArea.setEditable(false);

        gridPane.add(new Label("Select Movie:"), 0, 0);
        gridPane.add(movieComboBox, 1, 0);
        gridPane.add(new Label("Select Showtime:"), 0, 1);
        gridPane.add(showtimeComboBox, 1, 1);
        gridPane.add(new Label("Select Seats:"), 0, 2);
        gridPane.add(seatList, 0, 3, 2, 1);
        HBox buttonBox = new HBox(10, bookButton, displayButton, backButton, clearButton, recommendButton);
        gridPane.add(buttonBox, 0, 4, 2, 1);
        gridPane.add(new VBox(displayArea), 0, 5, 2, 1);

        bookButton.setOnAction(e -> handleBooking());
        displayButton.setOnAction(e -> displayBookings());
        backButton.setOnAction(e -> {
            if (!pageStack.isEmpty()) {
                primaryStage.setScene(pageStack.pop());
            }
        });
        clearButton.setOnAction(e -> clearBookings());
        recommendButton.setOnAction(e -> recommendSeats());

        Scene bookingScene = new Scene(gridPane, 500, 400);
        primaryStage.setScene(bookingScene);
    }

    private void handleBooking() {
        try {
            String movie = movieComboBox.getValue();
            String showtime = showtimeComboBox.getValue();
            ObservableList<String> selectedSeats = seatList.getSelectionModel().getSelectedItems();

            if (selectedSeats.isEmpty()) {
                throw new Exception("Please select at least one seat.");
            }

            MovieBooking booking = new MovieBooking(movie, showtime, selectedSeats, bookings);
            booking.confirmBooking();
            showAlert("Booking successful!");

        } catch (Exception ex) {
            showAlert(ex.getMessage());
        }
    }

    private void displayBookings() {
        StringBuilder displayText = new StringBuilder();

        for (String bookingKey : bookings.keySet()) {
            displayText.append(bookingKey).append(": ").append(bookings.get(bookingKey).toString()).append("\n");
        }

        displayArea.setText(displayText.toString());
    }

    private void clearBookings() {
        bookings.clear();
        displayArea.clear();
        showAlert("Bookings cleared!");
    }

    private void recommendSeats() {
        // Simple seat recommendation based on availability
        for (String seat : seats) {
            if (bookings.values().stream().noneMatch(booking -> booking.contains(seat))) {
                showAlert("Recommended Seat: " + seat);
                return;
            }
        }
        showAlert("No seats available for recommendation!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
