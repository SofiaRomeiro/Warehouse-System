package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import ggc.core.exception.BadEntryException;
import ggc.core.Label;
import ggc.core.Product.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  // FIXME define attributes
  private Date _date;
  private Balance _balance = new Balance();

  private Map<String, Partner> _partners;
  private Map<String, Product> _products;




  // FIXME define contructor(s)
  public Warehouse() {
    _date = new Date(0);
    _partners = new TreeMap<>();
    _products = new TreeMap<>();

  }

/*
  public Warehouse(int date) {
    _date = new Date(date);
    _partners = new TreeMap<>();
  } */

  // FIXME define methods

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


  public LinkedList<Product> getAllProducts() {
    //MYFIXME implementar metodo
    LinkedList<Product> list = new LinkedList<Product>();
    list.addAll(_products.values());
    return list;
  }

  public TreeMap<String, String> showAllProducts() {
    //MYFIXME por implementar

    TreeMap<String, String> products = new TreeMap<String,String>();

    for( Product product : getAllProducts())
    products.put(product.getId(), product.toString());

    return products;
  }



  public String getAllBatches() {
    //MYFIXME por implementar
    // adicionar um comparator, do genero:
    // List<Batches> tmp = <criar nova lista ou wtv com todos os lotes>
    // Collections.sort(tmp, new BatchesComparator())
    return "aa";
  }

  /*
  private static class BatchesComparator implements Comparator<Batch> {
    @Override
    public int compare(Batch b1, Batch b2) {

      if (b1.getID().compareTo(b2.getId()) != 0) {
        return b1.getID().compareTo(b2.getName());
      }
      else {

        if (b1.getPartner().compareTo(b2.getPartner()) != 0) {
          return b1.getID().compareTo(b2.getName());
        } 
        else {

          if (b1.getPrice() != b2.getPrice()) {
            return b1.getPrice() - b2.getPrice();
          }
          else {
            return b1.getStock() - b2.getStock();
          }

        }

      }

    }
  }*/

  public LinkedList<Partner> getAllPartners() {
    //MYFIXME implementar metodo
    LinkedList<Partner> list = new LinkedList<Partner>();
    list.addAll(_partners.values());
    return list;
  }

  /*public String showAllPartners() {
    TreeMap<String, String> partners = new TreeMap<String,String>();
    String displayText = new String();

    for( Partner partner : getAllPartners())
      partners.put(partner.getKey(), partner.toString());

    for(String s : partners.values())
      displayText += s;

    return displayText;
  }*/

  public TreeMap<String, String> showAllPartners() {
    TreeMap<String, String> partners = new TreeMap<String,String>();

    for( Partner partner : getAllPartners())
      partners.put(partner.getKey(), partner.toString());

    return partners;
  }

  public String getPartnerById(String key) {
    //MYFIXME por implementar
    return _partners.get(key).toString();
  }

  public void addPartner(String key, String name, String address) {
    // criarParceiro(id, name, address)
    // adicionar à collection de parceiros

    Partner partner = new Partner(key, name, address);
    if (!hasPartner(partner.getKey()))
      _partners.put(partner.getKey(), partner);

  }

  public boolean hasPartner( String key) {
    return _partners.containsKey(key);
  }



  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    //FIXME implement method

    //String[] fields;
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
          _partners.put(id, new Partner(id, name, address));         
        }

        else if (fields[0].equals(Label.BATCH_S)) {
          id = fields[1];
          partner = fields[2];
          price = Double.parseDouble(fields[3]);
          //price = Double.valueOf(fields[3]);
          stock = Integer.parseInt(fields[4]);
          //stock = Double.valueOf(fields[4]);

          // metodo para criar lote de produto simples

        }

        else {
          id = fields[1];
          partner = fields[2];
          price = Double.parseDouble(fields[3]);
          //price = Double.valueOf(fields[3]);
          stock = Integer.parseInt(fields[4]);
          //stock = Integer.valueOf(fields[4]);
          alpha = Double.parseDouble(fields[5]);
          //alpha = Double.valueOf(fields[5]);          

          while (fields[5] != null) {
            recipe = fields[5].split("#");

            for (int i = 0; i < recipe.length; i++) {
              component = recipe[i].split(":");

              componentId = component[0];
              quantity = Integer.parseInt(component[1]);

            } 

            // criar novo componente da receita e ir acrescentando a um array
            // do genero:
            // newComponent = new Component(componentId, quantity);
            // ArrayList<Component> components = new ArrayList<>();  --> usar isto como variavel, declarar junto das outras
            // components.add(newComponent)
          }

          //no fim do 1º while, criar uma nova receita que contém o array de componentes já existente 

        }

      }

    }

    catch (IOException e) {
      throw e;
    }

  }

}
