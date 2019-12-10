package db;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Map;

public interface IDbServices extends Remote {
    boolean addCategory(int code, String name) throws Exception;
    DbCategory getCategory(int code) throws Exception;
    ArrayList<DbCategory> getCategories() throws Exception;
    boolean deleteCategory(int code) throws Exception;

    boolean addProduct(int code, String name, String country, int price,
                      int categoryCode) throws Exception;
    boolean updateProduct(int code, Map<String,String> changes) throws Exception;
    DbProduct getProduct(int code) throws Exception;
    ArrayList<DbProduct> getProducts() throws Exception;
    ArrayList<DbProduct> getProductsByCategory(int categoryCode) throws Exception;
    boolean deleteProduct(int code) throws Exception;
}