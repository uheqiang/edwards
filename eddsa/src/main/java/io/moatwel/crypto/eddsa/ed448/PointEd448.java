package io.moatwel.crypto.eddsa.ed448;

import java.math.BigInteger;

import io.moatwel.crypto.eddsa.Coordinate;
import io.moatwel.crypto.eddsa.Point;

public class PointEd448 extends Point {

    /**
     * constructor of Point
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public PointEd448(Coordinate x, Coordinate y) {
        super(x, y);
        curve = Ed448Curve.getCurve();
    }

    @Override
    public Point add(Point point) {
        return null;
    }

    @Override
    public Point scalarMultiply(BigInteger integer) {
        return null;
    }
}