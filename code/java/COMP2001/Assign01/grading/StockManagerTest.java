package grading;

import inventory.Product;
import inventory.StockData;
import inventory.StockManager;
import inventory.StockManagerInterface;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GradingTestWatcher.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockManagerTest {

    StockManagerInterface stockManager;
    String[][] dataLoaded;
    static String[] under10 = {"Microwave Oven", "Superhero Pills", "Nails", "Fork Handles", "Candles", "Plugs", "Ronnies" };


    @BeforeEach
    void setUp() {
        dataLoaded = (new StockData()).stock_data;
        stockManager = new StockManager();
        stockManager.loadSampleData(dataLoaded);
    }

    @AfterEach
    void tearDown() {
    }

    @GradeValue(1)
    @Order(2)
    @Test
    public void testFind() {
        for (String[] dataRow : dataLoaded) {
            Product p = stockManager.findProduct(Integer.parseInt(dataRow[0]));
            assertEquals(Integer.parseInt(dataRow[2]), p.getQuantity(), "quantity not loaded correctly");
            assertEquals(Integer.parseInt(dataRow[0]), p.getID(), "id not loaded correctly");
            assertEquals(dataRow[1], p.getName(), "name not loaded correctly");

        }
    }

    @GradeValue(1)
    @Order(3)
    @Test // by name - exercise 4.60
    public void testFindProduct() {
        for (String[] dataRow : dataLoaded) {
            Product p = stockManager.findProduct(Integer.parseInt(dataRow[0]));
            assertNotNull(p, "product not found");
        }
        assertNull(stockManager.findProduct(101010), "bad id should return null");
    }

    @GradeValue(3)
    @Test
    @Order(1)
    public void loadSampleData() {
        // should be loaded by setup
        assertTrue(dataLoaded.length > 20, "Not enough sample data");
        assertEquals(dataLoaded.length, stockManager.productCount(), "Product count does not match sample data");
    }

    @Order(1)
    @GradeValue(1)
    @Test
    public void testHasOnOrderField() throws NoSuchFieldException {
        Field onOrderField = StockManager.class.getDeclaredField("onOrder");
        Type t = onOrderField.getType();
        assertEquals("java.util.ArrayList", onOrderField.getType().getTypeName(), "onOrder is wrong type");
        ParameterizedType genericType = (ParameterizedType) onOrderField.getGenericType();
        assertEquals("java.util.ArrayList<inventory.Product>", genericType.getTypeName(), "onOrder is wrong type");
    }

    @GradeValue(1)
    @Test
    @Order(5)
        // book - exercise 4.59
    public void delivery() {
        stockManager.delivery(113,100);
        assertEquals(300,stockManager.findProduct(113).getQuantity(),"ID 113 should, be 300 after delivery of 100");
    }

    @GradeValue(1)
    @Order(4)
    @Test
        // id - exercise 4.57
    public void testFindProductByName() {
        for (String[] dataRow : dataLoaded) {
            Product p = stockManager.findProduct(dataRow[1]);
            assertNotNull(p, "product not found");
        }
        assertNull(stockManager.findProduct("YabbaDabbaDoo"), "bad name should return null");
    }

    @GradeValue(1)
    @Order(6)
    @Test
        // exercise 4.58
    public void numberInStock() {
        for (String[] dataRow : dataLoaded) {
            Product p = stockManager.findProduct(dataRow[1]);
            assertEquals(Integer.parseInt(dataRow[2]), p.getQuantity(),  "wrong number found");
        }
        assertEquals(0, stockManager.numberInStock(5009), "bad id should return 0");
    }

    @GradeValue(1)
    @Order(2)
    @Test
        // already exists - exercise 4.60
    public void addProduct() {
        Product fc = new Product(500, "Fruitcake");
        stockManager.addProduct(fc);
        assertEquals(fc, stockManager.findProduct(fc.getName()), "Can't find product after adding");
    }



    @Order(8)
    @GradeValue(3)
    @Test
    public void lowStockProducts() {
        String[] under10 = {"Microwave Oven", "Superhero Pills", "Nails", "Fork Handles", "Candles", "Plugs", "Ronnies" };

        ArrayList<String> result = stockManager.lowStockProducts(10);
        for(String pName: under10 ){
            assertTrue(result.contains(pName), pName + " should be included <10");

        }
        for(String pName: result ){
            assertTrue(Arrays.asList(under10).contains(pName), pName + " should not be included <10");

        }
    }

    @Test
    @GradeValue(3)
    @Order(10)
    public void orderStockProducts() {
        stockManager.orderStockProducts(10);
         for(String pName: under10 ){
             int ordered = stockManager.findOrder(pName).getQuantity();
             int onHand = stockManager.findProduct(pName).getQuantity();
            assertEquals(10 - onHand,  ordered,pName + " order incorrect amount " );

        }
    }

    @Test
    @Order(11)
    @GradeValue(3)
    public void reorderStockProducts() {
        stockManager.orderStockProducts(10);
        for(String pName: under10 ){
            int ordered = stockManager.findOrder(pName).getQuantity();
            int onHand = stockManager.findProduct(pName).getQuantity();
            assertEquals(10 - onHand,  ordered,pName + " order incorrect amount " );
        }
        stockManager.orderStockProducts(20);
        for(String pName: under10 ){
            int ordered = stockManager.findOrder(pName).getQuantity();
            int onHand = stockManager.findProduct(pName).getQuantity();
            assertEquals(20 - onHand,  ordered,pName + " reordered incorrect amount " );
        }

    }

    // @Test
    @GradeValue(3)
    @Order(15)
    @Test
    public void orderAffectsStock() {
        HashMap<String, Integer> quanta = new HashMap<String, Integer>();
        for (String pName : under10) quanta.put(pName, stockManager.findProduct(pName).getQuantity());
        stockManager.orderStockProducts(10);
        for (String pName : under10)
            assertEquals(quanta.get(pName), stockManager.findProduct(pName).getQuantity(),
                    "ordering " + pName + " should not affect stock quantity");
    }

    @Test
    @Order(12)
    @GradeValue(2)
    public void findOrder() {
        stockManager.orderStockProducts(10);
        for(String pName: under10 ){
            assertNotNull(stockManager.findOrder(pName),
                    pName + " order missing");

        }
        assertNull(stockManager.findOrder("Shampoo"), "non-order product should return null");
    }
}
