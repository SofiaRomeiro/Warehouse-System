package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

import ggc.core.WarehouseManager;
import ggc.app.exception.FileOpenFailedException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.UnavailableFileException;

import java.io.*;
//FIXME import classes

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    addStringField("filename", Message.openFile());
    //FIXME maybe add command fields
  }

  @Override
  public final void execute() throws CommandException, FileOpenFailedException {

    String filename = stringField("filename");
    
    try {
      //FIXME implement command    
      _receiver.load(filename);

    }       

    catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());
    }

    /*catch (CommandException ce) {
      throw ce;
    } */
    
    catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    
  }

}
