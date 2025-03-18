package inventory;
import java.util.ArrayList;

/**
 * Manage the stock in a business.
 * The stock is described by zero or more Products.
 * 
 * @author Dewan Mukto (dmimukto) 
 * @version 2022 Sept 27
 */
public class StockManager implements StockManagerInterface
{
    // A list of the products.
    private ArrayList<Product> stock;
    private ArrayList<Product> onOrder;

    /**
     * Initialise the stock manager.
     */
    public StockManager()
    {
        // the array lists are initialized
        stock = new ArrayList<>();
        onOrder = new ArrayList<>();
    }

    /**
     * Add a product to the list.
     * @param item The item to be added.
     */
    public void addProduct(Product item)
    {
        stock.add(item); // adds a product to the stock
    }

    /**
     * Receive a delivery of a particular product.
     * Increase the quantity of the product by the given amount.
     * @param id The ID of the product.
     * @param amount The amount to increase the quantity by.
     */
    public void delivery(int id, int amount)
    {
        int index;
        // finding an index
        for(index=0;index<stock.size();index++){
            if(stock.get(index).getID()==id){
                stock.get(index).increaseQuantity(amount); // increasing the product's quantity
            }
        }
    }

    /**
     * Try to find a product in the stock with the given id.
     * @return The identified product, or null if there is none
     *         with a matching ID.
     */
    public Product findProduct(int id)
    {
        int index;
        // using a for loop to scan the stock for a match
        for (index=0;index<stock.size();index++){
            if(stock.get(index).getID()==id){
                return stock.get(index); // match is found of the product ID
            }
        }
        return null; // no match found for the product ID
    }

    /**
     * Locate a product with the given ID, and return how
     * many of this item are in stock. If the ID does not
     * match any product, return zero.
     * @param id The ID of the product.
     * @return The quantity of the given product in stock.
     */
    public int numberInStock(int id)
    {
        int index;
        // using a for loop to scan the stock for a match
        for (index=0;index<stock.size();index++){
            if(stock.get(index).getID()==id){
                return stock.get(index).getQuantity(); // match is found and quantity is returned
            }
        }
        return 0;
    }

    /**
     * Print details of all the products.
     */
    public void printProductDetails()
    {
        int index;
        // using a for loop to iterate over the stock
        for (index=0;index<stock.size();index++){
            System.out.println("ID: "+stock.get(index).getID()+" Name: "+stock.get(index).getName()+" Quantity: "+stock.get(index).getQuantity()); // each product detail printed
        }
    }

    /**
     * Return the number of different types of stock items
     * in stock list. Added by E Brown.
     */
    public int productCount() {
        return stock.size();
    }

    /**
     * Try to find a product in the orders with the given name.
     *
     * @return The identified product, or null if there is none
     * with a matching name.
     */
    public Product findOrder(String name)
    {
        int i;
        for(i=0;i<onOrder.size();i++){
            if(onOrder.get(i).getName().equalsIgnoreCase(name) || onOrder.get(i).getName()==name) return onOrder.get(i);
        }
        return null;
        // int index;
        // // using a for loop to iterate over the orders and scan for a name match
        // int size = onOrder.size();
        // if(size <= 1){
        // if(onOrder.get(0).getName().equals(name)){return onOrder.get(0);};
        // }
        // for(index=0;index<onOrder.size();index++){
        // if (onOrder.get(index).getName()==name){
        // return onOrder.get(index); // returns the Product if a match is found
        // }
        // }
        // return null; // returns null if no match is found
    }

    /**
     * Load the stock inventory with the Product information contained in the data provided as a
     * parameter to the method.
     * The data parameter is a 2d array of strings.
     * Earch row index i of the 2d array data[i] contains three strings:
     *      data[i][0] is a string containing the id of the ith product
     *      data[i][1] is a string containing the name/description of the ith product
     *        data[i][2] is a string containing the quantity of the ith product
     * Note: some of these values will need to be converted from String type to int type.
     */
    public void loadSampleData(String[][] data)
    {
        int index;
        // using a for loop to iterate over the 2d array of sample data
        for(index=0;index<data.length;index++){
            // each data sample is converted to a Product object
            Product newproduct = new Product(Integer.parseInt(data[index][0]),data[index][1]);
            newproduct.increaseQuantity(Integer.parseInt(data[index][2])); // the stock quantity is adjusted with the actual amount
            stock.add(newproduct); // each sample Product is loaded to the stock
        }
    }

    /**
     * Add the products which has stock
     * levels below the given amount to the onOrder list, but
     * only if they are not already on the onOrder list.
     * Change the quantity field in onOrder list to the difference between the current
     * number on hand and the stockTarget
     */
    public void orderStockProducts(int stockTarget)
    {
        int index_1,index_2;
        // using a for loop to iterate through stock to find products with quantities lower than the target
        for(index_1=0;index_1<stock.size();index_1++){
            if(stock.get(index_1).getQuantity() < stockTarget){ // if product quantity is lower than stock target
                Product productToOrder = new Product(stock.get(index_1).getID(),stock.get(index_1).getName()); // define a new Product object
                int toOrder = stockTarget - stock.get(index_1).getQuantity(); // defining a variable to store quantity to order
                productToOrder.increaseQuantity(toOrder); // update quantity to order
                // using a for loop to check if product is already on order
                for(index_2=0;index_2<onOrder.size();index_2++){
                    if(onOrder.get(index_2).getID()==productToOrder.getID()){ // if there is a match of product already on order
                        if (toOrder > onOrder.get(index_2).getQuantity()){
                            //onOrder.get(index_2).increaseQuantity(-onOrder.get(index_2).getQuantity()); // resetting quantity on order
                            onOrder.get(index_2).increaseQuantity(toOrder - onOrder.get(index_2).getQuantity()); // applying new quantity to order
                        }
                    }
                }
                onOrder.add(productToOrder); // if product isn't on order, it is added there
            }
        }
    }

    /**
     * Return arraylist of all the products which has stock
     * levels below the given amount
     */
    public ArrayList<String> lowStockProducts(int upperLimit)
    {
        ArrayList<String> lowStockArrayList = new ArrayList<>();
        int index;
        for(index=0;index<stock.size();index++){
            if(stock.get(index).getQuantity() < upperLimit){
                lowStockArrayList.add(stock.get(index).getName());
            }
        }
        return lowStockArrayList;
    }

    /**
     * Try to find a product in the stock with the given name.
     *
     * @return The identified product, or null if there is none
     * with a matching name.
     */
    public Product findProduct(String name)
    {
        int index;
        for(index=0;index<stock.size();index++){

            if(stock.get(index).getName().equals(name)){
                return stock.get(index);
            }
        }
        return null;
    }

}
