package org.bahmni.module.example.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.bahmni.module.example.dao.ExampleDao;
import org.bahmni.module.example.service.ExampleService;
import org.bahmni.module.example.service.OpenERPProductService;
import org.openmrs.Concept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExampleServiceImpl implements ExampleService {

  private OpenERPProductService erpProductService;
  private ExampleDao exampleDao;

  @Autowired
  public ExampleServiceImpl(OpenERPProductService erpProductService, ExampleDao exampleDao) {
    this.erpProductService = erpProductService;
    this.exampleDao = exampleDao;
  }

  @Override
  public Double findRelevantProductCost(Concept concept) {
    return erpProductService.getCostOfProduct(concept.getUuid());
  }

  @Override
  public Map<Concept, Double> getPriceOfProducts(List<Concept> allOrderables) {
    List<String> allOrderableUuids = allOrderables.stream().map(concept -> concept.getUuid()).collect(Collectors.toList());
    Map<String, Double> listPricesForOrderables = erpProductService.getListPricesForOrderables(allOrderableUuids);
    Map<Concept, Double> priceListForProducts = new HashedMap();
    for (Concept concept : allOrderables) {
      Double value = listPricesForOrderables.get(concept.getUuid());
      priceListForProducts.put(concept, value);
    }
    return  priceListForProducts;
  }


}
