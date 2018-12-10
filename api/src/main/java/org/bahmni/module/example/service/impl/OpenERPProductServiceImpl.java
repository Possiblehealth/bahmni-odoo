package org.bahmni.module.example.service.impl;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import org.apache.commons.collections.map.HashedMap;
import org.bahmni.module.example.service.OpenERPProductService;
import org.bahmni.module.example.utils.ExampleProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OpenERPProductServiceImpl implements OpenERPProductService {

    //default values
    private String HOST = "127.0.0.1";
    private int PORT = 8069;
    private String DATABASE = "openerp";
    private String USER = "admin";
    private String PASSWORD = "password";

    private String erpHost() {
        String property = ExampleProperties.getProperty("erp.host");
        return property != null ? property : HOST;
    }

    private int erpPort() {
        String property = ExampleProperties.getProperty("erp.port");
        return property != null ? Integer.valueOf(property) : PORT;
    }

    private String erpDatabase() {
        String property = ExampleProperties.getProperty("erp.database");
        return property != null ? property : DATABASE;
    }

    private String erpUser() {
        String property = ExampleProperties.getProperty("erp.user");
        return property != null ? property : USER;
    }

    private String erpPassword() {
        String property = ExampleProperties.getProperty("erp.password");
        return property != null ? property : PASSWORD;
    }

    private Session getERPSession() {
        return new Session(erpHost(), erpPort(), erpDatabase(), erpUser(), erpPassword());
    }

    public OpenERPProductServiceImpl() {
        ExampleProperties.load();

    }

    @Override
    public Double getCostOfProduct(String uuid) {
        Session openERPSession = getERPSession();
        try {
            openERPSession.startSession();
            ObjectAdapter productModel = openERPSession.getObjectAdapter("product.product");
            FilterCollection filters = new FilterCollection();
            filters.add("uuid","=",uuid);
            RowCollection products = productModel.searchAndReadObject(filters, new String[]{"name","list_price"});
            if ((products != null) && (products.size() > 0)) {
                Object list_price = products.get(0).get("list_price");
                return Double.valueOf(list_price.toString());
            }
        } catch (Exception e) {
            System.out.println("Error while reading data from ERP server:\n\n" + e.getMessage());
        }

        return null;
    }

    @Override
    public Map<String, Double> getListPricesForOrderables(List<String> allOrderableUuids) {
        Session openERPSession = getERPSession();
        Map<String, Double> results = new HashedMap();
        try {
            openERPSession.startSession();
            ObjectAdapter productModel = openERPSession.getObjectAdapter("product.product");
            FilterCollection filters = new FilterCollection();

            for (String orderableUuid : allOrderableUuids) {
                filters.clear();
                filters.add("uuid","=",orderableUuid);
                RowCollection products = productModel.searchAndReadObject(filters, new String[]{"name","list_price"});
                if ((products != null) && (products.size() > 0)) {
                    Object list_price = products.get(0).get("list_price");
                    if ((list_price != null)) {
                        results.put(orderableUuid, Double.valueOf(list_price.toString()));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error while reading data from ERP server:\n\n" + e.getMessage());
        }
        return results;
    }


}
