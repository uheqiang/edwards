package io.moatwel.crypto.eddsa.ed25519;

import io.moatwel.crypto.EdDsaSigner;
import io.moatwel.crypto.HashAlgorithm;
import io.moatwel.crypto.KeyGenerator;
import io.moatwel.crypto.KeyPair;
import io.moatwel.crypto.PrivateKey;
import io.moatwel.crypto.PublicKey;
import io.moatwel.crypto.Signature;
import io.moatwel.crypto.eddsa.EdDsaKeyGenerator;
import io.moatwel.crypto.eddsa.EdKeyAnalyzer;
import io.moatwel.crypto.eddsa.Edwards;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Ed25519VerifyTest {

    private KeyPair pair;
    private EdDsaSigner signer = new Ed25519Signer(HashAlgorithm.SHA_512, new Ed25519SchemeProvider(HashAlgorithm.SHA_512));
    private Edwards edwards;

    @Before
    public void setup() {
        KeyGenerator generator = new EdDsaKeyGenerator(new Ed25519SchemeProvider(HashAlgorithm.SHA_512));
        PrivateKey privateKey = PrivateKeyEd25519.fromHexString("abd3df0ba4c941a451c934a44938cc2bf051233c4e535931233c4e5351a4c695");
        pair = generator.generateKeyPair(privateKey);

        edwards = new Edwards();

        assertThat(pair.getPublicKey().getHexString(), is("195ac5d462f0aa357c424982250f994ab0918ecee50a2ce5c6feb4f6b07ab660"));
    }

    @Test
    public void success_VerifySignature_1() {
        Signature signature = signer.sign(pair, "demo".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "demo".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_2() {
        Signature signature = signer.sign(pair, "This is it.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "This is it.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_3() {
        Signature signature = signer.sign(pair, "klf;ajdfa98".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "klf;ajdfa98".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_4() {
        Signature signature = signer.sign(pair, "ed25519".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "ed25519".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_5() {
        Signature signature = signer.sign(pair, "!@#$@#Sample".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "!@#$@#Sample".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_6() {
        Signature signature = signer.sign(pair, "1092834dfakfjd98fle".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "1092834dfakfjd98fle".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_7() {
        Signature signature = signer.sign(pair, "09a[sp i9-a0r90q 90i [qi309qu3r9".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "09a[sp i9-a0r90q 90i [qi309qu3r9".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_8() {
        Signature signature = signer.sign(pair, "Are You Sleeping Brother John".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Are You Sleeping Brother John".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_9() {
        Signature signature = signer.sign(pair, "Humpty Dumpty sat on a wall, Humpty Dumpty had a great fall. Four-score Men and Four-score more, Could not make Humpty Dumpty where he was before.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Humpty Dumpty sat on a wall, Humpty Dumpty had a great fall. Four-score Men and Four-score more, Could not make Humpty Dumpty where he was before.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_10() {
        Signature signature = signer.sign(pair, "There was once a rich man whose wife lay sick, and when she felt her end drawing near she called to her only daughter to come near her bed".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "There was once a rich man whose wife lay sick, and when she felt her end drawing near she called to her only daughter to come near her bed".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_11() {
        Signature signature = signer.sign(pair, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_12() {
        Signature signature = signer.sign(pair, "Check the group equation [8][S]B = [8]R + [8][k]A'.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Check the group equation [8][S]B = [8]R + [8][k]A'.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_13() {
        Signature signature = signer.sign(pair, "Ed448ph is the same but with PH being SHAKE256(x, 64) and phflag being 1".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Ed448ph is the same but with PH being SHAKE256(x, 64) and phflag being 1".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_14() {
        Signature signature = signer.sign(pair, "When Harry knocked they heard a frantic scrabbling from inside and several booming barks.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "When Harry knocked they heard a frantic scrabbling from inside and several booming barks.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_15() {
        Signature signature = signer.sign(pair, "And when the evening came she wanted to go home, but the prince said he would go with her to take care of her, for he wanted to see where the beautiful maiden lived.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "And when the evening came she wanted to go home, but the prince said he would go with her to take care of her, for he wanted to see where the beautiful maiden lived.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_16() {
        Signature signature = signer.sign(pair, "Hush, little baby, don't say a word,　Mama's going to buy you a mockingbird.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Hush, little baby, don't say a word,　Mama's going to buy you a mockingbird.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_17() {
        Signature signature = signer.sign(pair, "London Bridge is broken down,　Broken down, broken down.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "London Bridge is broken down,　Broken down, broken down.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_18() {
        Signature signature = signer.sign(pair, "Peter Piper picked a peck of pickled peppers.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Peter Piper picked a peck of pickled peppers.".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_19() {
        Signature signature = signer.sign(pair, "This is the farmer sowing his corn, that kept the cock that crowed in the morn, that waked the priest all shaven and shorn, that married the man all tattered and torn, that kissed the maiden all forlorn, that milked the cow with the crumpled horn".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "This is the farmer sowing his corn, that kept the cock that crowed in the morn, that waked the priest all shaven and shorn, that married the man all tattered and torn, that kissed the maiden all forlorn, that milked the cow with the crumpled horn".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_20() {
        Signature signature = signer.sign(pair, "Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do".getBytes(), null, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void success_VerifySignature_21() {
        SecureRandom random = new SecureRandom();
        byte[] context = new byte[255];
        random.nextBytes(context);
        Signature signature = signer.sign(pair, "alice".getBytes(), context);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "alice".getBytes(), context, signature);

        assertThat(isVerified, is(true));
    }

    @Test
    public void failure_VerifySignature_1() {
        Signature signature = signer.sign(pair, "demo".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "demo.".getBytes(), null, signature);

        assertThat(isVerified, is(false));
    }

    @Test
    public void failure_VerifySignature_2() {
        Signature signature = signer.sign(pair, "This is it.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "This is it".getBytes(), null, signature);

        assertThat(isVerified, is(false));
    }

    @Test
    public void failure_VerifySignature_3() {
        Signature signature = signer.sign(pair, "klf;ajdfa98".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "klf;ajdfa98d".getBytes(), null, signature);

        assertThat(isVerified, is(false));
    }

    @Test
    public void failure_VerifySignature_16() {
        Signature signature = signer.sign(pair, "Hush, little baby, don't say a word,　Mama's going to buy you a mockingbird.".getBytes(), null);

        PublicKey receiverPublicKey = pair.getPublicKey();
        boolean isVerified = signer.verify(receiverPublicKey, "Hush,  little baby, don't say a word,　Mama's going to buy you a mockingbird.".getBytes(), null, signature);

        assertThat(isVerified, is(false));
    }

    @Test
    public void failure_VerifyMessage_9() {
        PrivateKey privateKey = PrivateKey.newInstance(new byte[]{70, 46, -23, 118, -119, 9, 22, -27, 79, -88, 37, -46, 107,
                -35, 2, 53, -11, -21, 91, 106, 20, 60, 25, -102, -80, -82, 94, -23, 50, -114, 8, -50});
        // make PublicKey to invoke DecodeException
        PublicKey publicKey = new PublicKey(new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 123});

        EdKeyAnalyzer analyzer = edwards.getKeyGenerator().getKeyAnalyzer();

        KeyPair keyPair = new KeyPair(privateKey, publicKey, analyzer);

        // invoke DecodeException
        boolean isValid = edwards.verify(keyPair.getPublicKey(), "hoge".getBytes(), new SignatureEd25519(new byte[32], new byte[32]));
        assertThat(isValid, is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void failure_TooLongContext() {
        byte[] context = new byte[256];

        Signature signature = signer.sign(pair, "doctor".getBytes(), null);
        PublicKey receiverPublicKey = pair.getPublicKey();
        signer.verify(receiverPublicKey, "doctor".getBytes(), context, signature);
    }
}
