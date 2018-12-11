package org.bahmni.module.example.service.impl;

import org.bahmni.module.example.utils.ExampleProperties;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;


public class OpenERPProductServiceImplTest {

    @Test
    public void getListPricesForOrderables() throws Exception {
        Properties erpProperties = new Properties();
        erpProperties.put("erp.host", "192.168.33.11");
        erpProperties.put("erp.port", "8069");
        erpProperties.put("erp.database", "openerp");
        erpProperties.put("erp.user", "admin");
        erpProperties.put("erp.password", "password");
        ExampleProperties.initalize(erpProperties);
        OpenERPProductServiceImpl erpProductService = new OpenERPProductServiceImpl();
        //List<String> productUuids = Arrays.asList("38752a31-5b23-4957-ae0c-ecdbc6b0a489", "6ad082b1-2783-4783-9c11-5cc63cf3a1b0", "ed86eff4-56a2-471f-beb1-f0e50c943d83", "7574c69b-1133-4805-bc31-64635f40c3c0");
        List<String> productUuids = Arrays.asList("4ee6f5af-68c6-4a78-a675-a9ebfce1f625");
        Map<String, Double> listPricesForOrderables = erpProductService.getListPricesForOrderables(productUuids);
        System.out.println(listPricesForOrderables);
    }

}