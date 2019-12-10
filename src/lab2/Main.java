package lab2;

import db.DbShop;
import db.DbConfigs;
import db.DbExamples;

public class Main {
    public static void main(String[] args) throws Exception {
        DbExamples examples = new DbExamples(getServicesInstance());
        examples.runAll();
    }

    public static DbShop getServicesInstance() throws Exception {
        DbShop services = new DbShop();
        services.connect(DbConfigs.URL, DbConfigs.USER, DbConfigs.PASSWORD);
        return services;
    }
}
