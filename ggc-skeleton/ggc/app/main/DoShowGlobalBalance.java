package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.main.Message;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<WarehouseManager> {

  DoShowGlobalBalance(WarehouseManager receiver) {
    super(Label.SHOW_BALANCE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command
    double accountant = _receiver.getAccountantBalance();
    double available = _receiver.getAvailableBalance();

    _display.popup(Message.currentBalance(available, accountant));
       

  }
  
}
