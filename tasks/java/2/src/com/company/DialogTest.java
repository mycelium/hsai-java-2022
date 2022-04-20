package com.company;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DialogTest {

    @org.junit.jupiter.api.Test
    void parseArgsTest1() {
        String[] testArgs = new String[] {"type=uni", "params=[0.5,5]", "cnt=100"};
        HashMap<String, Object> testParams = Dialog.parseArgs(testArgs);
        HashMap<String, Object> checkParams = new HashMap<>(
                Map.of("type", Distribution.Uniform, "cnt", 10000L, "out", "",
                        "params", new Float[]{0.5f, 5f}));

        assertEquals(checkParams.get("type"), testParams.get("type"));
        assertEquals(checkParams.get("cnt"), testParams.get("cnt"));
        assertEquals(checkParams.get("out"), testParams.get("out"));
        assertEquals(((Float[])checkParams.get("params"))[0], ((Float[])testParams.get("params"))[0]);
        assertEquals(((Float[])checkParams.get("params"))[1], ((Float[])testParams.get("params"))[1]);
    }
    @org.junit.jupiter.api.Test
    void parseArgsTest2() {
        String[] testArgs = new String[] {"type=norm", "params=[0,1]", "cnt=50000"};
        HashMap<String, Object> testParams = Dialog.parseArgs(testArgs);
        HashMap<String, Object> checkParams = new HashMap<>(
                Map.of("type", Distribution.Normal, "cnt", 50000L, "out", "",
                        "params", new Float[]{0f, 1f}));

        assertEquals(checkParams.get("type"), testParams.get("type"));
        assertEquals(checkParams.get("cnt"), testParams.get("cnt"));
        assertEquals(checkParams.get("out"), testParams.get("out"));
        assertEquals(((Float[])checkParams.get("params"))[0], ((Float[])testParams.get("params"))[0]);
        assertEquals(((Float[])checkParams.get("params"))[1], ((Float[])testParams.get("params"))[1]);
    }
}