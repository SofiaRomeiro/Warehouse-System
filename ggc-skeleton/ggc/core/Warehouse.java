package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import ggc.core.exception.BadEntryException;
import ggc.core.Label;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  // FIXME define attributes
  private Date _date;
  private Balance _balance = new Balance();


  // FIXME define contructor(s)
  public Warehouse() {

  }


  public Warehouse(int date) {
    _date = new Date(date);
  }
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

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    //FIXME implement method

    String[] fields;
    String[] recipe;
    String[] component;
    String id;
    String name, address;
    String partner;
    String componentId;
    int quantity = 0;
    double price = 0.00;
    double alpha = 0.00;
    int stock = 0;

    try (BufferedReader in = new BufferedReader(new FileReader(txtfile))) {

      String line = in.readLine();

      while (line != null) {

        fields = line.split("|");

        if (fields[0].equals(Label.PARTNER)) {
          id = fields[1];
          name = fields[2];
          address = fields[3];

          // metodo para adicionar parceiro

        }

        else if (fields[0].equals(Label.BATCH_S)) {
          id = fields[1];
          partner = fields[2];
          price = Double.parseDouble(fields[3]);
          stock = Integer.parseInt(fields[4]);

          // metodo para criar lote de produto simples

        }

        else {
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
