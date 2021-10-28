package ggc.app.main;


import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import ggc.core.WarehouseManager;
import ggc.app.main.Message;
//FIXME import classes

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  String filename;  

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command and create a local Form

    if (!(_receiver.hasFilename())) {

      /*Form form = new Form();
      form.addStringField("filename", Message.saveAs());
      form.parse();
      String filename = form.stringField("filename") */

      //String filename = requestString(Message.newSaveAs());

      Form request = new Form();
      request.addStringField("answer", Message.newSaveAs());
      filename = request.parse().stringField("answer");

      //MYFIXME completar e guardar

    }

    else {
      filename = _receiver.getFilename();
    }

    _receiver.save(filename);

  }

}
