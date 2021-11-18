package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<WarehouseManager> {

  DoShowGlobalBalance(WarehouseManager receiver) {
    super(Label.SHOW_BALANCE, receiver);
  }

  @Override
  public final void execute() throws CommandException {

    double accountant = _receiver.getAccountantBalance();
    double available = _receiver.getAvailableBalance();
    
    //try {
      _display.popup(Message.currentBalance(available, accountant));
    //}
    /*catch (ClassNotFoundException e)  { 
       e.printStackTrace(); 
    }*/
    
  }
}
