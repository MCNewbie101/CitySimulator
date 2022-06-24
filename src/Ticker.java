/*
 * Not yet implemented
 */
public class Ticker extends Thread {
    private boolean paused;

    @Override
    public void run() {
        while (!paused) {
            
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
