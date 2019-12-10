package lab3;

import db.DbCategory;
import db.DbProduct;
import db.IDbServices;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class SocketServices implements IDbServices {
    private Socket sock = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;

    public SocketServices(String ip, int port) throws IOException {
        sock = new Socket(ip, port);
        in = new DataInputStream(sock.getInputStream());
        out = new DataOutputStream(sock.getOutputStream());
    }

    public void disconnect() throws IOException {
        sock.close();
    }

    @Override
    public boolean addCategory(int code, String name) throws Exception {
        System.out.println("hehhedbcikbehdclsjb");
        out.writeInt(OperationTypes.ADD_CATEGORY);
        out.writeInt(code);
        out.writeUTF(name);
        return in.readBoolean();
    }

    @Override
    public DbCategory getCategory(int code) throws Exception {
        out.writeInt(OperationTypes.QUERY_CATEGORY);
        out.writeInt(code);
        return new DbCategory(in);
    }

    @Override
    public ArrayList<DbCategory> getCategories() throws Exception {
        out.writeInt(OperationTypes.LIST_CATEGORIES);
        ArrayList<DbCategory> categories = new ArrayList<>();
        int cnt = in.readInt();
        for (int i = 0; i < cnt; ++i) {
            categories.add(new DbCategory(in));
        }
        return categories;
    }

    @Override
    public boolean deleteCategory(int code) throws Exception {
        out.writeInt(OperationTypes.DELETE_CATEGORY);
        out.writeInt(code);
        return in.readBoolean();
    }

    @Override
    public boolean addProduct(int code, String name, String country, int price, int categoryCode) throws Exception {
        out.writeInt(OperationTypes.ADD_PRODUCT);
        out.writeInt(code);
        out.writeInt(categoryCode);
        out.writeUTF(name);
        out.writeUTF(country);
        out.writeInt(price);
        return in.readBoolean();
    }

    @Override
    public boolean updateProduct(int code, Map<String, String> changes) throws Exception {
        out.writeInt(OperationTypes.UPDATE_PRODUCT);
        out.writeInt(code);
        out.writeInt(changes.size());
        for (Map.Entry entry : changes.entrySet()) {
            out.writeUTF((String)entry.getKey());
            out.writeUTF((String)entry.getValue());
        }
        return in.readBoolean();
    }

    @Override
    public DbProduct getProduct(int code) throws Exception {
        out.writeInt(OperationTypes.QUERY_PRODUCT);
        out.writeInt(code);
        return new DbProduct(in);
    }

    @Override
    public ArrayList<DbProduct> getProducts() throws Exception {
        out.writeInt(OperationTypes.LIST_PRODUCTS);
        ArrayList<DbProduct> products = new ArrayList<>();
        int cnt = in.readInt();
        for (int i = 0; i < cnt; ++i) {
            products.add(new DbProduct(in));
        }
        return products;
    }

    @Override
    public ArrayList<DbProduct> getProductsByCategory(int airlineCode) throws Exception {
        out.writeInt(OperationTypes.LIST_PRODUCTS_BY_CATEGORY);
        out.writeInt(airlineCode);
        ArrayList<DbProduct> products = new ArrayList<>();
        int cnt = in.readInt();
        for (int i = 0; i < cnt; ++i) {
            products.add(new DbProduct(in));
        }
        return products;
    }

    @Override
    public boolean deleteProduct(int code) throws Exception {
        out.writeInt(OperationTypes.DELETE_PRODUCT);
        out.writeInt(code);
        return in.readBoolean();
    }
}
