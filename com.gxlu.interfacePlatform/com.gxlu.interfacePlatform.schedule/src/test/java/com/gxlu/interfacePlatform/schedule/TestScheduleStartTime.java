package com.gxlu.interfacePlatform.schedule;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Assert;

public class TestScheduleStartTime extends TestCase {
  private Schedule schedule;
  private DefaultScheduleManager scheduleManager;

  @Override
  protected void setUp() throws Exception {
    schedule = new Schedule();
    scheduleManager = new DefaultScheduleManager();
    schedule.setNextRunTime(new Date());
    schedule.setRunPeriod(1l);
  }

  public void testLesser() {
    schedule.setNextRunTime(new Date());
    schedule.setNextRunTime(new Date(new Date().getTime() - 10000l));
    Date startTime = schedule.calculateStartTime();
    Assert.assertTrue(startTime.compareTo(new Date()) == 1);
  }

  public void testGreater() {
    schedule.setNextRunTime(new Date(new Date().getTime() + 10000l));
    Date startTime = schedule.calculateStartTime();
    Assert.assertTrue(startTime.compareTo(new Date()) == 1);
  }

  public void testEquals() {
    Date startTime = schedule.calculateStartTime();
    Assert.assertTrue(startTime.compareTo(new Date()) == 1);
  }

}
