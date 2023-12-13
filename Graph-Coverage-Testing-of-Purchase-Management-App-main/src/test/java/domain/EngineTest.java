package domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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
    public void getAverageOrderQuantityByCustomerReturnsAvgWithSomeOrderHistory() {
        mockOrder1.setCustomer(1);
        mockOrder2.setCustomer(1);
        mockOrder1.setQuantity(4);
        mockOrder2.setQuantity(10);
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(7, mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAvgWithNotSeenCustomer() {
        mockOrder1.setCustomer(2);
        mockOrder2.setCustomer(1);
        mockOrder1.setQuantity(4);
        mockOrder2.setQuantity(10);
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(4, mockEngine.getAverageOrderQuantityByCustomer(2));
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
    public void getQuantityPatternByPriceReturns0With3MockOrdersAndPrice9ForOrdersAndInput() {
        mockOrder1.quantity = 7;
        mockOrder2.quantity = 17;
        mockOrder1.price = 9;
        mockOrder2.price = 9;
        mockOrder3.price = 9;
        mockOrder1.id = 1;
        mockOrder2.id = 2;
        mockOrder3.id = 3;
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        mockEngine.orderHistory.add(mockOrder3);
        assertEquals(0 , mockEngine.getQuantityPatternByPrice(9));
    }

    @Test
    public void getQuantityPatternByPriceReturnsDiffAmountWith3MockOrdersAndSameDiffBetweenOrdersAndInput() {
        mockOrder1.quantity = 7;
        mockOrder2.quantity = 17;
        mockOrder3.quantity = 27;
        mockOrder1.price = 9;
        mockOrder2.price = 9;
        mockOrder3.price = 9;
        mockOrder1.id = 1;
        mockOrder2.id = 2;
        mockOrder3.id = 3;
        mockEngine.orderHistory.add(mockOrder1);
        mockEngine.orderHistory.add(mockOrder2);
        mockEngine.orderHistory.add(mockOrder3);
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

    @Test
    public void addOrderAndGetFraudulentQuantityReturns0WhenOrderIsAlreadyInHistoryList() {
        mockEngine.orderHistory = new ArrayList<>();
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(0, mockEngine.addOrderAndGetFraudulentQuantity(mockOrder1));
    }

    @Test
    public void addOrderAndGetFraudulentQuantityReturns5() {
        mockOrder3.quantity = 10;
        mockOrder3.customer = 1;
        assertEquals(10, mockEngine.addOrderAndGetFraudulentQuantity(mockOrder3));
    }




    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryCustomerWith1Quantity() {
        mockOrder1.setCustomer(1);
        mockOrder1.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(4, mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryNegCustomerWith1Quantity() {
        mockOrder1.setCustomer(-1);
        mockOrder1.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(4, mockEngine.getAverageOrderQuantityByCustomer(-1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistory0CustomerWith1Quantity() {
        mockOrder1.setCustomer(0);
        mockOrder1.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(4, mockEngine.getAverageOrderQuantityByCustomer(0));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryCustomerWith0Quantity() {
        mockOrder1.setCustomer(1);
        mockOrder1.setQuantity(0);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryNegCustomerWith0Quantity() {
        mockOrder1.setCustomer(-1);
        mockOrder1.setQuantity(0);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(-1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistory0CustomerWith0Quantity() {
        mockOrder1.setCustomer(0);
        mockOrder1.setQuantity(0);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(0));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryCustomerWithNegQuantity() {
        mockOrder1.setCustomer(1);
        mockOrder1.setQuantity(-2);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(-2, mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryNegCustomerWithNegQuantity() {
        mockOrder1.setCustomer(-1);
        mockOrder1.setQuantity(-2);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(-2, mockEngine.getAverageOrderQuantityByCustomer(-1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistory0CustomerWithNegQuantity() {
        mockOrder1.setCustomer(0);
        mockOrder1.setQuantity(-2);
        mockEngine.orderHistory.add(mockOrder1);
        assertEquals(-2, mockEngine.getAverageOrderQuantityByCustomer(0));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryCustomersWith1Quantity() {
        mockOrder1.setCustomer(1);
        mockOrder1.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder1);
        mockOrder2.setCustomer(1);
        mockOrder2.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(4, mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryNegCustomersWith1Quantity() {
        mockOrder1.setCustomer(-1);
        mockOrder1.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder1);
        mockOrder2.setCustomer(-1);
        mockOrder2.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(4, mockEngine.getAverageOrderQuantityByCustomer(-1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistory0CustomersWith1Quantity() {
        mockOrder1.setCustomer(0);
        mockOrder1.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder1);
        mockOrder2.setCustomer(0);
        mockOrder2.setQuantity(4);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(4, mockEngine.getAverageOrderQuantityByCustomer(0));
    }

//    @Test
//    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryCustomersWith0Quantity() {
//        mockOrder1.setCustomer(1);
//        mockOrder1.setQuantity(0);
//        mockEngine.orderHistory.add(mockOrder1);
//        mockOrder2.setCustomer(1);
//        mockOrder2.setQuantity(4);
//        mockEngine.orderHistory.add(mockOrder2);
//        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(1));
//    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryNegCustomersWith0Quantity() {
        mockOrder1.setCustomer(-1);
        mockOrder1.setQuantity(0);
        mockEngine.orderHistory.add(mockOrder1);
        mockOrder2.setCustomer(-1);
        mockOrder2.setQuantity(0);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(-1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistory0CustomersWith0Quantity() {
        mockOrder1.setCustomer(0);
        mockOrder1.setQuantity(0);
        mockEngine.orderHistory.add(mockOrder1);
        mockOrder2.setCustomer(0);
        mockOrder2.setQuantity(0);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(0));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryCustomersWithNegQuantity() {
        mockOrder1.setCustomer(1);
        mockOrder1.setQuantity(-2);
        mockEngine.orderHistory.add(mockOrder1);
        mockOrder2.setCustomer(1);
        mockOrder2.setQuantity(-2);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(-2, mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistoryNegCustomersWithNegQuantity() {
        mockOrder1.setCustomer(-1);
        mockOrder1.setQuantity(-3);
        mockEngine.orderHistory.add(mockOrder1);
        mockOrder2.setCustomer(-1);
        mockOrder2.setQuantity(1);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(-1, mockEngine.getAverageOrderQuantityByCustomer(-1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithSameOrderHistory0CustomersWithNegQuantity() {
        mockOrder1.setCustomer(0);
        mockOrder1.setQuantity(-4);
        mockEngine.orderHistory.add(mockOrder1);
        mockOrder2.setCustomer(0);
        mockOrder2.setQuantity(0);
        mockEngine.orderHistory.add(mockOrder2);
        assertEquals(-2, mockEngine.getAverageOrderQuantityByCustomer(0));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithNullOrderHistory() {
        mockEngine.orderHistory = mockOrderHistory;
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithNullOrderHistoryNegCustomer() {
        mockEngine.orderHistory = mockOrderHistory;
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(-1));
    }

    @Test
    public void getAverageOrderQuantityByCustomerReturnsAverageWithNullOrderHistory0Customer() {
        mockEngine.orderHistory = mockOrderHistory;
        assertEquals(0, mockEngine.getAverageOrderQuantityByCustomer(0));
    }


}













