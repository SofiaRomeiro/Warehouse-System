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

import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse and is responsible for all business management.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  private Date _date;
  private Balance _balance = new Balance();

  private Map<String, Partner> _partners;
  private Map<String, Product> _products;


  public Warehouse() {
    _date = new Date(0);
    _partners = new TreeMap<>();
    _products = new TreeMap<>();

  }

  public int getDate() {
    return _date.getDays();
  }

  public boolean advanceDate(int days) {
    return _date.advanceDate(days);
  }

  public double getAvailableBalance() {
    return _balance.getCurrentAvailable();
  }

  public double getAccountantBalance() {
    return _balance.getCurrentAccountant();
  }

  public List<Product> getAllProducts() {
    LinkedList<Product> list = new LinkedList<Product>();
    list.addAll(_products.values());
    return list;
  }

  public Map<String, String> showAllProducts() {

    TreeMap<String, String> products = new TreeMap<String,String>();

    for( Product product : getAllProducts())
      products.put(product.getId(), product.toString());

    return products;
  }

  public List<String> showAllBatches() {

    ArrayList<String> allBatches = new ArrayList<>();
    
    for (Product p : _products.values()) {
      for (Batch b : p.getAllBatches()) {
        allBatches.add(b.toString());
      }
    }

    return allBatches;
  }


  public List<Partner> getAllPartners() {
    LinkedList<Partner> list = new LinkedList<Partner>();
    list.addAll(_partners.values());
    return list;
  }

 
  public Map<String, String> showAllPartners() {
    TreeMap<String, String> showPartners = new TreeMap<String,String>();

    for( Partner partner : getAllPartners())
      showPartners.put(partner.getKey(), partner.toString());

    return showPartners;
  }

  public String getPartnerById(String key) {
    return _partners.get(key.toLowerCase()).toString();
  }


  public void addPartner(String key, String name, String address) {
   
    Partner partner = new Partner(key, name, address);
    if (!hasPartner(key.toLowerCase()))
      _partners.put(key.toLowerCase(), partner);

  }

  public boolean hasPartner(String key) {
    return _partners.containsKey(key.toLowerCase());
  }


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

            for (int i = 0; i < recipe.length; i++) {
              component = recipe[i].split(":");
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
