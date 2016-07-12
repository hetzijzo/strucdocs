package org.hetzijzo.strucdocs.transpose;

public enum Interval {

    second("2"),
    minor("m"),
    major("maj"),
    fourth("4"),
    fifth("5"),
    sixth("6"),
    seventh("7"),
    ninth("9"),
    eleventh("11"),
    sus2("sus2"),
    sus4("sus4"),
    add2("sus2"),
    add4("add4"),
    dim5("b5"),
    aug5("#5");

    /**
     * The notation of the Interval
     */
    String notation;

    Interval(String notation) {
        this.notation = notation;
    }

    @Override
    public String toString() {
        return notation;
    }
}
