package cis726.jms.test;

import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author Jakub Janecek
 */
public class Main {

  public Main () {
  }

  public static void main (String[] args) {
    System.out.println ("JmsSupportTest");
    results (JUnitCore.runClasses (JmsSupportTest.class));
    System.exit (0);
  }

  public static void results (Result result) {
    System.out.println ("SUCCESS: " + result.wasSuccessful ());
    System.out.println ("FAILURES: " + result.getFailureCount ());
    System.out.println ("RUN TIME: " + result.getRunTime ());
    List<Failure> list = result.getFailures ();
    for (Failure failure : list) {
      System.out.println (failure.getTestHeader () + "\t" + failure.getMessage ());
      System.out.println ("\t" + failure.getDescription ());
      System.out.println ("\t" + failure.getTrace ());
    }
    System.out.println ("");
    System.out.println ("");
  }
}