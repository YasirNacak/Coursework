package Q2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Mutex implementation of the barrier mechanism for AddAndDFT operation.
 * @author Yasir
 */
public class AddAndDFTMutex extends AddAndDFT {
    /**
     * Mutex counter variable
     */
    private static volatile int _counter = 0;

    /**
     * Control variable that protects the counter
     */
    private static ReentrantLock _mutex = new ReentrantLock();

    /**
     * Semaphore for the thread that is utilizing the barrier
     */
    private static Semaphore _arrival = new Semaphore(1);

    /**
     * Semaphore for making all threads wait until the operation ends
     */
    private static Semaphore _departure = new Semaphore(0);

    /**
     * Constructor that creates 2 matrices with given dimension sizes, randomizes them and
     * initializes the result of the operation as an empty matrix
     * @param threadCount Number of threads that will be involved in the calculation
     * @param matrixSize Size of the each dimension of each matrix
     */
    public AddAndDFTMutex(int threadCount, int matrixSize) {
        super(threadCount, matrixSize);
    }

    /**
     * Barrier mechanism that will be used the by the worker threads.
     * Utilizes a Mutex and two Semaphores to achieve parallelism.
     */
    @Override
    protected void Barrier() {
        try {
            _arrival.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        _mutex.lock();
        _counter++;
        _mutex.unlock();
        if (_counter < _threadCount) {
            _arrival.release();
        } else {
            _departure.release();
        }

        try {
            _departure.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        _mutex.lock();
        _counter--;
        _mutex.unlock();
        if (_counter > 0) {
            _departure.release();
        } else {
            _arrival.release();
        }
    }
}
