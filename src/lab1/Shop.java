package lab1;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shop {
    private ArrayList<Category> categories;
    private ArrayList<Product> products;

    private DocumentBuilderFactory dbf;

    public Shop() throws SAXException {
        categories = new ArrayList<>();
        products = new ArrayList<>();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema s = sf.newSchema(new File("lab1-Shop.xsd"));

        dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setSchema(s);
    }

    public void saveToFile(String filename) throws Exception {
        DocumentBuilder db = null;
        Document doc = null;
        db = dbf.newDocumentBuilder();

        doc = db.newDocument();

        Element root = doc.createElement("Shop");
        doc.appendChild(root);

        Map<Category, Element> CategoryElMap = new HashMap<>();

        for (Category Category : categories) {
            Element CategoryElement = Category.serialize(doc);
            root.appendChild(CategoryElement);
            CategoryElMap.put(Category, CategoryElement);
        }

        for (Product product : products) {
            Element productElement = product.serialize(doc);
            CategoryElMap.get(product.category).appendChild(productElement);
        }

        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(filename));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, fileResult);
    }

    public void loadFromFile(String filename) throws Exception {
        categories.clear();
        products.clear();

        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();

        Document doc = null;
        doc = db.parse(new File(filename));

        Element root = doc.getDocumentElement();
        if (root.getTagName().equals("Shop")) {
            NodeList listCategories = root.getElementsByTagName("category");

            for (int i = 0; i < listCategories.getLength(); i++) {
                Element categoryElement = (Element) listCategories.item(i);
                Category category = new Category(categoryElement);
                categories.add(category);

                NodeList listProducts = categoryElement.getElementsByTagName("product");

                for (int j = 0; j < listProducts.getLength(); j++) {
                    Element productElement = (Element) listProducts.item(j);
                    Product product = new Product(productElement, category);
                    products.add(product);
                }
            }
        }
    }

    public void addCategory(int code, String name) throws Exception {
        if (getCategory(code) != null) throw new Exception("Category with code " + code + " already exists");
        categories.add(new Category(code, name));
    }

    public Category getCategory(int code) {
        System.out.println(code);
        System.out.println(categories);
        for (Category category : categories) {
            if (category.code == code) return category;
        }
        return null;
    }

    public Category getCategoryInd(int index) {
        return categories.get(index);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public int countCategories() {
        return categories.size();
    }

    public void deleteCategory(int code) throws Exception {
        Category category = getCategory(code);
        if (category == null) throw new Exception("Cannot delete airline with code " + code + " as it doesn't exist");
        products.removeIf(f -> f.category == category);
        categories.remove(category);
    }

    public void addProduct(int code, String name, String country, int price,
                          int categoryCode) throws Exception {
        if (getProduct(code) != null) throw new Exception("Product with code " + code + " already exists");

        Category airline = getCategory(categoryCode);
        if (airline == null)
            throw new Exception("Cannot add product with code " + code + " because airline with code " + categoryCode + " doesn't exist");

        DateFormat df = new SimpleDateFormat(Product.DATE_FORMAT);

        Product product = new Product(code, name, country, price,
                airline);

        products.add(product);
    }

    public Product getProduct(int code) {
        for (Product product : products) {
            if (product.code == code) return product;
        }
        return null;
    }

    public Product getProductInd(int index) {
        return products.get(index);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getProductsByCategory(int airlineCode) {
        Category airline = getCategory(airlineCode);
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.category == airline) {
                result.add(product);
            }
        }
        return result;
    }

    public int countProducts() {
        return products.size();
    }

    public void deleteProduct(int code) throws Exception {
        Product product = getProduct(code);
        if (product == null) throw new Exception("Cannot delete product with code " + code + " as it doesn't exist");
        products.remove(product);
    }
}
