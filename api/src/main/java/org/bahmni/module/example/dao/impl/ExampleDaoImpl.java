package org.bahmni.module.example.dao.impl;

import org.bahmni.module.example.dao.ExampleDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExampleDaoImpl implements ExampleDao {

  private SessionFactory sessionFactory;

  @Autowired
  public ExampleDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }



}
