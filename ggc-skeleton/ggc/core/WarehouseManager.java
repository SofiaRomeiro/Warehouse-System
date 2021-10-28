package ggc.core;

//FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.lang.Object;

import ggc.app.exception.FileOpenFailedException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
   private Warehouse _warehouse;

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

  // Date
  public int displayDate() {
    return _warehouse.getDate();
  }

  public boolean advanceDate(int days) {
    return _warehouse.advanceDate(days);
  }

  //Balance
  public double getAvailableBalance() {
    return _warehouse.getAvailableBalance();
  }

  public double getAccountantBalance() {
    return _warehouse.getAccountantBalance();
  }

  //File
  public boolean hasFilename() {
    return !(_filename.equals(""));
  }

  public String getFilename() {
    return _filename;
  }

  // Products
  public TreeMap<String, String> showAllProducts() {
    return _warehouse.showAllProducts();
  }

  public ArrayList<String> showAllBatches() {
    return _warehouse.showAllBatches();
  }

  // Partner
  public TreeMap<String, String> showAllPartners() {
    return _warehouse.showAllPartners();
  }

  public String showPartner(String key) {
    return _warehouse.getPartnerById(key);
  }

  public void registerPartner(String key, String name, String address) {
    _warehouse.addPartner(key, name, address);
  }
//add no app
  public boolean duplicatePartnerKey(String key ){
    return _warehouse.hasPartner(key);
  }

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save(String filename) /*throws FileNotFoundException*/ {
    //FIXME implement serialization method -> ObjectOut&InStreams ...

    if (!hasFilename()) {
      _filename = filename;
    }

    try (ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
      objOut.writeObject(_warehouse);
    }

    /*catch (FileNotFoundException fnfe) {
      throw fnfe;
    } */

    catch (IOException e) {
      e.printStackTrace();
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
    save(filename);
  }

  public String openFile(String filename) throws FileOpenFailedException, IOException, ClassNotFoundException{

    try {

      Warehouse warehouse;
      ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
      warehouse  = (Warehouse) objIn.readObject();

      _warehouse = warehouse;
      _filename = filename;

    }

    /*catch (FileOpenFailedException fofe) {
      throw new FileOpenFailedException(filename);
    } */

    catch (ClassNotFoundException cnfe) {
      throw cnfe;
    }

    catch (IOException e) {
      throw e;
    }

    return filename; // ??????????????????

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
    } catch (IOException | BadEntryException /*|  ?? ImportFileException ?? */ /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
