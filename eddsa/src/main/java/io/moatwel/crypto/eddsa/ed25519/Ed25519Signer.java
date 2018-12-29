package io.moatwel.crypto.eddsa.ed25519;

import java.math.BigInteger;

import io.moatwel.crypto.EdDsaSigner;
import io.moatwel.crypto.HashAlgorithm;
import io.moatwel.crypto.Hashes;
import io.moatwel.crypto.KeyPair;
import io.moatwel.crypto.Signature;
import io.moatwel.crypto.eddsa.Coordinate;
import io.moatwel.crypto.eddsa.Curve;
import io.moatwel.crypto.eddsa.DecodeException;
import io.moatwel.crypto.eddsa.EncodedCoordinate;
import io.moatwel.crypto.eddsa.EncodedPoint;
import io.moatwel.crypto.eddsa.Point;
import io.moatwel.util.ByteUtils;

/**
 * A Signer on Edwards-CURVE DSA specified on Ed25519 CURVE.
 *
 * @author halu5071 (Yasunori Horii) at 2018/6/11
 * @see Ed25519SchemeProvider
 * @see EdDsaSigner
 */
class Ed25519Signer implements EdDsaSigner {

    private static final Curve CURVE = Curve25519.getInstance();

    private final HashAlgorithm hashAlgorithm;

    Ed25519Signer(HashAlgorithm algorithm) {
        this.hashAlgorithm = algorithm;
    }

    @Override
    public Signature sign(KeyPair keyPair, byte[] data, byte[] context) {
        byte[] h = Hashes.hash(hashAlgorithm, keyPair.getPrivateKey().getRaw());

        // Step1
        byte[] first32 = ByteUtils.split(h, 32)[0];

        first32[0] &= 0xF8;
        first32[31] &= 0x7F;
        first32[31] |= 0x40;

        byte[] sSeed = ByteUtils.reverse(first32);
        BigInteger s = new BigInteger(sSeed);

        // Step2
        byte[] prefix = ByteUtils.split(h, 32)[1];

        byte[] rSeed = Hashes.hash(hashAlgorithm, prefix, data);
        byte[] rSeedReversed = ByteUtils.reverse(rSeed);
        BigInteger r = new BigInteger(1, rSeedReversed);

        // Step3
        Point pointR = CURVE.getBasePoint().scalarMultiply(r);
        byte[] rPoint = pointR.encode().getValue();

        // Step4
        byte[] kSeed = Hashes.hash(hashAlgorithm, rPoint, keyPair.getPublicKey().getRaw(), data);

        // Step5
        BigInteger k = new BigInteger(1, ByteUtils.reverse(kSeed));

        BigInteger pointS = k.mod(CURVE.getPrimeL()).multiply(s).add(r).mod(CURVE.getPrimeL());
        byte[] sPoint = new CoordinateEd25519(pointS).encode().getValue();

        // Step6
        return new SignatureEd25519(ByteUtils.paddingZeroOnTail(rPoint, 32),
                ByteUtils.paddingZeroOnTail(sPoint, 32));
    }

    @Override
    public boolean verify(KeyPair keyPair, byte[] data, byte[] context, Signature signature) {
        try {
            byte[] rSeed = signature.getR();
            EncodedPoint encodedR = new EncodedPointEd25519(rSeed);
            Point r = encodedR.decode();

            EncodedPoint encodedPublicKey = new EncodedPointEd25519(keyPair.getPublicKey().getRaw());
            Point a = encodedPublicKey.decode();

            EncodedCoordinate encodedS = new EncodedCoordinateEd25519(signature.getS());
            Coordinate s = encodedS.decode();

            byte[] kSeed = Hashes.hash(hashAlgorithm, r.encode().getValue(), keyPair.getPublicKey().getRaw(), data);
            Coordinate k = new EncodedCoordinateEd25519(kSeed).decode();

            Point checkPoint = r.add(a.scalarMultiply(k.getInteger()));

            Point target = CURVE.getBasePoint().scalarMultiply(s.getInteger());

            return checkPoint.isEqual(target);
        } catch (DecodeException e) {
            return false;
        }
    }
}
