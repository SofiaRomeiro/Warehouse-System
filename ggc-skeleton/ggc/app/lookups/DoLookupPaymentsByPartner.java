package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import java.util.List;
import java.util.ArrayList;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnkPartnerKeyException;
import ggc.core.exception.NoPaymentsByPartner;



/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("partnerKey", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException{
    String partnerKey = stringField("partnerKey");
  List<String> transactions = new ArrayList<>();

    try {
      transactions = _receiver.lookupPaymentsByPartner(partnerKey);
      for (String b : transactions) {
        _display.addLine(b);
      }
      _display.display();
    }catch (UnkPartnerKeyException upke) {
      throw new UnknownPartnerKeyException(partnerKey);
    }catch (NoPaymentsByPartner npbp) {
      
    }
  }

}
