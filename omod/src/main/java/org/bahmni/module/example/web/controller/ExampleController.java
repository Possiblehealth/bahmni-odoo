package org.bahmni.module.example.web.controller;


import org.bahmni.module.example.service.ExampleService;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/example")
public class ExampleController extends BaseRestController {

  private ExampleService exampleService;
  private PatientService patientService;

  @Autowired
  public ExampleController(ExampleService exampleService, PatientService patientService) {
      this.patientService = patientService;
      this.exampleService = exampleService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/patient/{uuid}", produces = "application/json")
  @ResponseBody
  public List getDetails(@PathVariable("uuid") String patientUuid,
                         @RequestParam(value = "startDate", required = false) String startDateString,
                         @RequestParam(value = "endDate", required = false) String endDateString) throws ParseException {
      Patient patient = patientService.getPatientByUuid(patientUuid);
      if (patient == null) {
          throw new RuntimeException("Can not identify Patient");
      }
      return exampleService.getOrderCosts(patient);
  }

}
