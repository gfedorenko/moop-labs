package db;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;

public class DbCategory implements Serializable {
    public int code;
    public String name;

    public DbCategory(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public DbCategory(DataInputStream in) throws Exception {
        code = in.readInt();
        name = in.readUTF();
    }

    public void serialize(DataOutputStream out) throws Exception {
        out.writeInt(code);
        out.writeUTF(name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
