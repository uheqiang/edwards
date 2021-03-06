package io.moatwel.crypto.eddsa.ed25519;

import io.moatwel.crypto.eddsa.Coordinate;
import io.moatwel.crypto.eddsa.Curve;
import io.moatwel.crypto.eddsa.EncodedCoordinate;
import io.moatwel.util.ArrayUtils;
import io.moatwel.util.ByteUtils;

import java.math.BigInteger;

/**
 * Coordinate on Curve25519
 *
 * @author halu5071 (Yasunori Horii)
 */
class CoordinateEd25519 extends Coordinate {

    public static final CoordinateEd25519 ONE = new CoordinateEd25519(BigInteger.ONE);

    private static final Curve curve = Curve25519.getInstance();

    CoordinateEd25519(BigInteger integer) {
        super(integer);
    }

    @Override
    public final Coordinate add(Coordinate val) {
        BigInteger integer = val.getInteger();
        return new CoordinateEd25519(value.add(integer));
    }

    @Override
    public final Coordinate divide(Coordinate val) {
        return new CoordinateEd25519(value.divide(val.getInteger()));
    }

    @Override
    public final Coordinate multiply(Coordinate val) {
        BigInteger integer = val.getInteger();
        return new CoordinateEd25519(value.multiply(integer));
    }

    @Override
    public final Coordinate subtract(Coordinate val) {
        BigInteger integer = val.getInteger();
        return new CoordinateEd25519(value.subtract(integer));
    }

    @Override
    public final Coordinate mod() {
        return new CoordinateEd25519(getInteger().mod(curve.getPrimePowerP()));
    }

    @Override
    public final Coordinate inverse() {
        return new CoordinateEd25519(getInteger().modInverse(curve.getPrimePowerP()));
    }

    @Override
    public Coordinate powerMod(BigInteger exponent) {
        return new CoordinateEd25519(value.modPow(exponent, curve.getPrimePowerP()));
    }

    @Override
    public Coordinate negate() {
        return new CoordinateEd25519(value.negate()).mod();
    }

    @Override
    public EncodedCoordinate encode() {
        byte[] seed = ByteUtils.reverse(ArrayUtils.toByteArray(value, 32));
        return new EncodedCoordinateEd25519(seed);
    }
}
