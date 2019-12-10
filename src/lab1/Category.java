package lab1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Category {
    public int code;
    public String name;

    public Category(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public Category(Element category) {
        this.code = Integer.parseInt(category.getAttribute("id"));
        this.name = category.getAttribute("name");
    }

    public Element serialize(Document doc) {
        Element category = doc.createElement("category");
        category.setAttribute("id", String.valueOf(this.code));
        category.setAttribute("name", this.name);
        return category;
    }
}
