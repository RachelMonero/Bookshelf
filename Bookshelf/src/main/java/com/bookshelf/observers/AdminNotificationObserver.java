package com.bookshelf.observers;

public class AdminNotificationObserver implements ReservationObserver {
    @Override
    public void onReservationCanceled(String reservationId, String userId, String libraryBookId) {
        System.out.println("Admin notified: Reservation " + reservationId + " for book " + libraryBookId + " was canceled by user " + userId);
       
    }
}
