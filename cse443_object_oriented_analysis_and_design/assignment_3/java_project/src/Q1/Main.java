package Q1;

import java.util.ArrayList;

/**
 * Tester class that can test BestDSEver and BestDSEverThreadSafe
 * @author Yasir
 */
public class Main {
    public static void main(String[] args) {
        TestDS(true);
    }

    /**
     * Tests the BestDSEver or BestDSEverThreadSafe based on the given parameter
     * @param isThreadSafe tests thread-safe if this value is true, tests thread-unsafe otherwise
     */
    private static void TestDS(boolean isThreadSafe)
    {
        BestDSEver bestDSEver = new BestDSEver();
        BestDSEverThreadSafe bestDSEverThreadSafe = new BestDSEverThreadSafe();

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 500; j++) {
                    if(isThreadSafe) {
                        bestDSEverThreadSafe.Add("data");
                    }
                    else {
                        bestDSEver.Add("data");
                    }
                }
            }));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
