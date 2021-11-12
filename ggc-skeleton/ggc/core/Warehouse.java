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
import ggc.core.exception.UnaProductException;
import ggc.core.exception.UnkPartnerKeyException;
import ggc.core.exception.DuplPartnerKeyException;
import ggc.core.exception.UnkProductKeyException;
import ggc.core.exception.UnkTransactionKeyException;


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

  private static Date _date;
  private Balance _balance;
  private int _transactionsIds;
  private Map<String, Partner> _partners;
  private Map<String, Product> _products;
  private List<Transaction> _transations;
  private List<Notification> _notifications;

  /**
   * Constructor.
   */
  public Warehouse() {
    _date = Date.now();
    _balance = Balance.getBalance();
    _transactionsIds = 0;
    _partners = new TreeMap<>();
    _products = new TreeMap<>();
    _transations = new ArrayList<>();
    _notifications = new ArrayList<>();
  }

  /**
   * Returns the date.
   * 
   * @return
   */
  public int getDate() {
    return _date.getDate();
  }

  /**
   * Advance a certain number of days.
   * 
   * @param days
   * @return
   */
  public void advanceDate(int days) throws NotValidDateException {
    _date = _date.advance(days);
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

  /**
   * Returns a list of all batches by partner.
   * 
   * @return
   */
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
   * Returns a list of all batches under given price.
   * 
   * @return
   */
  public List<String> showBatchesUnderPrice(double price) {

    ArrayList<String> batchesUnderPrice = new ArrayList<>();

    
    for (Product p : _products.values()) {
      for (Batch b : p.getAllBatches()) {
        if (b.getPrice() < price) {
          batchesUnderPrice.add(b.toString());
        }        
      }
    }

    return batchesUnderPrice;

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

  public void toggleNotifications(String partnerKey, String productKey) throws UnkProductKeyException, UnkPartnerKeyException {
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

    for (Product p: _products.values()) {
      p.toggleNotifications(partner);
    }
  }

  public List<String> showAcquisitionTransactionByPartner(String key) throws UnkPartnerKeyException {
    
    ArrayList<String> transactions = new ArrayList<>();

    if (!_partners.containsKey(key.toLowerCase())) {
      throw new UnkPartnerKeyException();
    }

    Partner partner = _partners.get(key.toLowerCase());

    for (Transaction t : partner.getAllTransactions()) {
      if (t instanceof Acquisition)
      transactions.add(t.toString());
    }
    return transactions;
  }


  /**
   * Add a new simple product.
   * @param id
   */
  public void addSimpleProduct(String id) {
   
    if (!(_products.containsKey(id.toLowerCase()))) {
      Product product = new SimpleProduct(id);

      _products.put(id.toLowerCase(), product);
      for (Partner p : _partners.values()) {
        product.toggleNotifications(p);
      }  
    }
  }

  private void addSimpleProductWithQuantity(String id, int quantity, List<Observer> obs) {
   
    if (!(_products.containsKey(id.toLowerCase()))) {
      SimpleProduct product = new SimpleProduct(id, quantity, obs);
      _products.put(id.toLowerCase(), product);

      for (Partner p : _partners.values()) {
        product.toggleNotifications(p);
      } 
    }    
  }

  public void addAggregateProduct(String id, Recipe recipe) {
    if (!(_products.containsKey(id.toLowerCase()))) {
      AggregateProduct product = new AggregateProduct(id, recipe); 
      _products.put(id.toLowerCase(), product);

      for (Partner p : _partners.values()) {
        product.toggleNotifications(p);
      }
    }     
  }


  public void createAggregateProduct(String productKey, Double alpha, List<String> componentsProductKey, List<Integer> componentsProductAmount) {
    Recipe recipe = new Recipe(alpha);
    for (int i = 0; i < componentsProductAmount.size(); i++) {
      if (!_products.containsKey(productKey.toLowerCase())) {
        addSimpleProduct(productKey);
      }
      recipe.addComponent(new Component(componentsProductAmount.get(i), _products.get(componentsProductKey.get(i).toLowerCase())));
    }
    addAggregateProduct(productKey, recipe);
  }


  // Transaction

  public void validateParameters(String partnerKey, String productKey) throws UnkPartnerKeyException, UnkProductKeyException {
    if (!_partners.containsKey(partnerKey.toLowerCase())) 
      throw new UnkPartnerKeyException();

    if (!_products.containsKey(productKey.toLowerCase())) 
      throw new UnkProductKeyException();
  }

  // Acquisition
  public void addNewAcquisitionTransaction(String partnerKey, String productKey, double price, int amount) {

    Batch batch = new Batch(price, amount, _products.get(productKey.toLowerCase()), _partners.get(partnerKey.toLowerCase()));
    _products.get(productKey.toLowerCase()).addNewBatch(batch);

    Product p = _products.get(productKey.toLowerCase());

    _partners.get(partnerKey.toLowerCase()).addBatch(batch);
    Transaction transaction = new Acquisition(_transactionsIds, _date.now(), price, amount, _products.get(productKey.toLowerCase()), _partners.get(partnerKey.toLowerCase()));
    _transactionsIds++;
    _transations.add(transaction);
    _partners.get(partnerKey.toLowerCase()).addTransation(transaction);
    _balance.setCurrentAvailable(-price * amount);
    _balance.setCurrentAccountant(-price * amount);
  }

  public void addNewSaleTransaction(String partnerKey, int deadlinePayment, String productKey, int amount) throws UnaProductException {

    Product product = _products.get(productKey.toLowerCase());
    Partner partner = _partners.get(partnerKey.toLowerCase());

    //verificar se é possivel vender
    
    if (product instanceof SimpleProduct && product.getCurrentQuantity() < amount) 
      throw new UnaProductException(product.getCurrentQuantity());

    else if (product instanceof AggregateProduct && product.getCurrentQuantity() < amount) {
      Recipe recipe = ((AggregateProduct) product).getRecipe();
      List<Component> components = recipe.getComponents();

      //para cada um dos componentes da receita verificar se tem quantidade suficiente
      for (Component c : components) {
        if (c.getProduct().getCurrentQuantity() < amount) 
          throw new UnaProductException(c.getProduct().getCurrentQuantity());
      }
    }

    // se o produto for simples, vende se diretamente -> VENDA SIMPLE PRODUCT <-

    Transaction transaction = new SaleByCredit(_transactionsIds, new Date(_date.now(), deadlinePayment), price, amount, _products.get(productKey.toLowerCase()), _partners.get(partnerKey.toLowerCase())); 

        //procurar preco mais baixo 

    List<Batch> batches = new ArrayList<>(product.getAllBatches());
    Collections.sort(batches, new BatchesComparatorByPrice());

    int quantityToSale = 0;
    int quantityAvailable = 0;

    while (quantityToSale < amount) {
      for (Batch b : batches) {

        quantityAvailable = b.getQuantity();

        if (quantityAvailable >= amount) 
          b.decreaseQuantity(amount);
        else {
          quantityToSale += quantityAvailable;
          b.decreaseQuantity(amount);
          product.removeEmptyBatch();
        }

        //calcular os precos dos produtos
      }
    }
    


    // se o produto for agregado, e nao houver quantidade suficiente, pode fabricar-se
    
    Recipe recipe = ((AggregateProduct) product).getRecipe();
    Double breakdownSalePrice = product.getBreakdownSalePrice();
    Double totalTransactionPrice = 0.0;
    Double totalAggregateProductPrice = 0.0;
    Double transactionPrice = 0.0;

    int createProductNum = 0;

    batches = product.getAllBatchesByPrice();


    for (int i = 0; createProductNum != amount; i++) {
      int num = 0;
      if (batches.get(i).getQuantity() >= (amount - createProductNum))
        num = amount - createProductNum;
      else 
        num = (amount - createProductNum) - batches.get(i).getQuantity();
        
        totalAggregateProductPrice += batches.get(i).getPrice() * num;
        Double componentsPrice = 0.0;

      for (Component c : recipe.getComponents()) {
        componentsPrice += c.getQuantity() * num * batches.get(i).getPrice();
        Batch batch = new Batch(breakdownSalePrice, c.getQuantity() * num , product, partner);
        c.getProduct().addNewBatch(batch);
        partner.addBatch(batch);
      }
      totalTransactionPrice += componentsPrice * num;
      batches.get(i).decreaseQuantity(num);
    }

    product.removeEmptyBatch();
    partner.removeEmptyBatch();

    if ((transactionPrice = totalAggregateProductPrice - totalTransactionPrice) < 0)
      transactionPrice = 0.0;

  }

  protected final static class BatchesComparatorByPrice implements Comparator<Batch> {

    @Override
    public int compare(Batch b1, Batch b2) {
      return (int) (b1.getPrice() - b2.getPrice());
    }
  } 

  // BreakdownSale
  public void addNewBreakdownSaleTransaction(String partnerKey, String productKey, int amount) throws UnaProductException {
    Product product = _products.get(productKey.toLowerCase());
    Partner partner = _partners.get(partnerKey.toLowerCase());
    if (product instanceof SimpleProduct) 
      return;
    if (product.getCurrentQuantity() < amount) 
      throw new UnaProductException(product.getCurrentQuantity());
    
    Recipe recipe = ((AggregateProduct) product).getRecipe();
    Double breakdownSalePrice = product.getBreakdownSalePrice();
    Double totalTransactionPrice = 0.0;
    Double totalAggregateProductPrice = 0.0;
    Double transactionPrice = 0.0;

    int createProductNum = 0;

    List<Batch> batches = product.getAllBatchesByPrice();


    for (int i = 0; createProductNum != amount; i++) {
      int num = 0;
      if (batches.get(i).getQuantity() >= (amount - createProductNum))
        num = amount - createProductNum;
      else 
        num = (amount - createProductNum) - batches.get(i).getQuantity();
        
        totalAggregateProductPrice += batches.get(i).getPrice() * num;
        Double componentsPrice = 0.0;

      for (Component c : recipe.getComponents()) {
        componentsPrice += c.getQuantity() * num * batches.get(i).getPrice();
        Batch batch = new Batch(breakdownSalePrice, c.getQuantity() * num , product, partner);
        c.getProduct().addNewBatch(batch);
        partner.addBatch(batch);
      }
      totalTransactionPrice += componentsPrice * num;
      batches.get(i).decreaseQuantity(num);
    }

    product.removeEmptyBatch();
    partner.removeEmptyBatch();

    if ((transactionPrice = totalAggregateProductPrice - totalTransactionPrice) < 0)
      transactionPrice = 0.0;

    Transaction transaction = new BreakdownSale(_transactionsIds, _date.now(), transactionPrice, amount, product, partner);
    _transactionsIds++;
    _transations.add(transaction);
    partner.addTransation(transaction);
    _balance.setCurrentAvailable(-transactionPrice * amount);
    _balance.setCurrentAccountant(-transactionPrice * amount);

  }

  public String showTransaction(int transactionKey) throws UnkTransactionKeyException{
    if (_transations.size() <= transactionKey)
      throw new UnkTransactionKeyException();
    return _transations.get(transactionKey).toString();
    
  }


  public void registNotification(Notification notification) {
    _notifications.add(notification);
  }

  private void importPartner(String[] fields)  {
    String id = fields[1];
    String name = fields[2];
    String address = fields[3];

    Partner partner = new Partner(id, name, address);
    _partners.put(id.toLowerCase(), partner);

    for (Product p: _products.values()) {
      p.toggleNotifications(partner);
    }
  }

  private void importBatchS(String[] fields) {
    String id = fields[1];
    String partner = fields[2];
    double price = Double.parseDouble(fields[3]);
    int stock = Integer.parseInt(fields[4]);

    // metodo para criar lote de produto simples
    if (!(_products.containsKey(id.toLowerCase()))) {
      addSimpleProduct(id);
    }

    SimpleProduct product = (SimpleProduct) _products.get(id.toLowerCase());
    Partner prtnr = _partners.get(partner.toLowerCase());
    Batch b = new Batch(price, stock, product, prtnr);
    product.addNewBatch(b);

    //adicionar o lote ao parceiro
    Partner p = _partners.get(partner.toLowerCase());
    p.addBatch(b);
  }

  private void importBatchM(String[] fields) {
    String id = fields[1];
    String partner = fields[2];
    String[] idAndQuantity;
    String componentId;
    int quantity;
    double price = Double.parseDouble(fields[3]);
    int stock = Integer.parseInt(fields[4]);
    double alpha = Double.parseDouble(fields[5]);

    Recipe recipe = new Recipe(alpha);

    String[] components = fields[6].split("#"); //contains each part of the division "<id> : <quantity>"          

    for (String component: components) {
      idAndQuantity = component.split(":");   
      componentId = idAndQuantity[0];            
      quantity = Integer.parseInt(idAndQuantity[1]);

      if (!(_products.containsKey(componentId.toLowerCase()))) {
        addSimpleProductWithQuantity(componentId, quantity, new ArrayList<Observer>(_partners.values()));
      }    

      Component c = new Component(quantity, new SimpleProduct(componentId));
      recipe.addComponent(c);
    }
  
    //criar o produto derivado
    if (!(_products.containsKey(id.toLowerCase()))) { //se ainda nao foi criado
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

  /**
   * 
   * @param txtfile
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {

    try (BufferedReader in = new BufferedReader(new FileReader(txtfile))) {
      
      String line;

      while ((line = in.readLine()) != null) {

        String[] fields = line.split("\\|");

        if (fields[0].equals(Label.PARTNER)) {
          importPartner(fields);                   
        }

        else if (fields[0].equals(Label.BATCH_S)) {
          importBatchS(fields);
        }

        else if (fields[0].equals(Label.BATCH_M)) {
          importBatchM(fields);
        }
      }
    }
    catch (IOException e) {
      throw e;
    }

  }

}
