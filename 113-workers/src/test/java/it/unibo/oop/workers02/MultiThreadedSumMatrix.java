package it.unibo.oop.workers02;

/**
 * Multithreaded implementation of the {@link SumMatrix} interface.
 */
public final class MultiThreadedSumMatrix implements SumMatrix {

    private final int nthread;

    /**
     * @param nthread the number of threads performing the class.
     */
    public MultiThreadedSumMatrix(final int nthread) {
        this.nthread = nthread;
    }

    @Override
    public double sum(final double[][] matrix) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sum'");
    }

    private static final class Worker extends Thread {

        private final double[][] matrix;
        private final int startpos;
        private final int nelem;
        private long res;

        /**
         * Build a new worker.
         *
         * @param matrix
         *            the matrix to sum
         * @param startpos
         *            the initial position for this worker
         * @param nelem
         *            the no. of elems to sum up for this worker
         */
        private Worker(final double[][] matrix, final int startpos, final int nelem) {
            super();
            this.matrix = matrix;
            this.startpos = startpos;
            this.nelem = nelem;
        }

        @Override
        @SuppressWarnings("PMD.SystemPrintln")
        public synchronized void run() {
            final int rows = this.matrix.length;
            System.out.println("Working from position " + startpos + " to position " + (startpos + nelem - 1));
            for (int i = startpos; i < rows && i < startpos + nelem; i++) {
                for (final double element: this.matrix[i]) {
                    res += element;
                }
            }
        }

        /**
         * Returns the result of summing up the doubles within the matrix.
         *
         * @return the sum of every element in the matrix
         */
        public synchronized long getResult() {
            return this.res;
        }

    }

}
