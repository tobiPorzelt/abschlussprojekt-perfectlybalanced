package de.hhu.abschlussprojektverleihplattform.model;

import org.junit.Assert;
import org.junit.Test;


public class LendingStatusTest {

    @Test
    public void testToOrdinal(){

        Lendingstatus s1 = Lendingstatus.requested;
        Assert.assertEquals(0,s1.ordinal());
    }

    @Test
    public void testFromOrdinal(){

        Lendingstatus s2 = Lendingstatus.values()[0];
        Assert.assertEquals(0,s2.ordinal());
        Assert.assertEquals(Lendingstatus.requested,s2);
    }
}
