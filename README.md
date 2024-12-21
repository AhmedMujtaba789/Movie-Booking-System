# Movie Booking System - JavaFX Application

## Overview
The Movie Booking System is a JavaFX-based application designed to simplify the process of booking movie tickets. Users can select movies, showtimes, and seats conveniently through an interactive graphical interface. This system ensures efficient data management and provides features like seat recommendations, viewing current bookings, and clearing all bookings.

---

## Features
- **User Authentication:** Secure login with username and password.
- **Movie Selection:** Choose between Bollywood and Hollywood movies.
- **Showtime and Seat Selection:** Interactive seat selection with real-time booking validation.
- **Booking Management:** View, manage, and clear bookings.
- **Seat Recommendations:** Suggests available seats to users.
- **Persistence:** Saves booking details in a text file (`bookings.txt`) for future reference.

---

## Installation and Usage

### Prerequisites
- Java Development Kit (JDK) 8 or later
- JavaFX SDK

### Steps to Run
1. Clone the repository or download the source code.
2. Compile and run the application using your preferred IDE or terminal.
   ```
   javac MovieBookingSystem.java
   java MovieBookingSystem
   ```
3. Login with the default credentials:
   - **Username:** Ahmed
   - **Password:** Iqra

4. Follow the prompts to select movies, showtimes, and seats.

---

## System Design

### Problem Definition
The Movie Ticket Booking System addresses the complexity and inconvenience of traditional methods of booking movie tickets by providing a digital solution. The system ensures:
- Simplified booking process.
- Efficient management of movie-related data.
- Seamless navigation and data integrity.

### Objectives
- Simplify movie ticket booking.
- Validate and confirm bookings efficiently.
- Provide easy navigation between different views.
- Offer a smooth user experience.

### Justification for Chosen Data Structures
- **HashMap:** Efficient storage and quick lookup for booked seats by movie and showtime.
- **ArrayList:** Easy iteration and updates for movies, showtimes, and seats.
- **Stack:** Manages scene transitions for navigation.
- **Doubly Linked List:** Efficient navigation between Bollywood and Hollywood movie categories.

---

## System Architecture

### Architecture Design
The application follows a layered architecture:
1. **User Interface (UI):** Built with JavaFX to provide an interactive platform.
2. **Application Logic:** Handles validation, data manipulation, and scene navigation.
3. **Data Management:** Uses HashMap for bookings and ArrayList for managing movies, showtimes, and seats.
4. **Data Persistence:** Saves booking data to a text file for later retrieval.

### Component Interaction
- **UI Layer:** Captures user inputs and displays outputs.
- **Logic Layer:** Processes inputs and communicates with the data layer.
- **Data Layer:** Stores and retrieves booking and movie-related data.

---

## Anticipated Challenges
1. Avoiding seat conflicts during simultaneous bookings.
2. Ensuring smooth scene transitions.
3. Maintaining data integrity when handling multiple bookings.
4. Providing clear feedback for invalid operations.

---

## Testing

### Testing Strategy
- **Unit Testing:** Ensures correctness of individual methods.
- **Integration Testing:** Verifies interaction between layers.
- **System Testing:** Confirms overall application functionality.
- **User Acceptance Testing (UAT):** Validates user satisfaction.

### Test Cases
- **Login Functionality:** Valid credentials should navigate to the movie selection page.
- **Movie Selection:** Displays selected movie in the booking interface.
- **Seat Booking:** Confirms booking and updates `bookings.txt`.
- **Display Bookings:** Shows current bookings.
- **Clear Bookings:** Empties booking data and clears the display.

### Performance Testing
- **Load Testing:** Handled up to 50 simultaneous users.
- **Response Time:**
  - Booking confirmation: <2 seconds.
  - Data display: <1 second.

---

## Reflection Report

### Challenges Faced
- **UI Glitches:** Resolved misalignment issues through JavaFX layout adjustments.
- **Error Handling:** Enhanced error messages for invalid bookings and inputs.
- **Data Synchronization:** Fixed delays in booking updates by refining the logic.

### Design Choices
- Modular architecture separating UI, logic, and data layers.
- JavaFX for a user-friendly GUI.
- Efficient data structures for robust data management.

### Learning Outcomes
- Improved understanding of modular design and data structures.
- Enhanced problem-solving and debugging skills.
- Gained hands-on experience with JavaFX development.

---

## File Details
- **Main Class:** `MovieBookingSystem.java`
- **Abstract Class:** `Booking` (Handles generic booking functionality).
- **Interface:** `Displayable` (For displaying booking details).
- **Concrete Class:** `MovieBooking` (Implements booking logic).
- **Text File:** `bookings.txt` (Stores booking records).

---

## Future Enhancements
- Add user registration and role-based access (admin vs user).
- Integrate payment gateways for ticket purchases.
- Extend to support online booking via web or mobile platforms.
- Implement advanced seat recommendation algorithms.

---

## License
This project is licensed under the MIT License. Feel free to use and modify the code.

---

## Authors
Developed by:
- **Ahmed Mujtaba (63979)**
- **Abdul Majeed (62204)**

"# Movie-Booking-System" 
