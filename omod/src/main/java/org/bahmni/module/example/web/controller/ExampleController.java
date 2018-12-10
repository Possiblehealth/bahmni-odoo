package org.bahmni.module.example.web.controller;


import org.bahmni.module.example.service.ExampleService;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;


@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/example")
public class ExampleController extends BaseRestController {

  ExampleService exampleService;
  private PatientService patientService;
  private VisitService visitService;

  @Autowired
  public ExampleController(ExampleService exampleService, PatientService patientService, VisitService visitService) {
    this.exampleService = exampleService;
    this.patientService = patientService;
    this.visitService = visitService;
  }



  @RequestMapping(method = RequestMethod.GET, value = "/patient/{uuid}", produces = "application/json")
  @ResponseBody
  public List getDetails(@PathVariable("uuid") String patientUuid,
                         @RequestParam(value = "startDate", required = false) String startDateString,
                         @RequestParam(value = "endDate", required = false) String endDateString) throws ParseException {


    Patient patient = patientService.getPatientByUuid(patientUuid);
    if (patient == null) {
        //throw 404 error
    }

    List<Visit> activeVisits = visitService.getActiveVisitsByPatient(patient);
    List<Order> orders = new ArrayList<>();
    for (Visit activeVisit : activeVisits) {
      activeVisit.getNonVoidedEncounters().stream().forEach(encounter -> orders.addAll(encounter.getOrders()));
    }


    List activeOrders = new ArrayList();
    for (Order order : orders) {
        HashMap<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("name", order.getConcept().getName().getName());
        orderInfo.put("date", order.getDateActivated());
        orderInfo.put("uuid", order.getConcept().getUuid());
        activeOrders.add(orderInfo);

    }

    return activeOrders;
  }

}
