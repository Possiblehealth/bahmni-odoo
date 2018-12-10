package org.bahmni.module.example.service;

import java.util.List;
import java.util.Map;

public interface OpenERPProductService {
    Double getCostOfProduct(String uuid);
    Map<String,Double> getListPricesForOrderables(List<String > allOrderableUuids);
}
