package ggc.core;

import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.Collections;

import ggc.core.exception.BadEntryException;
import ggc.core.ProductComparator;

/**
 * Class Warehouse implements a warehouse and is responsible for all business management.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  private Date _date;
  private Balance _balance = new Balance();

  private Map<String, Partner> _partners;
  private Map<String, Product> _products;

  /**
   * Constructor.
   */
  public Warehouse() {
    _date = new Date(0);
    _partners = new TreeMap<>();
    _products = new TreeMap<>();

  }

  /**
   * Returns the date.
   * 
   * @return
   */
  public int getDate() {
    return _date.getDays();
  }

  /**
   * Advance a certain number of days.
   * 
   * @param days
   * @return
   */
  public boolean advanceDate(int days) {
    return _date.advanceDate(days);
  }

  /**
   * Returns available balance.
   * 
   * @return
   */
  public double getAvailableBalance() {
    return _balance.getCurrentAvailable();
  }

  /**
   * Returns account balance.
   * 
   * @return
   */
  public double getAccountantBalance() {
    return _balance.getCurrentAccountant();
  }

  /**
   * Returns a list of all produts in the warehouse, ordered by product id.
   * 
   * @return
   */
  public List<String> showAllProducts() {
    List<String> list = new LinkedList<String>();
    List<Product> toSort = new LinkedList<Product>(_products.values());
    Collections.sort(toSort, new ProductComparator());
    for (Product p : toSort){
      list.add(p.toString());
    }
    return list;
  }

  /*private class ProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
      return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
    }
  }*/

  /**
   * Returns a list of all batches description.
   * 
   * @return
   */
  public List<String> showAllBatches() {

    ArrayList<String> allBatches = new ArrayList<>();
    
    for (Product p : _products.values()) {
      for (Batch b : p.getAllBatches()) {
        allBatches.add(b.toString());
      }
    }

    return allBatches;
  }

  /**
   * Returns a list of all partners.
   * 
   * @return
   */
  public List<String> showAllPartners() {
    LinkedList<String> list = new LinkedList<String>();
    for (Partner p : _partners.values()){
      list.add(p.toString());
    }
    return list;
  }

 
  /**
   * Returns the partner according to the key.
   * 
   * @param key
   * @return
   */
  public String getPartnerById(String key) {
    return _partners.get(key.toLowerCase()).toString();
  }

  /**
   * Add new partner.
   * 
   * @param key
   * @param name
   * @param address
   */
  public void addPartner(String key, String name, String address) {
   
    Partner partner = new Partner(key, name, address);
    if (!hasPartner(key.toLowerCase()))
      _partners.put(key.toLowerCase(), partner);

  }

  /**
   * Checks for the existence of a partner with a given key.
   * 
   * @param key
   * @return
   */
  public boolean hasPartner(String key) {
    return _partners.containsKey(key.toLowerCase());
  }

  /**
   * Add a new simple product.
   * @param id
   */
  public void addSimpleProduct(String id) {
   
    if (!(_products.containsKey(id))) {
      SimpleProduct product = new SimpleProduct(id);
      _products.put(id, product);
    }
  }

  /**
   * 
   * @param txtfile
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {

    String[] recipe;
    String[] component;
    String id;
    String name, address;
    String partner;
    String componentId;
    int quantity = 0;
    double price;
    double alpha;
    int stock = 0;

    try (BufferedReader in = new BufferedReader(new FileReader(txtfile))) {

      
      String line;

      while ((line = in.readLine()) != null) {

        String[] fields = line.split("\\|");

        if (fields[0].equals(Label.PARTNER)) {
          id = fields[1];
          name = fields[2];
          address = fields[3];

          // metodo para adicionar parceiro
          _partners.put(id.toLowerCase(), new Partner(id, name, address));         
        }

        else if (fields[0].equals(Label.BATCH_S)) {
          id = fields[1];
          partner = fields[2];
          price = Double.parseDouble(fields[3]);
          stock = Integer.parseInt(fields[4]);

          // metodo para criar lote de produto simples
          if (!(_products.containsKey(id))) {
            addSimpleProduct(id);
          }
          SimpleProduct product = (SimpleProduct) _products.get(id);
          Partner prtnr = _partners.get(partner.toLowerCase());
          product.addNewBatch(new Batch(price, stock, new SimpleProduct(id), prtnr));

        }

        else if (fields[0].equals(Label.BATCH_M)) {
          id = fields[1];
          partner = fields[2];
          price = Double.parseDouble(fields[3]);
          stock = Integer.parseInt(fields[4]);
          alpha = Double.parseDouble(fields[5]);


          while (fields[5] != null) {
            recipe = fields[5].split("#");

            for (String c: recipe) {
              component = c.split(":");
              componentId = component[0];
              quantity = Integer.parseInt(component[1]);
            }
          }
        }

      }

    }

    catch (IOException e) {
      throw e;
    }

  }

}
