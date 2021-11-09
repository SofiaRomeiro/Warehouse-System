package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
import java.util.ArrayList;

import ggc.core.WarehouseManager;

import ggc.app.lookups.Message;
//FIXME import classes

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    //FIXME add command fields
    addRealField("price", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    //FIXME implement command
    Double price = realField("price");
    ArrayList<String> batchesUnderPrice = new ArrayList<>(_receiver.showBatchesUnderPrice(price));

    for (String b : batchesUnderPrice) {
        _display.addLine(b);
    }
      _display.display();

  }

}
