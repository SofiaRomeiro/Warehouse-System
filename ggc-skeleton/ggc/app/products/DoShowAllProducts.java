package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;

/**
 * Show all products.
 */
class DoShowAllProducts extends Command<WarehouseManager> {

  DoShowAllProducts(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command

    TreeMap<String, String> products = new TreeMap<>(_receiver.showAllProducts());

    for (String p: products.values()){
      _display.addLine(p);
    }
    _display.display();

  }

}
