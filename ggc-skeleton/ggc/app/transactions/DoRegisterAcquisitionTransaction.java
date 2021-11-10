package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

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
    addIntegerField("amount", Message.requestAmount());
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

      Form request = new Form();
      request.addStringField("answer", Message.requestAddRecipe());
      String answer = request.parse().stringField("answer");
      if (answer.toUpperCase().equals("N"))
        _receiver.createSimpleProduct(productKey);
      else {
        Form request1 = new Form();
        Form request2 = new Form();

        request1.addIntegerField("numComponents", Message.requestNumberOfComponents());
        request2.addRealField("alpha", Message.requestAlpha());
        Integer numComponents = request1.parse().integerField("numComponents");
        Double alpha = request2.parse().realField("alpha");
        List<String> componentsProductKey = new ArrayList<>();
        List<Integer> componentsProductAmount = new ArrayList<>();

        for (int i = 0; i < numComponents; i++) {
          Form request3 = new Form();
          Form request4 = new Form();
          request3.addStringField("componentsKey", Message.requestProductKey());
          request4.addIntegerField("componentAmount", Message.requestAmount());
          componentsProductKey.add(request3.parse().stringField("componentsKey"));
          componentsProductAmount.add(request4.parse().integerField("componentAmount"));
        }
        _receiver.createAggregateProduct(productKey, alpha, componentsProductKey, componentsProductAmount);
      }
      _receiver.registerAcquisitionTransaction(partnerKey, productKey, price, amount);
    } 

  }

}