package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
import java.util.ArrayList;

import javax.sound.midi.Receiver;

import ggc.core.WarehouseManager;

import ggc.core.exception.UnkPartnerKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnkProductKeyException;


/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("partnerKey", Message.requestPartnerKey());
    addStringField("productKey", Message.requestProductKey());
    addRealField("price", Message.requestPrice());
    addIntegerField("Amount", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException, UnknownPartnerKeyException {
    
    String partnerKey = stringField("partnerKey");
    String productKey = stringField("productKey");
    Double price = realField("price");
    Integer amount = integerField("amount");

    try {
      _receiver.validateParameters(partnerKey, productKey);
      _receiver.registerAcquisitionTransaction(partnerKey, productKey, price, amount);
    }
    catch (UnkPartnerKeyException upke){
      throw new UnknownPartnerKeyException(partnerKey);
    } 
    catch (UnkProductKeyException upke){
      addStringField("answer", Message.requestAddRecipe());
      String answer = stringField("answer");
      if (answer.toLowerCase().equals("n"))
        _receiver.createSimpleProduct(productKey);
      else {
        addIntegerField("numComponents", Message.requestNumberOfComponents());
        addRealField("alpha", Message.requestAlpha());
        Integer numComponents = integerField("numComponents");
        Double alpha = realField("alpha");
        List<String> componentsProductKey = new ArrayList<>();
        List<Integer> componentsProductAmount = new ArrayList<>();

        for (int i = 0; i < numComponents; i++) {
          addStringField("componentsKey", Message.requestProductKey());
          addIntegerField("componentAmount", Message.requestAmount());
          componentsProductKey.add(stringField("componentsKey"));
          componentsProductAmount.add(integerField("componentAmount"));
        }
        _receiver.createAggregateProduct(productKey, alpha, componentsProductKey, componentsProductAmount);
      }
      _receiver.registerAcquisitionTransaction(partnerKey, productKey, price, amount);
    } 

  }

}