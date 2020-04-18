package Q2;

import java.util.ArrayList;

/**
 * Class that does DFT(A+B) to two randomly generated matrices of complex numbers.
 * Does it in a multi threaded fashion which helps parallelize this operation.
 * Provides configurable matrix sizes and thread numbers.
 * @author Yasir
 */
public abstract class AddAndDFT {
    /**
     * Number of threads that will perform this operation
     */
    protected int _threadCount = 1;

    /**
     * Size of each dimension of the matrices
     */
    protected int _matrixSize = 32;

    /**
     * First parameter of the input matrices
     */
    private final ComplexMatrix _m1;

    /**
     * Second parameter of the input matrices
     */
    private final ComplexMatrix _m2;

    /**
     * Result of the DFT(A+B) operation
     */
    public final ComplexMatrix Result;

    /**
     * Control variable that is used to stop ongoing calculation
     */
    private boolean _isCancelled;

    /**
     * Parallelization variable do determine how many threads have reached the barrier
     */
    protected static volatile int _arrived = 0;

    /**
     * Constructor that creates 2 matrices with given dimension sizes, randomizes them and
     * initializes the result of the operation as an empty matrix
     * @param threadCount Number of threads that will be involved in the calculation
     * @param matrixSize Size of the each dimension of each matrix
     */
    public AddAndDFT(int threadCount, int matrixSize) {
        _threadCount = threadCount;
        _matrixSize = matrixSize;

        _m1 = new ComplexMatrix(_matrixSize);
        _m1.Randomize();

        _m2 = new ComplexMatrix(_matrixSize);
        _m2.Randomize();

        Result = new ComplexMatrix(_matrixSize);

        _isCancelled = false;
    }

    /**
     * Cancels the on-going calculations in the threads
     */
    public void Cancel() {
        _isCancelled = true;
    }

    /**
     * Shares the matrix among the threads that has given in the initialization step,
     * starts each worker thread and starts a timer to count how many milliseconds have passed
     * since the start of the operation. Waits for worker threads to end and outputs the time
     * passed for the whole operation.
     * @return True if the operation has ended without interruption, false otherwise
     */
    public boolean Calculate() {
        _isCancelled = false;

        final ComplexMatrix result = new ComplexMatrix(_matrixSize);

        int threadPerDimension = (int)Math.sqrt(_threadCount);
        int sizePerDimension = _matrixSize / threadPerDimension;

        System.out.println("thread per dimension: " + threadPerDimension);

        ArrayList<Worker> workers = new ArrayList<>();

        for (int i = 0; i < threadPerDimension; i++) {
            for (int j = 0; j < threadPerDimension; j++) {
                int xStart = i  * sizePerDimension;
                int xEnd = (i + 1) * sizePerDimension;
                int yStart = j * sizePerDimension;
                int yEnd = (j + 1) * sizePerDimension;

                Worker worker = new Worker(xStart, xEnd, yStart, yEnd);
                workers.add(worker);
            }
        }

        for (Worker worker : workers) {
            worker.start();
        }

        long start = System.currentTimeMillis();

        for (Worker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = _isCancelled ? -1 : finish - start;

        System.out.println("Done");

        ApplicationWindow.FinishCalculation(timeElapsed);

        return _isCancelled;
    }

    /**
     * Parallelization mechanism that should be used by the threads so that they can
     * start their DFT operation after all addition finishes. Since there are multiple ways
     * of parallelization, this method has made abstract and left for deriving class to implement
     * any mechanism they might want to.
     */
    protected abstract void Barrier();

    /**
     * Worker thread class that receives a range of indices to calculate, does addition between
     * input matrices, waits for other worker threads to finish that step, and continues on to
     * do DFT on the given portion.
     */
    private class Worker extends Thread {
        /**
         * Start index of the column coordinates
         */
        private int _xStart;

        /**
         * End index of the column coordinates
         */
        private int _xEnd;

        /**
         * Start index of the row coordinates
         */
        private int _yStart;

        /**
         * End index of the row coordinates
         */
        private int _yEnd;

        /**
         * Constructor that initializes this worker with the given range of indices in the matrix
         * @param _xStart Low column index of the calculation
         * @param _xEnd High column index of the calculation
         * @param _yStart Low row index of the calculation
         * @param _yEnd High row index of the calculation
         */
        public Worker(int _xStart, int _xEnd, int _yStart, int _yEnd) {
            this._xStart = _xStart;
            this._xEnd = _xEnd;
            this._yStart = _yStart;
            this._yEnd = _yEnd;
        }

        /**
         * Starts the addition on the given part, waits for all other workers to finish their job,
         * then starts the DFT on the given part. Can get cancelled by the owner class.
         */
        @Override
        public void run() {
            // Addition
            for (int i = _xStart; i < _xEnd; i++) {
                for (int j = _yStart; j < _yEnd; j++) {
                    if(_isCancelled) break;

                    Result.data[i][j] = Complex.Add(_m1.data[i][j], _m2.data[i][j]);
                }
            }

            Barrier();

            // DFT
            for (int i = _xStart; i < _xEnd; i++) {
                for (int j = _yStart; j < _yEnd; j++) {
                    if(_isCancelled) break;

                    Result.data[i][j] = Complex.Add(_m1.data[i][j], new Complex(1, 5));
                }
            }

            Barrier();
        }
    }
}
