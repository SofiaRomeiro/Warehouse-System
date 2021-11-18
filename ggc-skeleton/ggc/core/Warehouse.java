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
import java.util.Collections;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.NotValidDateException;
import ggc.core.exception.UnaProductException;
import ggc.core.exception.UnkPartnerKeyException;
import ggc.core.exception.DuplPartnerKeyException;
import ggc.core.exception.UnkProductKeyException;
import ggc.core.exception.UnkTransactionKeyException;
import ggc.core.exception.UnaComponentException;
import ggc.core.exception.NoPaymentsByPartner;


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

  private List<Batch> _restore;

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

    _restore = new LinkedList<>();
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

  public void setDate(int days) {
    _date = _date.setDate(days);
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

  /*
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
      if (b.getQuantity() > 0)
        batches.add(b.toString());
    }

    return batches;
  }

  /*
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
      if (b.getQuantity() > 0)
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
  public String getPartnerById(String key) throws UnkPartnerKeyException{
    if (!_partners.containsKey(key.toLowerCase()))
      throw new UnkPartnerKeyException();
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
    _products.get(productKey.toLowerCase()).toggleNotifications(_partners.get(partnerKey.toLowerCase()));
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

  public List<String> showSaleTransactionByPartner(String key) throws UnkPartnerKeyException {
    
    ArrayList<String> transactions = new ArrayList<>();

    if (!_partners.containsKey(key.toLowerCase())) {
      throw new UnkPartnerKeyException();
    }

    Partner partner = _partners.get(key.toLowerCase());

    for (Transaction t : partner.getAllTransactions()) {
      if (t instanceof Sale)
      transactions.add(t.toString());
    }
    return transactions;
  }

  public List<String> lookupPaymentsByPartner(String partnerKey) throws UnkPartnerKeyException, NoPaymentsByPartner {
    if (!_partners.containsKey(partnerKey.toLowerCase()))
      throw new UnkPartnerKeyException();
    
    Partner p = _partners.get(partnerKey.toLowerCase());
    List<String> transactions = p.getAllPaidTransaction();
    if (p.getPaidSales() == 0.0)
      throw new NoPaymentsByPartner();
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

  public void addAggregateProduct(String id, Recipe recipe, double alpha) {
    if (!(_products.containsKey(id.toLowerCase()))) {
      AggregateProduct product = new AggregateProduct(id, recipe, alpha); 
      _products.put(id.toLowerCase(), product);

      for (Partner p : _partners.values()) {
        product.toggleNotifications(p);
      }
    }     
  }

  public void createAggregateProduct(String productKey, Double alpha, List<String> componentsProductKey, List<Integer> componentsProductAmount) throws UnaComponentException {
    Recipe recipe = new Recipe(alpha);
    for (int i = 0; i < componentsProductAmount.size(); i++) {
      if (!_products.containsKey(componentsProductKey.get(i).toLowerCase())) {
        throw new UnaComponentException(0, 0, componentsProductKey.get(i));
        //addSimpleProduct(productKey);
      }
      recipe.addComponent(new Component(componentsProductAmount.get(i), _products.get(componentsProductKey.get(i).toLowerCase())));
    }
    addAggregateProduct(productKey, recipe, alpha);
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
    Product p = _products.get(productKey.toLowerCase());
    if (p.getCurrentQuantity() == 0 && amount != 0 && p.getMaxPrice() != 0) {
      Notification newN = new Notification(NotificationType.NEW, p, price);
      p.notifyObservers(newN);
    }
    p.updatePrices(price);
    p.addNewBatch(batch);

    _partners.get(partnerKey.toLowerCase()).addBatch(batch);
    Transaction transaction = new Acquisition(_transactionsIds, _date.now(), price, amount, _products.get(productKey.toLowerCase()), _partners.get(partnerKey.toLowerCase()));
    _transactionsIds++;
    _transations.add(transaction);
    _partners.get(partnerKey.toLowerCase()).addTransation(transaction);
    _balance.setCurrentAvailable(-price * amount);
    _balance.setCurrentAccountant(-price * amount);
  }

  // SaleByCredit
  private void handleNewTransaction(double finalPrice, Transaction transaction, int amount, Partner partner, Product product) {
    ((Sale)transaction).setValuePaid(finalPrice);
    _transactionsIds++;
    _transations.add(transaction);
    partner.addTransation(transaction);
    product.updateCurrentQuantity(-amount);
    _balance.setCurrentAccountant(finalPrice * amount);
  }

  private void handleNewTransactionAggregation(int quantityManufactured, double finalPrice, Transaction transaction, int amount, Partner partner, Product product) {
    ((Sale)transaction).setValuePaid(finalPrice);
    _transactionsIds++;
    _transations.add(transaction);
    partner.addTransation(transaction);
    product.updateCurrentQuantity(-quantityManufactured);
    _balance.setCurrentAccountant(finalPrice * amount);
  }


  /*
 private double calculateFinalSalePrice(Product product, int amount) {

    List<Batch> batches = new ArrayList<>(product.getAllBatchesByPrice());
    int currentQuantityToSale = 0;
    int quantityTokenToSaleByBatch = 0;
    int quantityAvailableByEachBatch = 0;
    double finalPrice = 0.0;

    while (currentQuantityToSale < amount) {    

      for (Batch b : batches) {

        quantityAvailableByEachBatch = b.getQuantity();
        if (quantityAvailableByEachBatch >= amount && currentQuantityToSale == 0) { 
          b.decreaseQuantity(amount);
          currentQuantityToSale += amount;
          finalPrice += (amount * b.getPrice());
          break;
        }

        else if ((quantityAvailableByEachBatch + currentQuantityToSale) >= amount ) {
          quantityTokenToSaleByBatch = amount - currentQuantityToSale;                  //a quantidade a retirar corresponde à diferença entre o total necessario (amount) e a quantidade já disponivel
          b.decreaseQuantity(quantityTokenToSaleByBatch);
          currentQuantityToSale += quantityTokenToSaleByBatch;
          finalPrice += (quantityTokenToSaleByBatch * b.getPrice());
        }

        else {
          currentQuantityToSale += quantityAvailableByEachBatch;
          b.decreaseQuantity(b.getQuantity()); 
          finalPrice += (quantityAvailableByEachBatch * b.getPrice());

          // RECURSION

        }

      }

    }

    product.removeEmptyBatch();
    return finalPrice;
  } 
  */

  // SaleByCredit
  /*
  public void addNewSaleTransaction(String partnerKey, int deadlinePayment, String productKey, int amount) throws UnaProductException, UnaComponentException, UnkPartnerKeyException, UnkProductKeyException {

    if (!_products.containsKey(productKey.toLowerCase()))
      throw new UnkProductKeyException();

    if (!_partners.containsKey(partnerKey.toLowerCase()))
      throw new UnkPartnerKeyException();

    Product product = _products.get(productKey.toLowerCase());
    Partner partner = _partners.get(partnerKey.toLowerCase());

    if (product instanceof SimpleProduct ) { 

      double finalPrice = 0.0;     

      if (product.getCurrentQuantity() < amount)
        throw new UnaProductException(product.getCurrentQuantity(), product.getId());

      else {

        finalPrice = calculateFinalSalePrice(product, amount);    
      }        

      Transaction transaction = new SaleByCredit(_transactionsIds, new Date(_date.now().getDate(), deadlinePayment), finalPrice, amount, _products.get(productKey.toLowerCase()), _partners.get(partnerKey.toLowerCase())); 
      handleNewTransaction(finalPrice, transaction, amount, partner, product);

    }   

    else {

      if (product.getCurrentQuantity() > amount) { 
        double finalPrice = calculateFinalSalePrice(product, amount);
        Transaction transaction = new SaleByCredit(_transactionsIds, new Date(_date.now().getDate(), deadlinePayment), finalPrice, amount, product, partner);
        handleNewTransaction(finalPrice, transaction, amount, partner, product);

      }

      else if (product.getCurrentQuantity() > 0 && product.getCurrentQuantity() < amount) { 

        int productAmountAvailable = product.getCurrentQuantity();
        int leftQuantityToManufacture = amount - productAmountAvailable;
        double finalPrice = 0.0; 

        while (productAmountAvailable != 0 ) {  

          List<Batch> batchesAvailable = new ArrayList<>(product.getAllBatchesByPrice());
          int currentQuantityTokenToSale = 0;
          int quantityTokenFromBatch = 0;         

          for (Batch batch : batchesAvailable) {          

            quantityTokenFromBatch = batch.getQuantity();
            batch.decreaseQuantity(quantityTokenFromBatch);              
            product.removeEmptyBatch(); 
            productAmountAvailable -= quantityTokenFromBatch;
            currentQuantityTokenToSale += quantityTokenFromBatch;
            finalPrice += (batch.getPrice() * quantityTokenFromBatch);
          }                   
        }        
        

        Recipe recipe = ((AggregateProduct) product).getRecipe();
        double alpha = ((AggregateProduct)product).getAlpha();
        List<Component> components = recipe.getComponents();
        double baseSalePrice = finalPrice;

        for (Component c : components) {

          Product component = c.getProduct();

          int componentAmountNeeded = c.getQuantity() * leftQuantityToManufacture;

          if (component.getCurrentQuantity() < componentAmountNeeded) {
            throw new UnaComponentException(component.getCurrentQuantity(), componentAmountNeeded, component.getId());
          }

          baseSalePrice += calculateFinalSalePrice(component, componentAmountNeeded);
          component.updateCurrentQuantity(-componentAmountNeeded);
        }

        double manufacturePrice = (1 + alpha) * (baseSalePrice - finalPrice);

        double priceWithAlpha = (finalPrice + (manufacturePrice));

        
        Transaction transaction = new SaleByCredit(_transactionsIds, new Date(_date.now().getDate(), deadlinePayment), priceWithAlpha, amount, product, partner);
        handleNewTransactionAggregation(leftQuantityToManufacture, priceWithAlpha, transaction, amount, partner, product);
        product.updatePrices(manufacturePrice / leftQuantityToManufacture);


      }
      else {

        int productAmountAvailable = product.getCurrentQuantity();
        int leftQuantityToManufacture = amount - productAmountAvailable;
        double finalPrice = 0.0;
        Recipe recipe = ((AggregateProduct) product).getRecipe();
        double alpha = ((AggregateProduct)product).getAlpha();
        List<Component> components = recipe.getComponents();
        double baseSalePrice = finalPrice;

        for (Component c : components) {

          Product component = c.getProduct();

          int componentAmountNeeded = c.getQuantity() * leftQuantityToManufacture;

          if (component.getCurrentQuantity() < componentAmountNeeded) {
            throw new UnaComponentException(component.getCurrentQuantity(), componentAmountNeeded, component.getId());
          }

          baseSalePrice += calculateFinalSalePrice(component, componentAmountNeeded);            

        }

        double manufacturePrice = baseSalePrice - finalPrice;
        double priceWithAlpha = (finalPrice + ((1 + alpha) * manufacturePrice));

        Transaction transaction = new SaleByCredit(_transactionsIds, new Date(_date.now().getDate(), deadlinePayment), priceWithAlpha, amount, product, partner);
        handleNewTransactionAggregation(leftQuantityToManufacture, priceWithAlpha, transaction, amount, partner, product);
      }
    } 
  } */

  public void addNewSaleTransaction(String partnerKey, int deadlinePayment, String productKey, int amount)
      throws UnaProductException, UnaComponentException, UnkPartnerKeyException, UnkProductKeyException {

    if (!_products.containsKey(productKey.toLowerCase()))
      throw new UnkProductKeyException();

    if (!_partners.containsKey(partnerKey.toLowerCase()))
      throw new UnkPartnerKeyException();

    Product product = _products.get(productKey.toLowerCase());
    Partner partner = _partners.get(partnerKey.toLowerCase());
    double cost = 0;

    if (product instanceof SimpleProduct) {
      if (product.getCurrentQuantity() < amount)
        throw new UnaProductException(product.getCurrentQuantity(), product.getId());
    } else {
      stockValidation((AggregateProduct) product, amount);
    }
    int createAmount = 0;
    int take = 0;
    for (Batch b : product.getAllBatchesByPrice()) {
      if (b.getQuantity() >= amount - createAmount)
        take = amount - createAmount;
      else
        take = b.getQuantity();

      cost += take * b.getPrice();
      b.decreaseQuantity(take);
      product.updateCurrentQuantity(-take);
      partner.removeEmptyBatch();
      createAmount += take;
      if (amount == createAmount)
        break;
    }
    product.removeEmptyBatch();
    int lacking = amount - createAmount;
    for (int i = 0; i < lacking; i++) {
      cost += aggregateProduct((AggregateProduct) product);
    }
    Transaction transaction = new SaleByCredit(_transactionsIds, new Date(_date.now().getDate(), deadlinePayment, _date.now().getDate()), cost,
        amount, _products.get(productKey.toLowerCase()), _partners.get(partnerKey.toLowerCase()));
    _transactionsIds++;
    _transations.add(transaction);
    partner.addTransation(transaction);
    _balance.setCurrentAccountant(cost);
  }

  public void stockValidation(AggregateProduct product, int amount) throws UnaComponentException {
    if (_products.get(product.getId().toLowerCase()).getCurrentQuantity() >= amount)
      return;

    int lacking = amount - _products.get(product.getId().toLowerCase()).getCurrentQuantity();

    for (Component c : product.getRecipe().getComponents()) {
      int lacking2 = 0;
      if (c.getProduct() instanceof SimpleProduct) {
        if (c.getProduct().getCurrentQuantity() < (lacking * c.getQuantity()))
          throw new UnaComponentException(c.getProduct().getCurrentQuantity(), lacking * c.getQuantity(),
              c.getProduct().getId());
      } else {
        if (c.getProduct().getCurrentQuantity() < (lacking * c.getQuantity()))
          lacking2 = (lacking * c.getQuantity()) - c.getProduct().getCurrentQuantity();
        stockValidation((AggregateProduct) c.getProduct(), lacking2);
      }
    }
  }

  public double aggregateProduct(AggregateProduct product) {
    double cost = 0;
    double componentPriceSum = 0;

    for (Component c : product.getRecipe().getComponents()) {
      int createAmount = 0;
      int take = 0;
      for (Batch b : c.getProduct().getAllBatchesByPrice()) {
        if (b.getQuantity() >= c.getQuantity() - createAmount)
          take = c.getQuantity() - createAmount;
        else
          take = b.getQuantity();

        componentPriceSum += take * b.getPrice();
        b.decreaseQuantity(take);
        c.getProduct().updateCurrentQuantity(-take);
        _partners.get(b.getPartnerKey().toLowerCase()).removeEmptyBatch();
        createAmount += take;
        if (c.getQuantity() == createAmount)
          break;
      }
      c.getProduct().removeEmptyBatch();
      int lacking = c.getQuantity() - createAmount;
      for (int i = 0; i < lacking; i++) {
        componentPriceSum += aggregateProduct((AggregateProduct) c.getProduct());
      }
    }
    cost = (product.getAlpha() + 1) * componentPriceSum;
    product.updatePrices(cost);
    return cost;
  }

  /*
  public void addNewSaleTransaction(String partnerKey, int deadlinePayment, String productKey, int amount) throws UnaProductException, UnaComponentException, UnkPartnerKeyException, UnkProductKeyException {

    if (!_products.containsKey(productKey.toLowerCase()))
      throw new UnkProductKeyException();

    if (!_partners.containsKey(partnerKey.toLowerCase()))
      throw new UnkPartnerKeyException();

    Product product = _products.get(productKey.toLowerCase());
    Partner partner = _partners.get(partnerKey.toLowerCase());
    double finalPrice = 0.0;

    if (product instanceof SimpleProduct ) {      

      if (product.getCurrentQuantity() < amount)
        throw new UnaProductException(product.getCurrentQuantity(), product.getId());

      else {

        finalPrice = calculateFinalSalePrice(product, amount);    
      }        

      Transaction transaction = new SaleByCredit(_transactionsIds, new Date(_date.now().getDate(), deadlinePayment), finalPrice, amount, _products.get(productKey.toLowerCase()), _partners.get(partnerKey.toLowerCase())); 
      handleNewTransaction(finalPrice, transaction, amount, partner, product);
    }

    else {

      try {
        //List<String> l = new LinkedList<>();
        finalPrice = want(product, amount);
        //manufacture(product, amount);
      }
      catch (UnaProductException upe) {
        restore();
        throw new UnaProductException(product.getCurrentQuantity(), product.getId());
      }

      Transaction transaction = new SaleByCredit(_transactionsIds, new Date(_date.now().getDate(), deadlinePayment), finalPrice, amount, _products.get(productKey.toLowerCase()), _partners.get(partnerKey.toLowerCase())); 
      handleNewTransaction(finalPrice, transaction, amount, partner, product);
    }
  }

  private void backup(Product product, Batch b, int quantity) { 
    _restore.add(new Batch(b.getPrice(), quantity, product, b.getPartner()));
  }

  private double calculateFinalSalePrice(Product product, int amount) {    

    List<Batch> batches = new ArrayList<>(product.getAllBatchesByPrice());
    int quantity = 0;
    double price = 0.0;

    //por cada lote
    for (Batch b : batches) {
      if (b.getQuantity() + quantity >= amount) {
        backup(product, b, amount - quantity);
        b.decreaseQuantity(amount - quantity);
        product.updateCurrentQuantity(-(amount - quantity));
        product.removeEmptyBatch();
        return (b.getPrice() * (amount - quantity)) + price;
      }
      
      else {
        quantity += b.getQuantity();
        backup(product, b, b.getQuantity());
        b.decreaseQuantity(b.getQuantity());
        product.updateCurrentQuantity(-(b.getQuantity()));
        price += b.getPrice() * b.getQuantity();
      }
    }

    product.removeEmptyBatch();
    return price;
  } 

  private double want(Product product, int amount) throws UnaProductException {
    int missing = amount - product.getCurrentQuantity();
    int produced = 0;
    double salePrice = 0.0;

    if (product.getCurrentQuantity() > amount) {
      salePrice += calculateFinalSalePrice(product, amount);
    } 
    else {
      salePrice += calculateFinalSalePrice(product, product.getCurrentQuantity());
    }

    if (product instanceof SimpleProduct) {
        if (missing > 0) 
          throw new UnaProductException(product.getCurrentQuantity(), product.getId());
        
        return salePrice;
    } 

    double alpha = ((AggregateProduct) product).getAlpha();
    Recipe recipe = ((AggregateProduct) product).getRecipe();
    List<Component> components = recipe.getComponents();

    while (produced < missing) {       

        for (Component c : components) {

          try {

            salePrice += (1 + alpha) * want(c.getProduct(), c.getQuantity());
          }
          catch (UnaProductException upe) {
            throw upe;
          }
        }

      produced++;
    }
    return salePrice;
  }

  private void restore() {

    for (Batch batch : _restore) {
      Product p = _products.get(batch.getProduct().getId().toLowerCase());
      List<Batch> batches = p.getAllBatches();

      for (Batch b : batches) {
        if (b.equals(batch)) {
          p.updateCurrentQuantity(b.getQuantity());
          b.setQuantity(batch.getQuantity());
        }
        else {
          p.addNewBatch(new Batch(batch.getPrice(), batch.getQuantity(), batch.getProduct(), batch.getPartner()));
          p.updateCurrentQuantity(batch.getQuantity());
        }
      }
    }

    // por cada lote na lista para restaurar
      //vou procurar o produto original respetivo e verificar se o lote existe nesse produto
      // se nao existir, crio esse lote de novo no produto original
      // se existir numa quantidade inferior, reponho apenas a quantidade

      //indepentemente da situacao, atualizar as existencias do produto respetivo do lote

    _restore.clear();    
  }
  */
  
  // BreakdownSale
  public void addNewBreakdownSaleTransaction(String partnerKey, String productKey, int amount)
      throws UnaProductException {
    Product product = _products.get(productKey.toLowerCase());
    Partner partner = _partners.get(partnerKey.toLowerCase());
    if (product instanceof SimpleProduct)
      return;
    if (product.getCurrentQuantity() < amount)
      throw new UnaProductException(product.getCurrentQuantity());

    Recipe recipe = ((AggregateProduct) product).getRecipe();

    Double totalTransactionPrice = 0.0;
    Double totalAggregateProductPrice = 0.0;
    Double transactionPrice = 0.0;
    Double componentsPrice = 0.0;

    List<String> componentsString = new ArrayList<>();

    int createProductNum = 0;

    List<Batch> batches = new ArrayList<>();
    batches = product.getAllBatchesByPrice();

    for (Component c : recipe.getComponents()) {
      Double breakdownSalePrice = c.getProduct().getBreakdownSalePrice();
      componentsPrice = c.getQuantity() * breakdownSalePrice;
      Batch batch = new Batch(breakdownSalePrice, c.getQuantity() * amount, c.getProduct(), partner);
      c.getProduct().addNewBatch(batch);
      partner.addBatch(batch);
      totalTransactionPrice += componentsPrice;
    }

    for (int i = 0; createProductNum != amount; i++) {
      int num = 0;
      if (batches.get(i).getQuantity() >= (amount - createProductNum))
        num = amount - createProductNum;
      else
        num = batches.get(i).getQuantity();

      totalAggregateProductPrice = batches.get(i).getPrice() * num;
      createProductNum += num;
      transactionPrice += totalAggregateProductPrice - (totalTransactionPrice * num);
      batches.get(i).decreaseQuantity(num);
      product.updateCurrentQuantity(-num);
    }

    product.removeEmptyBatch();
    partner.removeEmptyBatch();

    for (Component c : recipe.getComponents()) {
      componentsString.add(c.getId() + ":" + (c.getQuantity() * amount) + ":"
          + (c.getQuantity() * Math.round(amount * c.getProduct().getBreakdownSalePrice())));
    }

    Transaction transaction = new BreakdownSale(_transactionsIds, _date.now(), transactionPrice, amount, product,
        partner);
    ((BreakdownSale) transaction).setComponentsString(componentsString);
    _transactionsIds++;
    _transations.add(transaction);

    partner.addTransation(transaction);
    if (transactionPrice > 0) {
      ((Sale) transaction).setValuePaid(transactionPrice);
      _balance.setCurrentAvailable(transactionPrice);
      _balance.setCurrentAccountant(transactionPrice);
      partner.pay(_date.now(), ((Sale)transaction) , "Aggregate");
    }

  }

  public String showTransaction(int transactionKey) throws UnkTransactionKeyException{
    if (_transations.size() <= transactionKey || transactionKey < 0) 
      throw new UnkTransactionKeyException();
    return _transations.get(transactionKey).toString();
    
  }

  // ReceivePayment
  public void receivePayment(int transactionKey) throws UnkTransactionKeyException{
    if ( 0 > transactionKey || transactionKey > _transactionsIds)
      throw new UnkTransactionKeyException();
    if (!(_transations.get(transactionKey) instanceof SaleByCredit))
      return;
    SaleByCredit transaction =  (SaleByCredit)_transations.get(transactionKey);
    if (transaction.isPaid())
      return;

    transaction.getTransactionDate().setPaymentDate(Date.now().getDate());
    Partner partner = transaction.getPartner();
    Double value = partner.pay(transaction.getTransactionDate(), ((Sale)transaction), transaction.getProductType());
    
    transaction.setValuePaid(value);
    transaction.pay();
    transaction.getTransactionDate().setPaymentDate(_date.getDate());
    _balance.setCurrentAvailable(value);
    partner.setPaidSales(value);
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
    //product.updatePrices(price);

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

      Component c = new Component(quantity, _products.get(componentId.toLowerCase()) /*new SimpleProduct(componentId)*/);
      recipe.addComponent(c);
    }
  
    //criar o produto derivado
    if (!(_products.containsKey(id.toLowerCase()))) { //se ainda nao foi criado
      addAggregateProduct(id, recipe, alpha);
    }
    AggregateProduct product = (AggregateProduct) _products.get(id.toLowerCase());
    Partner prtnr = _partners.get(partner.toLowerCase());
    AggregateProduct ap = new AggregateProduct(id, recipe, alpha);
    //ap.updatePrices(price);
    Batch b = new Batch(price, stock, ap, prtnr);
    product.addNewBatch(b);
    //product.updatePrices(price);

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
