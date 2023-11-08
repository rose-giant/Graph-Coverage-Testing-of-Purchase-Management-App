package domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class EngineTest {
    public Engine mockEngine;
    public Order mockOrder1 = new Order();
    public Order mockOrder2 = new Order();
    public Order mockOrder3 = new Order();
    public ArrayList<Order> mockOrderHistory = new ArrayList<>();

    @Before
    public void initialPhase() {
        mockEngine = new Engine();
    }

    @Test
    public void EngineConstructorAssignsOrderHistoryANewArrayList() {
        assertEquals(new ArrayList<>(),  mockEngine.orderHistory);
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturns0WithNoOrderHistory() {
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturns12With1MockOrderAndSameCustomerForTheOrderAndInput() {
        mockOrder1.customer = 8;
        mockOrder1.quantity = 12;
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(12, mockEngine.getAverageOrderQuantityByCustomer(8));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturns12With2MockOrdersForCustomer1AndInputCustomer1() {
        mockOrder1.quantity = 7;
        mockOrder2.quantity = 17;
        mockOrder1.customer = 1;
        mockOrder2.customer = 1;
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(12 , mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    //An issue was found: when we have orders with different customer, it will have a division by 0
    @Test
    public void getAverageOrderQuantityByCustomerReturns0With2MockOrdersForCustomer1AndInputCustomer2() {
        mockOrder1.quantity = 7;
        mockOrder2.quantity = 17;
        mockOrder1.customer = 1;
        mockOrder2.customer = 1;
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(0 , mockEngine.getAverageOrderQuantityByCustomer(2));
    }


    @Test
    public void getQuantityPatternByPriceReturns0With0OrderHistoryAndPrice9() {
        assertEquals(0, mockEngine.getQuantityPatternByPrice(9));
    }

    @Test
    public void getQuantityPatternByPriceReturns0With2MockOrdersWithDifferentPricesWithInput() {
        mockOrder1.quantity = 7;
        mockOrder2.quantity = 17;
        mockOrder1.price = 9;
        mockOrder2.price = 9;
        mockOrder1.id = 1;
        mockOrder2.id = 2;
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(0 , mockEngine.getQuantityPatternByPrice(19));
    }

    @Test
    public void getQuantityPatternByPriceReturns12With2MockOrdersAndPrice9ForOrdersAndInput() {
        mockOrder1.quantity = 7;
        mockOrder2.quantity = 17;
        mockOrder1.price = 9;
        mockOrder2.price = 9;
        mockOrder1.id = 1;
        mockOrder2.id = 2;
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(10 , mockEngine.getQuantityPatternByPrice(9));
    }

    @Test
    public void getCustomerFraudulentQuantityReturns5With2MockOrdersWithAverageOf12AndInputOrderQuantityOf17() {
        mockOrder1.quantity = 7;
        mockOrder2.quantity = 17;
        mockOrder1.customer = 1;
        mockOrder2.customer = 1;
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(5, mockEngine.getCustomerFraudulentQuantity(mockOrder2));
    }

    @Test
    public void getCustomerFraudulentQuantityReturns0With2MockOrdersWithAverageOf12AndInputOrderQuantityOf7() {
        mockOrder1.quantity = 7;
        mockOrder2.quantity = 17;
        mockOrder1.customer = 1;
        mockOrder2.customer = 1;
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(0, mockEngine.getCustomerFraudulentQuantity(mockOrder1));
    }

    @Test
    public void addOrderAndGetFraudulentQuantityReturns0WhenEmptyOrderHistoryDoesNotContainTheInputMockOrder() {
        mockEngine.orderHistory = new ArrayList<>();
        assertEquals(0, mockEngine.addOrderAndGetFraudulentQuantity(mockOrder1));
    }

}
















