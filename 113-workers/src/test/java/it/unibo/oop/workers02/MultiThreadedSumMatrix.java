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

}
