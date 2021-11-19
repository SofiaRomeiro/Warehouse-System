package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import java.util.List;
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

    List<String> partners = new LinkedList<>(_receiver.showAllPartners());

    for (String p : partners) {
      _display.addLine(p);
    }
    _display.display();

  }

}
