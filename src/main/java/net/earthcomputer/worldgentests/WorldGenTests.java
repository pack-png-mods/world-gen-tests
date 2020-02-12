package net.earthcomputer.worldgentests;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

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

    public static long getSeed(Random rand) {
        try {
            Field field = Random.class.getDeclaredField("seed");
            field.setAccessible(true);
            return ((AtomicLong) field.get(rand)).get();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
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
