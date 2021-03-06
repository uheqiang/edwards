package io.moatwel.crypto.eddsa.ed448;

import io.moatwel.crypto.EdDsaSigner;
import io.moatwel.crypto.HashAlgorithm;
import io.moatwel.crypto.PrivateKey;
import io.moatwel.crypto.eddsa.PublicKeyDelegate;
import io.moatwel.crypto.eddsa.SchemeProvider;
import io.moatwel.util.ByteUtils;

import java.security.SecureRandom;

/**
 * SchemeProvider for Ed448.
 *
 * @author halu5071 (Yasunori Horii)
 */
public class Ed448SchemeProvider extends SchemeProvider {

    private final HashAlgorithm hashAlgorithm;

    /**
     * Constructor of Ed448SchemeProvider.
     * <p>
     * Note that wrong hash algorithm is not allowed on Curve448 of
     * Edwards-curve DSA.
     *
     * @param hashAlgorithm hash algorithm you use.
     */
    public Ed448SchemeProvider(HashAlgorithm hashAlgorithm) {
        super(Curve448.getInstance());

        if (hashAlgorithm == null) {
            throw new IllegalArgumentException("argument HashAlgorithm must not be null.");
        }
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public EdDsaSigner getSigner() {
        return new Ed448Signer(hashAlgorithm, this);
    }

    @Override
    public PublicKeyDelegate getPublicKeyDelegate() {
        return new Ed448PublicKeyDelegate(hashAlgorithm);
    }

    @Override
    public PrivateKey generatePrivateKey() {
        SecureRandom random = new SecureRandom();
        byte[] seed = new byte[57];
        random.nextBytes(seed);
        return PrivateKey.newInstance(seed);
    }

    @Override
    public byte[] preHash(byte[] input) {
        return input;
    }

    @Override
    public byte[] dom(byte[] context) {
        String domPrefix = "SigEd448";
        return ByteUtils.join(
                domPrefix.getBytes(),
                // 0 is a flag for Ed448
                new byte[]{(byte) 0},
                new byte[]{(byte) context.length},
                context);
    }
}
