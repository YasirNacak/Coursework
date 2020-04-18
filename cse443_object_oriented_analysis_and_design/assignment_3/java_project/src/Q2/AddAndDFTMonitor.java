package Q2;

/**
 * Java Monitors implementation of the barrier for AddAndDFT operation.
 * Uses Wait and NotifyAll mechanisms provided by Java.
 * @author Yasir
 */
public class AddAndDFTMonitor extends AddAndDFT {
    /**
     * Constructor that creates 2 matrices with given dimension sizes, randomizes them and
     * initializes the result of the operation as an empty matrix
     * @param threadCount Number of threads that will be involved in the calculation
     * @param matrixSize Size of the each dimension of each matrix
     */
    public AddAndDFTMonitor(int threadCount, int matrixSize) {
        super(threadCount, matrixSize);
    }

    /**
     * Barrier mechanism that will be used the by the worker threads.
     * Utilizes Java monitors to achieve parallelism.
     */
    @Override
    protected synchronized void Barrier() {
        _arrived++;
        if (_arrived == _threadCount) {
            _arrived = 0;
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
