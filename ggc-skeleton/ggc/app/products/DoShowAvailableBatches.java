package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import java.util.List;
import java.util.ArrayList;

/**
 * Show available batches.
 */
class DoShowAvailableBatches extends Command<WarehouseManager> {

  DoShowAvailableBatches(WarehouseManager receiver) {
    super(Label.SHOW_AVAILABLE_BATCHES, receiver);
  }

  @Override
  public final void execute() throws CommandException {

    List<String> batches = new ArrayList<>(_receiver.showAllBatches());

    //try {
      for (String b: batches){
            _display.addLine(b);
      }
      _display.display();
    //}
    /*catch (ClassNotFoundException e)  { 
       e.printStackTrace(); 
    }*/

    
  }
}
