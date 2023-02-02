package com.mpfthprblmtq.commons.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RequestPropertiesTest {

    @Test
    public void testBuilder() {
        RequestProperties requestProperties = new RequestProperties()
                .withProperty("Content-Type", "application/json")
                .build();

        assertTrue(requestProperties.getProperties().containsKey("Content-Type"));
        assertEquals("application/json", requestProperties.getProperties().get("Content-Type"));
    }

    @Test
    public void testBuilderWithRemovedProperty() {
        RequestProperties initialRequestProperties = new RequestProperties()
                .withProperty("Content-Type", "application/json")
                .withProperty("Accept", "application/json")
                .build();

        assertTrue(initialRequestProperties.getProperties().containsKey("Content-Type"));
        assertEquals("application/json", initialRequestProperties.getProperties().get("Content-Type"));
        assertTrue(initialRequestProperties.getProperties().containsKey("Accept"));
        assertEquals("application/json", initialRequestProperties.getProperties().get("Accept"));

        RequestProperties updatedRequestProperties = initialRequestProperties
                .withoutProperty("Accept")
                .build();

        assertTrue(updatedRequestProperties.getProperties().containsKey("Content-Type"));
        assertEquals("application/json", updatedRequestProperties.getProperties().get("Content-Type"));
        assertFalse(updatedRequestProperties.getProperties().containsKey("Accept"));
        assertNull(updatedRequestProperties.getProperties().get("Accept"));
    }
}