package org.bahmni.module.example.service.impl;

import org.bahmni.module.example.dao.ExampleDao;
import org.bahmni.module.example.service.ExampleService;
import org.openmrs.annotation.Authorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExampleServiceImpl implements ExampleService {

  private ExampleDao exampleDao;

  @Autowired
  public ExampleServiceImpl(ExampleDao exampleDao) {
    this.exampleDao = exampleDao;
  }
}
