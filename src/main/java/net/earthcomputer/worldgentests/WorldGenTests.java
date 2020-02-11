package net.earthcomputer.worldgentests;

import java.util.Objects;

public class WorldGenTests {

    public static int totalSamples = 0;
    public static int successfulRandomSamples = 0;
    public static int successfulSamples = 0;

    public static int tickCounter;

    public static void tick() {
        if (tickCounter++ % 100 == 0 && totalSamples != 0) {
            //System.out.printf("Successful proportion: %d, with random: %d, tested %d samples\n", successfulSamples, successfulRandomSamples, totalSamples);
        }
    }

    private static class Vec2i {
        private final int x, y;

        public Vec2i(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vec2i vec2i = (Vec2i) o;
            return x == vec2i.x &&
                    y == vec2i.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Vec2i{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}
