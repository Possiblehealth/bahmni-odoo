package org.bahmni.module.example.web.controller;


import org.bahmni.module.example.service.ExampleService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/example")
public class ExampleController extends BaseRestController {

  ExampleService exampleService;

  @Autowired
  public ExampleController(ExampleService exampleService) {
    this.exampleService = exampleService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/patient/{uuid}", produces = "application/json")
  @ResponseBody
  public List<Object> getDetails(@PathVariable("uuid") String patientUuid,
                                          @RequestParam(value = "startDate", required = false) String startDateString,
                                          @RequestParam(value = "endDate", required = false) String endDateString) throws ParseException {
    return null;
  }

}
