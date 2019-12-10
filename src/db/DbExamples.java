package db;

import java.io.Serializable;
import java.util.HashMap;

public class DbExamples implements Serializable {
    private IDbServices services;

    public DbExamples(IDbServices services) {
        this.services = services;
    }

    public void runAll() throws Exception {
        example1();
        example2();
        example3();
        example4();
    }

    public void example1() throws Exception {
        System.out.println("\nExample 1");

        services.deleteCategory(3);
        services.addCategory(3, "Choco");
        System.out.println(services.getCategories());
        services.deleteCategory(3);
        System.out.println(services.getCategories());
    }

    public void example2() throws Exception {
        System.out.println("\nExample 2");

        System.out.println(services.getProducts());
        services.addProduct(5, "Name1", "Mexico", 100, 1);
        System.out.println(services.getProducts());
        services.deleteProduct(5);
        System.out.println(services.getProducts());
    }

    public void example3() throws Exception {
        System.out.println("\nExample 3");

        System.out.println(services.getProductsByCategory(1));
        System.out.println(services.getProductsByCategory(2));
    }

    public void example4() throws Exception {
        System.out.println("\nExample 4");

        System.out.println(services.getProductsByCategory(1));
        services.updateProduct(1, new HashMap<String, String>() {{
            put("country", "USA");
            put("price", "200");
        }});
        System.out.println(services.getProductsByCategory(1));
        services.updateProduct(1, new HashMap<String, String>() {{
            put("country", "Lagos");
            put("price", "400");
        }});
    }
}
