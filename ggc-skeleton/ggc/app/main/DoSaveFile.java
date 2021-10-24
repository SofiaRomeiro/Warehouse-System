package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.main.Message;
//FIXME import classes

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

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

      String filename = requestString(Message.newSaveAs());

      //MYFIXME completar e guardar

    }

    /*else {

    }

    //tem de saber se ja tiver dado um nome ao ficheiro, tem de ser esse nome, se nao tem de perguntar ao user */

    try (_receiver.save(filename)) {

    }

    catch (CommandException ce) {
      throw new CommandException(filename);
    }

  }

}
