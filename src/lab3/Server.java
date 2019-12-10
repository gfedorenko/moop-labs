package lab3;

import db.DbConfigs;
import db.DbShop;
import db.DbCategory;
import db.DbProduct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private ServerSocket server = null;
    private Socket sock = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private DbShop services = new DbShop();

    public void run(int port) throws Exception {
        server = new ServerSocket(port);
        services.connect(DbConfigs.URL, DbConfigs.USER, DbConfigs.PASSWORD);

        while (true) {
            sock = server.accept();

            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());

            while (processQuery()) ;
        }
    }

    public void stop() throws Exception {
        server.close();
        services.disconnect();
    }

    private boolean processQuery() {
        try {
            int oper = in.readInt();
            System.out.println(oper);

            if (oper == OperationTypes.ADD_CATEGORY) {
                int code = in.readInt();
                String name = in.readUTF();
                System.out.println(name);
                out.writeBoolean(services.addCategory(code, name));
            } else if (oper == OperationTypes.QUERY_CATEGORY) {
                int code = in.readInt();
                services.getCategory(code).serialize(out);
            } else if (oper == OperationTypes.LIST_CATEGORIES) {
                System.out.println("hello");
                ArrayList<DbCategory> categories = services.getCategories();
                out.writeInt(categories.size());
                for (DbCategory airline : categories) {
                    airline.serialize(out);
                }
            } else if (oper == OperationTypes.DELETE_CATEGORY) {
                int code = in.readInt();
                out.writeBoolean(services.deleteCategory(code));
            } else if (oper == OperationTypes.ADD_PRODUCT) {
                int code = in.readInt();
                int categoryCode = in.readInt();
                String name = in.readUTF();
                String country = in.readUTF();
                int price = in.readInt();
                out.writeBoolean(services.addProduct(code, name, country, price, categoryCode));
            } else if (oper == OperationTypes.UPDATE_PRODUCT) {
                int code = in.readInt();
                int cnt = in.readInt();
                Map<String, String> changes = new HashMap<>();
                for (int i = 0; i < cnt; ++i) {
                    changes.put(in.readUTF(), in.readUTF());
                }
                out.writeBoolean(services.updateProduct(code, changes));
            } else if (oper == OperationTypes.QUERY_PRODUCT) {
                int code = in.readInt();
                services.getProduct(code).serialize(out);
            } else if (oper == OperationTypes.LIST_PRODUCTS) {
                ArrayList<DbProduct> products = services.getProducts();
                out.writeInt(products.size());
                for (DbProduct product : products) {
                    product.serialize(out);
                }
            } else if (oper == OperationTypes.LIST_PRODUCTS_BY_CATEGORY) {
                int code = in.readInt();
                ArrayList<DbProduct> products = services.getProductsByCategory(code);
                out.writeInt(products.size());
                for (DbProduct product : products) {
                    product.serialize(out);
                }
            } else if (oper == OperationTypes.DELETE_PRODUCT) {
                int code = in.readInt();
                out.writeBoolean(services.deleteProduct(code));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        Server srv = new Server();
        srv.run(12345);
        srv.stop();
    }

}
