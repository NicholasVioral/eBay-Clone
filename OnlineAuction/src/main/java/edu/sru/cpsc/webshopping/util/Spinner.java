package edu.sru.cpsc.webshopping.util;

public class Spinner implements AutoCloseable {
    private static final char[] SPINNER_CHARS = {'|', '/', '-', '\\'};
    private boolean running = true;
    private int currentIndex = 0;

    public Spinner() {
        Thread spinnerThread = new Thread(() -> {
            while (running) {
                currentIndex = (currentIndex + 1) % SPINNER_CHARS.length;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // handle exception
                }
            }
        });
        spinnerThread.start();
    }

    public char getCurrentSpinnerChar() {
        return SPINNER_CHARS[currentIndex];
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void close() {
        this.running = false;
        System.out.print("\rDone!     \n"); // overwrite the spinner with "Done!"
    }
}
