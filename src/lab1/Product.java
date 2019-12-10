package lab1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {
    public final static String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    public int code;
    public String name;
    public String country;
    public int price;
    public Category category;

    private static final DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    public Product(int code, String name, String country, int price,
                  Category category) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.price = price;
        this.category = category;
    }

    public Product(Element product, Category category) throws ParseException {
        this.code = Integer.parseInt(product.getAttribute("id"));
        this.name = product.getAttribute("name");
        this.country = product.getAttribute("country");
        this.price = Integer.parseInt(product.getAttribute("price"));
        this.category = category;
    }

    public Element serialize(Document doc) {
        Element product = doc.createElement("product");
        product.setAttribute("id", String.valueOf(this.code));
        product.setAttribute("name", this.name);
        product.setAttribute("country", this.country);
        product.setAttribute("price", String.valueOf(this.price));
        return product;
    }
}
