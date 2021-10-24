package ggc.core;

//FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */

   private  Warehouse _warehouse;

  //private static WarehouseManager _singleton;  //if we use Singleton pattern


  //FIXME define other attributes
 

  //FIXME define constructor(s)
  public WarehouseManager() {
    _warehouse = new Warehouse();
  }

  /*private WarehouseManager() {      //if we use Singleton pattern
  }*/


  //FIXME define other methods


  /*public static WarehouseManager getSingleton() {     //if we use Singleton pattern
    if (_singleton==null) {
      _singleton = new WarehouseManager();
    }
    return _singleton;
  }*/

  public int displayDate() {
    return _warehouse.getDate();
  }

  public boolean advanceDate(int days) {
    return _warehouse.advanceDate(days);
  }

  public double getAvailableBalance() {
    return _warehouse.getAvailableBalance();
  }

  public double getAccountantBalance() {
    return _warehouse.getAccountantBalance();
  }

  public boolean hasFilename() {
    return !(_filename.equals(""));
  }

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save(String filename) throws IOException, FileNotFoundException, MissingFileAssociationException {
    //FIXME implement serialization method -> ObjectOut&InStreams ...

    if (!hasFilename()) {
      _filename = filename;
    }

    try (ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
      objOut.writeObject(_warehouse);
    }

    catch (FileNotFoundException fnfe) {
      throw fnfe;
    }

    catch (MissingFileAssociationException mfae) {
      throw mfae;
    }

    catch (IOException e) {
      throw e;
    }

    finally {
      objOut.close();
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  public String openFile(String filename) throws FileOpenFailedException, IOException {

    try {

      Warehouse warehouse;
      ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
      warehouse  = (Warehouse) objIn.readObject();

      _warehouse = warehouse;
      _filename = filename;

    }

    catch (FileOpenFailedException fofe) {
      throw new FileOpenFailedException(filename);
    }

    catch (IOException e) {
      throw e;
    }

    finally {
      objIn.close();
    }
  }


  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException  {
    //FIXME implement serialization method
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException | /* ?? ImportFileException ?? */ /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
