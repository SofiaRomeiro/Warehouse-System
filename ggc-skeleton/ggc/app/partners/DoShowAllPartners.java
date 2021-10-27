package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Show all partners.
 */
class DoShowAllPartners extends Command<WarehouseManager> {

  DoShowAllPartners(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PARTNERS, receiver);
  }

  @Override
  public void execute() throws CommandException {
    //FIXME implement command
    TreeMap<String, String> partners = _receiver.showAllPartners();

    for (String p: partners.values()){
      _display.addLine(p);
    }
    _display.display();

  }

}
