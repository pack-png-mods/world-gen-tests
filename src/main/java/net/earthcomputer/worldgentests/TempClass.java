package net.earthcomputer.worldgentests;

import java.util.Collection;

public class TempClass {

    public static String getSummaryString(Collection<Integer> values) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, sum = 0;
        for (int i : values) {
            if (i < min) min = i;
            if (i > max) max = i;
            sum += i;
        }
        double mean = values.size() == 0 ? 0 : (double) sum / values.size();
        double standardDeviation;
        if (values.size() <= 1) {
            standardDeviation = 0;
        } else {
            double sumSquaredError = 0;
            for (int i : values) {
                double diff = i - mean;
                sumSquaredError += diff * diff;
            }
            standardDeviation = Math.sqrt(sumSquaredError / (values.size() - 1));
        }

        return String.format("count=%d, sum=%d, min=%d, mean=%.6f, max=%d, sd=%.6f", values.size(), sum, min, mean, max, standardDeviation);
    }

}
