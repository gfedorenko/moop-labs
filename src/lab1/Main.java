package lab1;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Main {

    public static String DIR = "results/lab1/";

    public static void main(String[] args) throws Exception {
        new File(DIR).mkdirs();

        example1();
        example2();
        example3();
        example4();

    }

    public static void example1() throws Exception {
        System.out.println("\nExample 1");
        Shop services = new Shop();
        services.addCategory(1, "Chocolate");
        services.addProduct(1, "Milka", "Austria", 30, 1);
        services.addCategory(2, "Chips");
        services.addProduct(2, "Lux", "Ukraine", 20, 2);
        services.addProduct(3, "Pringles", "USA", 70, 2);
        services.saveToFile(DIR + "checkpoint-1.xml");
    }

    public static void example2() throws Exception {
        System.out.println("\nExample 2");
        Shop services = new Shop();
        services.loadFromFile(DIR + "checkpoint-1.xml");
        services.deleteCategory(2);
        services.saveToFile(DIR + "checkpoint-2.xml");
    }

    public static void example3() throws Exception {
        System.out.println("\nExample 3");
        Shop services = new Shop();
        services.loadFromFile(DIR + "checkpoint-1.xml");
        try {
            services.deleteCategory(3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ArrayList<Product> products = services.getProductsByCategory(2);
        assert products.size() == 2;
        assert services.getProduct(2) != null && services.getProduct(3) != null;
        assert products.contains(services.getProduct(2));
        assert products.contains(services.getProduct(3));
        services.deleteProduct(3);
        assert services.getProduct(2) != null && services.getProduct(3) == null;
        products = services.getProductsByCategory(2);
        assert products.size() == 1;
        assert products.contains(services.getProduct(2));
        services.saveToFile(DIR + "checkpoint-3.xml");
    }

    public static void example4() throws Exception {
        System.out.println("\nExample 4");
        Shop services = new Shop();
        services.loadFromFile(DIR + "checkpoint-2.xml");
        try {
            services.deleteProduct(3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        services.loadFromFile(DIR + "checkpoint-1.xml");
        services.getProduct(1).country = "Germany";
        services.getProduct(1).price = 12;
        services.deleteProduct(2);
        services.saveToFile(DIR + "checkpoint-4.xml");
    }
}
