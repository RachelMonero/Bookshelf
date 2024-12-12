package com.bookshelf.observers;

import java.util.ArrayList;
import java.util.List;

/**
 * Notifier for library book-related events.
 */
public class LibraryBookNotifier {
    private final List<ReservationObserver> observers;

    public LibraryBookNotifier() {
        this.observers = new ArrayList<>();
    }

    // Add an observer
    public void addObserver(ReservationObserver observer) {
        observers.add(observer);
    }

    // Remove an observer
    public void removeObserver(ReservationObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers of a reservation cancellation
    public void notifyObservers(String reservationId, String userId, String libraryBookId) {
        for (ReservationObserver observer : observers) {
            observer.onReservationCanceled(reservationId, userId, libraryBookId);
        }
    }
}
