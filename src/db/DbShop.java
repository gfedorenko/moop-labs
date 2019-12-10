package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class DbShop implements IDbServices {
    private Connection con;
    private Statement stmt;

    public DbShop() {
        con = null;
        stmt = null;
    }

    public void connect(String url, String user, String password) throws Exception {
        if (con != null) throw new Exception("Connection already exists");
        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();
    }

    public void disconnect() throws Exception {
        if (con == null) throw new Exception("There is no active connection");
        con.close();
        con = null;
        stmt = null;
    }

    public boolean addCategory(int code, String name) throws Exception {
        String sql = "INSERT INTO categories (id, name)" +
                "VALUES (" + code + ", '" + name + "')";
        System.out.println(sql);
        try {
            stmt.executeUpdate(sql);
            System.out.println("Successfully added category with code " + code);
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to add category with code " + code);
            return false;
        }
    }

    public DbCategory getCategory(int code) throws Exception {
        String sql = "SELECT id, name FROM categories WHERE id=" + code;
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                return new DbCategory(id, name);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("An error occurred while requesting category with code " + code);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<DbCategory> getCategories() throws Exception {
        ArrayList<DbCategory> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categories.add(new DbCategory(id, name));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("An error occurred while requesting all category");
            System.out.println(e.getMessage());
        }
        return categories;
    }

    public boolean deleteCategory(int code) throws Exception {
        String sql = "DELETE FROM categories WHERE id = " + code;
        try {
            int c = stmt.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Category with code " + code + " successfully deleted!");
                return true;
            } else {
                System.out.println("Category with code " + code + " wasn't found!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting category with code " + code);
            System.out.println(e.getMessage());
            return false;
        }

    }

    public boolean addProduct(int code, String name, String country, int price,
                             int categoryCode) {
        String sql = "INSERT INTO products (id, category_id, name, country, price)" +
                "VALUES (" + code + ", " + categoryCode + ", '" + name + "', '" + country + "', '" + price +  "')";
        try {
            stmt.executeUpdate(sql);
            System.out.println("Successfully added product with code " + code);
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to add product with code " + code);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateProduct(int code, Map<String,String> changes) {
        StringBuilder assignments = new StringBuilder();
        for (Map.Entry change : changes.entrySet()) {
            if (assignments.length() > 0)
                assignments.append(", ");
            assignments.append(change.getKey() + "='" + change.getValue() + "'");
        }

        String sql = "UPDATE products" +
                " SET " + assignments.toString() + " WHERE id=" + code;
        System.out.println(sql);

        try {
            stmt.executeUpdate(sql);
            System.out.println("Successfully updated product with code " + code);
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to update product with code " + code);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public DbProduct getProduct(int code) {
        String sql = "SELECT id FROM products WHERE id=" + code;
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int category_id = rs.getInt("category_id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                int price = rs.getInt("price");

                return new DbProduct(id, category_id, name, country, price);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("An error occured while requesting product with code " + code);
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<DbProduct> getProducts() {
        ArrayList<DbProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int category_id = rs.getInt("category_id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                int price = rs.getInt("price");

                products.add(new DbProduct(id, category_id, name, country, price));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("An error occured while requesting all products");
            System.out.println(e.getMessage());
        }
        return products;
    }

    public ArrayList<DbProduct> getProductsByCategory(int categoryCode) {
        ArrayList<DbProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id=" + categoryCode;
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int category_id = rs.getInt("category_id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                int price = rs.getInt("price");

                products.add(new DbProduct(id, category_id, name, country, price));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("An error occured while requesting products by arline");
            System.out.println(e.getMessage());
        }
        return products;
    }

    public boolean deleteProduct(int code) {
        String sql = "DELETE FROM products WHERE id = " + code;
        try {
            int c = stmt.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Product with code " + code + " successfully deleted!");
                return true;
            } else {
                System.out.println("Product with code " + code + " wasn't found!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("An error occured while deleting product with code " + code);
            System.out.println(e.getMessage());
            return false;
        }
    }
}