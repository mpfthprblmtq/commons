package com.mpfthprblmtq.commons.wrappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonWrapperTest {

    @Test
    public void testConstructor() {
        JsonWrapper wrapper = new JsonWrapper();
        Assertions.assertNotNull(wrapper.mapper);
    }

    @Test
    public void testWithProperty() {
        JsonWrapper wrapper = new JsonWrapper().withProperty(JsonWrapper.ALLOW_COMMENTS, true);
        Assertions.assertNotNull(wrapper.mapper);
    }
}
