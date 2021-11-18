package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnavailableProductException;
import ggc.core.exception.UnkProductKeyException;
import ggc.core.exception.UnkPartnerKeyException;
import ggc.core.exception.UnaProductException;
import ggc.core.exception.UnaComponentException;
import ggc.core.WarehouseManager;


/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("partnerKey", Message.requestPartnerKey());
    addIntegerField("deadline", Message.requestPaymentDeadline());
    addStringField("productKey", Message.requestProductKey());
    addIntegerField("amount", Message.requestAmount());
  }


  @Override
  public final void execute() throws CommandException {

    String partnerKey = stringField("partnerKey");
    Integer paymentDeadline = integerField("deadline");
    String productKey = stringField("productKey");
    Integer amount = integerField("amount");

    try {
      _receiver.registerSaleTransaction(partnerKey,paymentDeadline, productKey, amount);;
    }
    catch (UnkPartnerKeyException upke){
      throw new UnknownPartnerKeyException(partnerKey);
    } 
    catch (UnkProductKeyException upke){
      throw new UnknownProductKeyException(productKey);
    }
    catch (UnaProductException upe) {
      throw new UnavailableProductException(upe.getProductComponent(), amount, upe.getAvailable());
    }
    catch (UnaComponentException uce) {
      throw new UnavailableProductException(uce.getProductComponent(), uce.getRequested(), uce.getAvailable());
    }
    /*catch (ClassNotFoundException e)  { 
       e.printStackTrace(); 
    }*/



  }

}