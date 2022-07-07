package gh2;

import deque.ArrayDeque;
import deque.Deque;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    Deque<GuitarString> strings;

    public GuitarHero() {
        strings = new ArrayDeque<>();
        for (int i = 0; i < keyboard.length(); i++) {
            double frequency = 440 * Math.pow(2, (double) (i - 24) / 12);
            GuitarString string = new GuitarString(frequency);
            strings.addLast(string);
        }
    }

    public double sample() {
        double sample = 0.0;
        for (GuitarString string : (ArrayDeque<GuitarString>) strings) {
            sample += string.sample();
        }

        return sample;
    }

    public void tic() {
        for (GuitarString string : (ArrayDeque<GuitarString>) strings) {
            string.tic();
        }
    }

    public void play() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int idx = keyboard.indexOf(key);

                if (idx == -1) {
                    continue;
                }

                strings.get(idx).pluck();
            }

            double sample = sample();

            StdAudio.play(sample);

            tic();
        }
    }

    public static void main(String[] args) {
        (new GuitarHero()).play();
    }
}
