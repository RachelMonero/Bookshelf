package com.bookshelf.observers;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject class that notifies observers about reservation changes.
 */
public class ReservationNotifier {
    private final List<ReservationObserver> observers = new ArrayList<>();

    // Add an observer
    public void addObserver(ReservationObserver observer) {
        observers.add(observer);
    }

    // Remove an observer
    public void removeObserver(ReservationObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers about a canceled reservation
    public void notifyObservers(String reservationId, String userId, String libraryBookId) {
        for (ReservationObserver observer : observers) {
            observer.onReservationCanceled(reservationId, userId, libraryBookId);
        }
    }
}
