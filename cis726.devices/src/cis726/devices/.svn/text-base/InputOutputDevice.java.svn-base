package cis726.devices;

import cis726.logging.Logger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jakub Janecek
 */
public class InputOutputDevice extends Device {

  private AtomicInteger inputFood = new AtomicInteger (0);
  private AtomicInteger inputDrink = new AtomicInteger (0);
  private AtomicInteger inputIv = new AtomicInteger (0);
  private AtomicInteger outputSolid = new AtomicInteger (0);
  private AtomicInteger outputLiquid = new AtomicInteger (0);
  private AtomicInteger outputBlood = new AtomicInteger (0);
  private Random r = new Random ();

  public InputOutputDevice (int deviceId, int roomId) throws Exception {
    super (deviceId, roomId);
  }

  public void run () {

    while (!stop) {
      inputFood.addAndGet (r.nextInt (r.nextInt (50) + 1));
      inputDrink.addAndGet (r.nextInt (r.nextInt (50) + 1));
      inputIv.addAndGet (r.nextInt (r.nextInt (50) + 1));
      outputSolid.addAndGet (r.nextInt (r.nextInt (50) + 1));
      outputLiquid.addAndGet (r.nextInt (r.nextInt (50) + 1));
      outputBlood.addAndGet (r.nextInt (r.nextInt (50) + 1));
      producer.sendText (getId () + "#" + inputFood.get () + ";" + inputDrink.get () + ";" + inputIv.get () + ";" +
                         outputSolid.get () + ";" + outputLiquid.get () + ";" + outputBlood.get ());
      globalProducer.sendText (getId () + "#" + inputFood.get () + ";" + inputDrink.get () + ";" + inputIv.get () + ";" +
                               outputSolid.get () + ";" + outputLiquid.get () + ";" + outputBlood.get ());
//      int inputTotal = inputFood.get () + inputDrink.get () + inputIv.get ();
//      int outputTotal = outputSolid.get () + outputLiquid.get () + outputBlood.get ();
//      int inputFoodPercent = Math.round ((float) inputFood.get () / inputTotal * 100);
//      int inputDrinkPercent = Math.round ((float) inputDrink.get () / inputTotal * 100);
//      int inputIVPercent = Math.round ((float) inputIv.get () / inputTotal * 100);
//      int outputSolidPercent = Math.round ((float) outputSolid.get () / outputTotal * 100);
//      int outputLiquidPercent = Math.round ((float) outputLiquid.get () / outputTotal * 100);
//      int outputBloodPercent = Math.round ((float) outputBlood.get () / outputTotal * 100);
//      producer.sendText (getId () + "#" + inputFoodPercent + ";" + inputDrinkPercent + ";" + inputIVPercent + ";" +
//                         outputSolidPercent + ";" + outputLiquidPercent + ";" + outputBloodPercent);
//      globalProducer.sendText (getId () + "#" + inputFoodPercent + ";" + inputDrinkPercent + ";" + inputIVPercent + ";" +
//                               outputSolidPercent + ";" + outputLiquidPercent + ";" + outputBloodPercent);
      try {
        Thread.sleep (60000);
      }
      catch (InterruptedException ex) {
        Logger.logEx ("IODevice interrupted!", ex);
      }
    }
  }

  public int getFood () {
    return inputFood.get ();
  }

  public int getDrink () {
    return inputDrink.get ();
  }

  public int getIv () {
    return inputIv.get ();
  }

  public int getSolid () {
    return outputSolid.get ();
  }

  public int getLiquid () {
    return outputLiquid.get ();
  }

  public int getBlood () {
    return outputBlood.get ();
  }
}