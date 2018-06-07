package io.moatwel.crypto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(KeyPair.class)
public class KeyPairTest {

    private CryptoProvider mockProvider;
    private PrivateKey mockPrivateKey;
    private PublicKey mockPublicKey;
    private KeyGenerator mockGenerator;
    private KeyAnalyzer mockAnalyzer;
    private KeyPair mockTmpKeyPair;

    @Before
    public void setup() {
        mockProvider = mock(CryptoProvider.class);
        mockPrivateKey = mock(PrivateKey.class);
        mockPublicKey = mock(PublicKey.class);
        mockGenerator = mock(KeyGenerator.class);
        mockAnalyzer = mock(KeyAnalyzer.class);
        mockTmpKeyPair = mock(KeyPair.class);
    }

    @Test
    public void success_GenerateKeyPair_private_key_and_engine() {
        when(mockProvider.createKeyGenerator()).thenReturn(mockGenerator);
        when(mockGenerator.derivePublicKey(mockPrivateKey)).thenReturn(mockPublicKey);
        when(mockProvider.createKeyAnalyzer()).thenReturn(mockAnalyzer);
        when(mockAnalyzer.isKeyCompressed(mockPublicKey)).thenReturn(true);

        KeyPair pair = new KeyPair(mockPrivateKey, mockProvider);

        assertThat(pair.getPublicKey(), is(mockPublicKey));
        assertThat(pair.getPrivateKey(), is(mockPrivateKey));
    }

    @Test
    public void success_GenerateKeyPair_random() {
        when(mockProvider.createKeyGenerator()).thenReturn(mockGenerator);
        when(mockGenerator.generateKeyPair()).thenReturn(mockTmpKeyPair);
        when(mockTmpKeyPair.getPrivateKey()).thenReturn(mockPrivateKey);
        when(mockTmpKeyPair.getPublicKey()).thenReturn(mockPublicKey);
        when(mockProvider.createKeyAnalyzer()).thenReturn(mockAnalyzer);
        when(mockAnalyzer.isKeyCompressed(mockPublicKey)).thenReturn(true);

        KeyPair pair = mockProvider.createKeyGenerator().generateKeyPair();

        assertThat(pair.getPrivateKey(), is(mockPrivateKey));
        assertThat(pair.getPublicKey(), is(mockPublicKey));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failure_GenerateKeyPair_public_key_not_compressed() {
        when(mockProvider.createKeyGenerator()).thenReturn(mockGenerator);
        when(mockGenerator.derivePublicKey(mockPrivateKey)).thenReturn(mockPublicKey);
        when(mockProvider.createKeyAnalyzer()).thenReturn(mockAnalyzer);
        when(mockAnalyzer.isKeyCompressed(mockPublicKey)).thenReturn(false);

        new KeyPair(mockPrivateKey, mockProvider);
    }
}
