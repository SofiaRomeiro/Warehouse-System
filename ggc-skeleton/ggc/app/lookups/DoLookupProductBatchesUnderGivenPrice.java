package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
import java.util.ArrayList;

import ggc.core.WarehouseManager;

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addRealField("price", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    Double price = realField("price");
    List<String> batchesUnderPrice = new ArrayList<>(_receiver.showBatchesUnderPrice(price));

    for (String b : batchesUnderPrice) {
        _display.addLine(b);
    }
      _display.display();

  }

}
