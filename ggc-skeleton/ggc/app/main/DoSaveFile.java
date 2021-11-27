package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

import java.io.IOException;

import ggc.core.WarehouseManager;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  private String fileName;

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
      fileName = request.parse().stringField("answer");
    } else {
      fileName = _receiver.getFilename();
    }

    try {
      _receiver.save(fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
