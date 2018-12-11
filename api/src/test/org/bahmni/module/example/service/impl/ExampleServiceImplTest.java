package org.bahmni.module.example.service.impl;

import org.bahmni.module.example.dao.ExampleDao;
import org.bahmni.module.example.service.OpenERPProductService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openmrs.Patient;
import org.openmrs.api.OrderService;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExampleServiceImplTest {
  @Mock
  private PatientService patientService;
  @Mock
  private ExampleDao exampleDao;
  @Mock
  private OpenERPProductService erpProductService;
  @Mock
  private VisitService visitService;

  private ExampleServiceImpl exampleService;


  @Before
  public void setUp() {
    exampleService = new ExampleServiceImpl(visitService, erpProductService, exampleDao);
  }


  @Test
  @Ignore
  public void shouldTestSomething() throws ParseException {
     Patient  patient = new Patient();
     exampleService.getOrderCosts(patient);
  }



}
