package com.example.qriffic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

/**
 * Test suite for QRCode class
 */
public class QRCodeTest {

    private QRCode mockQRCode() throws NoSuchAlgorithmException {

        return new QRCode("uyhpOIUYHPDFnklmd;sajfonui893",
                new Location("53.1234", "12.5623"));
    }

    @Test
    void testGetters() throws NoSuchAlgorithmException {

        QRCode mockQRCode = mockQRCode();

        // CHANGE THIS WHEN WE FINISH THE SCORE CALCULATOR
        assertEquals(0, mockQRCode.getScore());
        assertNotEquals(1, mockQRCode.getScore());

        assertEquals(new Location("53.1234", "12.5623").getLongLat(),
                mockQRCode.getLocation().getLongLat());
        assertNotEquals(new Location("53.1234", "12.5643").getLongLat(),
                mockQRCode.getLocation().getLongLat());

        assertEquals("uyhpOIUYHPDFnklmd;sajfonui893", mockQRCode.getRawString());
        assertNotEquals("aahpOIUYHPDFnklmd;sajfonui893", mockQRCode.getRawString());

        // SHA256 output generated by https://emn178.github.io/online-tools/sha256.html
        assertEquals("c914133b717c126ecafc5b9b8bd529bcad06d064edc073906a6b83b58bf57e5c",
                mockQRCode.getIdHash());
        assertNotEquals("c914133b717c126ecafc5b9b8bd529bcad06d064edc073906a6b83b58bf57a5c",
                mockQRCode.getIdHash());

        // CHANGE THIS WHEN WE FINISH NAME GENERATOR
        assertEquals("UNNAMED MONSTER", mockQRCode.getName());
        assertNotEquals("NAMED MONSTER", mockQRCode.getName());
    }

    @Test
    void testCompareTo() throws NoSuchAlgorithmException {

        QRCode mockQRCode = mockQRCode();

        assertEquals(0, mockQRCode.compareTo(new QRCode("uyhpOIUYHPDFnklmd;sajfonui893")));
        assertNotEquals(0, mockQRCode.compareTo(new QRCode("ayhpOIUYHPDFnklmd;sajfonui893")));
    }
}