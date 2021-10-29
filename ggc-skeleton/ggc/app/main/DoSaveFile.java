package ggc.app.main;


import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import ggc.core.WarehouseManager;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  String filename;  

  /**
   * 
   * @param receiver
   */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {

    if (!(_receiver.hasFilename())) {

      Form request = new Form();
      request.addStringField("answer", Message.newSaveAs());
      filename = request.parse().stringField("answer");
    }
    else {
      filename = _receiver.getFilename();
    }

    _receiver.save(filename);
  }
}
