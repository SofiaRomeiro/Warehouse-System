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
import ggc.core.exception.NotValidDateException;
import ggc.core.exception.UnkPartnerKeyException;
import ggc.core.exception.DuplPartnerKeyException;
import ggc.core.exception.UnkProductKeyException;
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
  private Balance _balance;
  private Map<String, Partner> _partners;
  private Map<String, Product> _products;

  /**
   * Constructor.
   */
  public Warehouse() {
    _date = Date.now();
    _balance = Balance.getBalance();
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
  public void advanceDate(int days) throws NotValidDateException {
    _date.advanceDate(days);
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
   * Returns a list of all batches description by product.
   * 
   * @return
   */

  public List<String> showBatchesByProduct(String key) throws UnkProductKeyException {

    ArrayList<String> batches = new ArrayList<>();    

    if (!(_products.containsKey(key.toLowerCase()))) {
      throw new UnkProductKeyException();        
    }

    Product p = _products.get(key.toLowerCase());
    
    for (Batch b : p.getAllBatches()) {
      batches.add(b.toString());
    }

    return batches;
  }

  public List<String> showBatchesByPartner(String key) throws UnkPartnerKeyException {

    ArrayList<String> batches = new ArrayList<>();

    if (!(_partners.containsKey(key.toLowerCase()))) {
      throw new UnkPartnerKeyException();
    }

    Partner partner = _partners.get(key.toLowerCase());

    for (Batch b : partner.getAllBatches()) {
        batches.add(b.toString());
    }

    return batches;


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

  public boolean hasPartner(String key) {
    return _partners.containsKey(key.toLowerCase());
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

  public List<String> getPartnerNotificationsById(String key) {
    return _partners.get(key.toLowerCase()).getAllNotifications();
  }

  public void toggleNotifications(String partnerKey, String productKey) throws UnkProductKeyException, UnkPartnerKeyException{
    if (!_products.containsKey(productKey.toLowerCase())) {
      throw new UnkProductKeyException();
    }
    if (!_partners.containsKey(partnerKey.toLowerCase())) {
      throw new UnkPartnerKeyException();
    }
    _products.get(productKey.toLowerCase()).toggleNotifications(_partners.get(partnerKey.toLowerCase()));;
  }

  /**
   * Add new partner.
   * 
   * @param key
   * @param name
   * @param address
   */
  public void addPartner(String key, String name, String address) throws DuplPartnerKeyException {

    if (_partners.containsKey(key.toLowerCase())) {
      throw new DuplPartnerKeyException();
    }

    Partner partner = new Partner(key, name, address);
    _partners.put(key.toLowerCase(), partner);
  }


  /**
   * Add a new simple product.
   * @param id
   */
  public void addSimpleProduct(String id) {
   
    if (!(_products.containsKey(id.toLowerCase()))) {
      SimpleProduct product = new SimpleProduct(id);
      _products.put(id.toLowerCase(), product);
    }
  }

  public void addAggregateProduct(String id, Recipe recipe) {
    if (!(_products.containsKey(id.toLowerCase()))) {
      AggregateProduct product = new AggregateProduct(id, recipe);
      _products.put(id.toLowerCase(), product);
    }
  }

  /**
   * 
   * @param txtfile
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {

    String[] components;
    String[] idAndQuantity;
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
          SimpleProduct product = (SimpleProduct) _products.get(id.toLowerCase());
          Partner prtnr = _partners.get(partner.toLowerCase());
          Batch b = new Batch(price, stock, new SimpleProduct(id), prtnr);
          product.addNewBatch(b);

          //adicionar o lote ao parceiro
          Partner p = _partners.get(partner.toLowerCase());
          p.addBatch(b);
        }

        else if (fields[0].equals(Label.BATCH_M)) {
          id = fields[1];
          partner = fields[2];
          price = Double.parseDouble(fields[3]);
          stock = Integer.parseInt(fields[4]);
          alpha = Double.parseDouble(fields[5]);

          Recipe recipe = new Recipe(alpha);

          components = fields[6].split("#"); //contains each part of the division "<id> : <quantity>"          

          for (String component: components) {
            idAndQuantity = component.split(":");   
            componentId = idAndQuantity[0];            
            quantity = Integer.parseInt(idAndQuantity[1]);
            addSimpleProductWithQuantity(componentId, quantity);
            Component c = new Component(quantity, new SimpleProduct(componentId));
            recipe.addComponent(c);
          }
        
          //criar o produto derivado
          if (!(_products.containsKey(id))) { //se ainda nao foi criado
            addAggregateProduct(id, recipe);
          }
          AggregateProduct product = (AggregateProduct) _products.get(id.toLowerCase());
          Partner prtnr = _partners.get(partner.toLowerCase());
          Batch b = new Batch(price, stock, new AggregateProduct(id, recipe), prtnr);
          product.addNewBatch(b);

          //adicionar o lote ao parceiro
          Partner p = _partners.get(partner.toLowerCase());
          p.addBatch(b);

        }

      }

    }

    catch (IOException e) {
      throw e;
    }

  }

  private void addSimpleProductWithQuantity(String id, int quantity) {
   
    if (!(_products.containsKey(id))) {
      SimpleProduct product = new SimpleProduct(id, quantity);
      _products.put(id, product);
    }
  }

}
