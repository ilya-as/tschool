package com.tsystems.javaschool.tasks.pyramid;

import java.util.Comparator;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {

        try {
            inputNumbers.sort(Comparator.naturalOrder());
        } catch (Throwable e) {
            throw new CannotBuildPyramidException();
        }

        int n = ((int) Math.sqrt(8 * inputNumbers.size() + 1) - 1) / 2;

        if (((1 + n) * n) / 2 != inputNumbers.size()) {
            throw new CannotBuildPyramidException();
        }

        int[][] pyramid = new int[n][n * 2 - 1];

        int currentIndex = 0;
        for (int i = 0; i < n; i++) {
            int m = n - i - 1;
            for (int j = 0; j <= i; j++) {
                pyramid[i][m] = inputNumbers.get(currentIndex);
                currentIndex += 1;
                m += 2;
            }
        }
        return pyramid;
    }
}
