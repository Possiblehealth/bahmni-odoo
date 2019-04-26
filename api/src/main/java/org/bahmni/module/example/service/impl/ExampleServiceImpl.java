package org.bahmni.module.example.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.bahmni.module.example.dao.ExampleDao;
import org.bahmni.module.example.service.ExampleService;
import org.bahmni.module.example.service.OpenERPProductService;
import org.openmrs.*;
import org.openmrs.api.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExampleServiceImpl implements ExampleService {

    private VisitService visitService;
    private OpenERPProductService erpProductService;

    @Autowired
    public ExampleServiceImpl(VisitService visitService, OpenERPProductService erpProductService) {
      this.visitService = visitService;
      this.erpProductService = erpProductService;
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

    @Override
    public List<Map<String, Object>> getOrderCosts(Patient patient) {
        List<Visit> activeVisits = visitService.getActiveVisitsByPatient(patient);
        List<Order> orders = new ArrayList<>();
        for (Visit activeVisit : activeVisits) {
           activeVisit.getNonVoidedEncounters().stream().forEach(encounter -> orders.addAll(encounter.getOrders()));
        }
        List<String> orderableIdentifiers = orders.stream().map(order -> getIdentiferForOrderable(order)).collect(Collectors.toList());
        Map<String, Double> listPricesForOrderables = erpProductService.getListPricesForOrderables(orderableIdentifiers);
        List<Map<String, Object>> activeOrders = new ArrayList();
        for (Order order : orders) {
            HashMap<String, Object> orderInfo = new HashMap<>();
            orderInfo.put("name", getOrderableName(order));
            orderInfo.put("date", order.getDateActivated());
            String identiferForOrderable = getIdentiferForOrderable(order);
            orderInfo.put("uuid", identiferForOrderable);
            orderInfo.put("cost", listPricesForOrderables.get(identiferForOrderable));
            if (order instanceof DrugOrder) {
              DrugOrder drugOrder = (DrugOrder) order;
              DosingInstructions dosingInstructions = drugOrder.getDosingInstructionsInstance();
              //we will ofcourse need to calculate the dose * duration * durationUnits
            }
            activeOrders.add(orderInfo);
        }
        return activeOrders;
    }

    private String getOrderableName(Order order) {
        if (order instanceof  DrugOrder) {
            DrugOrder drugOrder = (DrugOrder) order;
            return drugOrder.getDrug() != null ? drugOrder.getDrug().getName() : drugOrder.getConcept().getName().getName();
        } else {
            return order.getConcept().getName().getName();
        }

    }

    private String getIdentiferForOrderable(Order order) {
        if (order instanceof  DrugOrder) {
            DrugOrder drugOrder = (DrugOrder) order;
            return drugOrder.getDrug() != null ? drugOrder.getDrug().getUuid() : drugOrder.getConcept().getUuid();
        } else {
            return order.getConcept().getUuid();
        }
    }
}
