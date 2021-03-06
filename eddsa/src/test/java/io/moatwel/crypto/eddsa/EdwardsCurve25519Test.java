package io.moatwel.crypto.eddsa;

import io.moatwel.crypto.EdDsaSigner;
import io.moatwel.crypto.HashAlgorithm;
import io.moatwel.crypto.KeyPair;
import io.moatwel.crypto.PrivateKey;
import io.moatwel.crypto.PublicKey;
import io.moatwel.crypto.Signature;
import io.moatwel.crypto.eddsa.ed25519.Curve25519;
import io.moatwel.crypto.eddsa.ed25519.PrivateKeyEd25519;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class EdwardsCurve25519Test {

    private Edwards edwards;

    @Before
    public void setup() {
        edwards = new Edwards(HashAlgorithm.SHA_512);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failure_InstantiateEdwards() {
        SchemeProvider provider = null;
        new Edwards(provider);
    }

    @Test
    public void success_GeneratePublicKey() {
        byte[] privateKeySeed = new byte[]{42, 52, 74, 1, 6, -34, 13, 64, 83, 21, 34, -3, -7, 41, 92, 38, 43, 77, 21, -91, 23, 11, 84, 34, 98, 28, 44, 54, 123, -123, 34, 55};
        PrivateKey privateKey = PrivateKeyEd25519.fromBytes(privateKeySeed);

        PublicKey publicKey = edwards.derivePublicKey(privateKey);
        assertNotNull(publicKey);
    }

    @Test
    public void success_GenerateKeyPair_from_PrivateKey() {
        byte[] privateKeySeed = new byte[]{42, 52, 74, 1, 6, -34, 13, 64, 83, 21, 34, -3, -7, 41, 92, 38, 43, 77, 21, -91, 23, 11, 84, 34, 98, 28, 44, 54, 123, -123, 34, 55};
        PrivateKey privateKey = PrivateKeyEd25519.fromBytes(privateKeySeed);

        KeyPair pair = edwards.generateKeyPair(privateKey);
        assertNotNull(pair);

        assertThat(pair.getPrivateKey(), is(privateKey));
    }

    @Test
    public void success_GenerateKeyPair_from_Random() {
        KeyPair pair = edwards.generateKeyPair();
        assertNotNull(pair);
    }

    @Test
    public void success_Sign_and_Verify() {
        byte[] privateKeySeed = new byte[]{42, 52, 74, 1, 6, -34, 13, 64, 83, 21, 34, -3, -7, 41, 92, 38, 43, 77, 21, -91, 23, 11, 84, 34, 98, 28, 44, 54, 123, -123, 34, 55};
        PrivateKey privateKey = PrivateKeyEd25519.fromBytes(privateKeySeed);

        KeyPair pair = edwards.generateKeyPair(privateKey);

        Signature signature = edwards.sign(pair, "hogehoge".getBytes());

        assertNotNull(signature);

        boolean isValid1 = edwards.verify(pair.getPublicKey(), "hogehoge".getBytes(), signature);
        assertThat(isValid1, is(true));

        boolean isValid2 = edwards.verify(pair.getPublicKey(), "hogefoge".getBytes(), signature);
        assertThat(isValid2, is(false));
    }

    @Test
    public void success_GetCurve() {
        Curve curve = edwards.getCurve();
        assertEquals(curve, Curve25519.getInstance());
    }

    @Test
    public void success_GetSigner() {
        EdDsaSigner signer = edwards.getDsaSigner();
        assertNotNull(signer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failure_GeneratePublicKey() {
        // Private key on Curve448
        PrivateKey privateKey = PrivateKey.newInstance("6c82a562cb808d10d632be89c8513ebf" +
                "6c929f34ddfa8c9f63c9960ef6e348a3" +
                "528c8a3fcc2f044e39a3fc5b94492f8f" +
                "032e7549a20098f95b");

        PublicKey publicKey = edwards.derivePublicKey(privateKey);
    }
}
