package db;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbProduct implements Serializable {
    public final static String DATE_FORMAT = "YYYY-MM-dd HH:MM:SS";

    public int code;
    public int category_id;
    public String name;
    public String country;
    public int price;

    private static final DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    public DbProduct(int code, int category_id, String name, String country, int price) {
        this.code = code;
        this.category_id = category_id;
        this.name = name;
        this.country = country;
        this.price = price;
    }


    public DbProduct(DataInputStream in) throws Exception {
        code = in.readInt();
        category_id = in.readInt();
        name = in.readUTF();
        country = in.readUTF();
        price = in.readInt();
    }

    public void serialize(DataOutputStream out) throws Exception {
        out.writeInt(code);
        out.writeInt(category_id);
        out.writeUTF(name);
        out.writeUTF(country);
        out.writeInt(price);
    }


    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return "Product{" +
                "code=" + code +
                ", category_id=" + category_id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
