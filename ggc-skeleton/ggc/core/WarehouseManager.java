package ggc.core;

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

import ggc.app.exception.FileOpenFailedException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.UnkPartnerKeyException;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.NotValidDateException;
import ggc.core.exception.DuplPartnerKeyException;
import ggc.core.exception.UnkProductKeyException;

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

  public WarehouseManager() {
    _warehouse = new Warehouse();
  }

  // Date
  /**
   * 
   * @return
   */
  public int displayDate() {
    return _warehouse.getDate();
  }

  /**
   * 
   * @param days
   * @return
   */
  public void advanceDate(int days) throws NotValidDateException {
    _warehouse.advanceDate(days);
  }

  //Balance
  /**
   * 
   * @return
   */
  public double getAvailableBalance() {
    return _warehouse.getAvailableBalance();
  }

  /**
   * 
   * @return
   */
  public double getAccountantBalance() {
    return _warehouse.getAccountantBalance();
  }

  //File
  /**
   * 
   * @return
   */
  public boolean hasFilename() {
    return !"".equals(_filename);
  }

  public String getFilename() {
    return _filename;
  }

  // Products
  public List<String> showAllProducts() {
    return _warehouse.showAllProducts();
  }

  public List<String> showAllBatches() {
    return _warehouse.showAllBatches();
  }

  public List<String> showBatchesByProduct(String key) throws UnkProductKeyException {
    return _warehouse.showBatchesByProduct(key);
  }

  public List<String> showBatchesByPartner(String key) throws UnkPartnerKeyException {
    return _warehouse.showBatchesByPartner(key);
  }

  // Partner
  public List<String> showAllPartners() {
    return _warehouse.showAllPartners();
  }

  public String showPartner(String key) {
    return _warehouse.getPartnerById(key);
  }

  public List<String> showParterNotifications(String key) {
    return _warehouse.getPartnerNotificationsById(key);
  }

  public void registerPartner(String key, String name, String address) throws DuplPartnerKeyException {
    _warehouse.addPartner(key, name, address);
  }

  public void toggleProductNotifications(String partnerKey, String productKey) throws UnkProductKeyException, UnkPartnerKeyException {
    _warehouse.toggleNotifications(partnerKey, productKey);
  }

  public boolean hasPartner(String key ){
    return _warehouse.hasPartner(key);
  }

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save(String filename) throws IOException, FileNotFoundException {

    if (!hasFilename()) {
      _filename = filename;
    }

    try (ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
      objOut.writeObject(_warehouse);
    } catch (FileNotFoundException fnfe) { 
      throw fnfe; 
    } catch (IOException e) {
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

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException  {

      try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(filename))) {
        Object anObject = objIn.readObject();
        _warehouse = (Warehouse) anObject;
        _filename = filename;
      } catch (ClassNotFoundException cnfe) {
        throw cnfe;
      } catch (IOException e) {
        throw new UnavailableFileException(filename);
      }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
