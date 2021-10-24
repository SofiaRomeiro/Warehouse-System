package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  // FIXME define attributes
  private Date _date;
  private Balance _balance;


  // FIXME define contructor(s)
  public Warehouse(int date) {
    _date = new Date(date);
  }
  // FIXME define methods

  public int getDate() {
    return _date.getDays();
  }

  public boolean advanceDate(int days) {
    return _date.advanceDate(days);
  }

  public double getAvailableBalance() {
    return _balance.getCurrentAvailable();
  }

  public double getAccountantBalance() {
    return _balance.getCurrentAccountant();
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    //FIXME implement method
    
  }

}
