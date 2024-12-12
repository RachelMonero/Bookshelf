package com.bookshelf.observers;

public class LibrarianNotificationObserver implements ReservationObserver {
    @Override
    public void onReservationCanceled(String reservationId, String userId, String libraryBookId) {
        System.out.println("Librarian notified: Reservation " + reservationId + " for book " + libraryBookId + " was canceled by user " + userId);
       
    }
}
