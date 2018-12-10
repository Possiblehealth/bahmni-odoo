package org.bahmni.module.example.service;

import org.openmrs.Concept;

import java.util.List;
import java.util.Map;

public interface ExampleService {
    Double findRelevantProductCost(Concept concept);
    Map<Concept, Double> getPriceOfProducts(List<Concept> allOrderables);
}
