package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.core.WarehouseManager;
import ggc.app.exception.ImportFileException;

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
    
    try (){
      //FIXME implement command
      

      _display.addLine(_receiver.openFile(filename));
      _display.display();

    } 

    catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());

    } 

    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    catch (FileOpenFailedException fofe) {
      throw new FileOpenFailedException(filename);
    } 

    finally {
      objIn.close();
    }
    
  }

}
