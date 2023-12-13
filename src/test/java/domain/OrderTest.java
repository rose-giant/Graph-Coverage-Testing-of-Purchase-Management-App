package domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OrderTest {
    Order mockOrder;
    int mockId = 12;

    @Before
    public void initialPhase() {
        mockOrder = new Order();
        mockOrder.setId(mockId);
    }

    @Test
    public void OrderIdEquals() {
        Order theOrder = new Order();
        theOrder.setId(mockId);
        assertTrue(mockOrder.equals(theOrder));
    }

    @Test
    public void OrderIdNotEquals() {
        Order theOrder = new Order();
        theOrder.setId(mockId + 1);
        assertFalse(mockOrder.equals(theOrder));
    }

    @Test
    public void NotOrderPassedToEquals() {
        assertFalse(mockOrder.equals(new Object()));
    }

    @Test
    public void getCustomerTest() {
        mockOrder.setCustomer(1);
        assertEquals(1, mockOrder.getCustomer());
    }

    @Test
    public void getIdTest() {
        mockOrder.setId(2);
        assertEquals(2, mockOrder.getId());
    }

    @Test
    public void getQuantityTest() {
        mockOrder.setQuantity(2);
        assertEquals(2, mockOrder.getQuantity());
    }

    @Test
    public void getPriceTest() {
        mockOrder.setPrice(2);
        assertEquals(2, mockOrder.getPrice());
    }
}
