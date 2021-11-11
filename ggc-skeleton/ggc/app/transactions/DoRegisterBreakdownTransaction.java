package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnavailableProductException;
import ggc.core.exception.UnkProductKeyException;
import ggc.core.exception.UnkPartnerKeyException;
import ggc.core.exception.UnaProductException;
import ggc.core.WarehouseManager;



/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("partnerKey", Message.requestPartnerKey());
    addStringField("productKey", Message.requestProductKey());
    addIntegerField("amount", Message.requestAmount());  }

  @Override
  public final void execute() throws CommandException, UnknownPartnerKeyException, UnknownProductKeyException, UnavailableProductException {
    
    String partnerKey = stringField("partnerKey");
    String productKey = stringField("productKey");
    Integer amount = integerField("amount");

    try {
      _receiver.validateParameters(partnerKey, productKey);
      _receiver.registerBreakdownSaleTransaction(partnerKey, productKey, amount);;
    }
    catch (UnkPartnerKeyException upke){
      throw new UnknownPartnerKeyException(partnerKey);
    } 
    catch (UnkProductKeyException upke){  
      throw new UnknownProductKeyException(productKey);
    }
    catch (UnaProductException upe) {
      throw new UnavailableProductException(productKey, amount, upe.getAvailable());
    }
  }

}
