package org.bahmni.module.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.ModuleActivator;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class ExampleModuleActivator implements ModuleActivator {

  private Log log = LogFactory.getLog(this.getClass());

  @Override
  public void willRefreshContext() {
    log.warn("Will refresh Example Module");
  }

  @Override
  public void contextRefreshed() {
    log.warn("Refreshed Example Module");
  }

  @Override
  public void willStart() {
    log.warn("Will start Example Module");

  }

  @Override
  public void started() {
    log.warn("Started Example Module");
  }

  @Override
  public void willStop() {
    log.warn("Will stop Example Module");
  }

  @Override
  public void stopped() {
    log.warn("Stopped Example Module");
  }
}
