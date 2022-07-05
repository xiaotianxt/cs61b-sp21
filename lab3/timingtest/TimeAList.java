package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Date;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        int N = 1000;
        for (int i = 0; i < 8; i++, N *= 2) {
            Stopwatch sw = new Stopwatch();

            AList<Integer> testSet = new AList<>();
            for (int item = 0; item < N; item++) {
                testSet.addLast(item);
            }

            double time = sw.elapsedTime();
            Ns.addLast(N);
            opCounts.addLast(N);
            times.addLast(time);
        }

        printTimingTable(Ns, times, opCounts);
    }
}
