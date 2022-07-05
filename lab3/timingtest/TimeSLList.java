package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        int N = 1000;
        int opCount = 10000;
        for (int i = 0; i < 8; i++, N*=2) {
            // construct the SLList.
            SLList<Integer> test = new SLList<>();
            for (int item = 0; item < N; item++) {
                test.addFirst(item);
            }

            // count the operation time.
            Stopwatch sw = new Stopwatch();
            for (int item = 0; item < opCount; item++) {
                test.getLast();
            }
            double time = sw.elapsedTime();

            Ns.addLast(N);
            opCounts.addLast(opCount);
            times.addLast(time);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
