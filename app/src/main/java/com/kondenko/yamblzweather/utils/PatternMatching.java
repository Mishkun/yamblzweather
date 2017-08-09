package com.kondenko.yamblzweather.utils;

/**
 * Attempt to bring pattern matching to Java.
 * See here for explanation: https://kerflyn.wordpress.com/2012/05/09/towards-pattern-matching-in-java/
 *
 */

public class PatternMatching<P, T> {
    private Pattern<P, T>[] patterns;

    public PatternMatching(Pattern<P, T>... patterns) { this.patterns = patterns; }

    public T matchFor(P value) {
        for (Pattern<P, T> pattern : patterns)
            if (pattern.matches(value))
                return pattern.apply(value);

        throw new IllegalArgumentException("cannot match " + value);
    }

    public interface PatternFunction<P, T>{
        T apply(P value);
    }

    public interface Pattern<P, T> {
        boolean matches(P value);
        T apply(P value);

        class ClassPattern<P, T> implements Pattern<P, T> {

            private Class<P> clazz;

            private PatternFunction<P,T> function;

            private ClassPattern(Class<P> clazz, PatternFunction<P, T> function) {
                this.clazz = clazz;
                this.function = function;
            }

            public boolean matches(P value) {
                return clazz.isInstance(value);
            }

            public T apply(P value) {
                return function.apply(value);
            }

            public static <T, P> Pattern instanceOf(Class<P> clazz, PatternFunction<P, T> function) {
                return new ClassPattern<>(clazz, function);
            }
        }
    }
}
