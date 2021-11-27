package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class BreakdownSale extends Sale implements Serializable, Label {

    private static final long serialVersionUID = 928404186756L;
    private List<String> _componentsString;

    public BreakdownSale(int id, Date transactionDate, double baseValue, int quantity, Product product,
            Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner, Label.BREAKDOWN_SALE);
        _componentsString = new ArrayList<>();

    }

    public boolean isPaid() {
        return true;
    }

    public void setComponentsString(List<String> componentsString) {
        _componentsString = componentsString;
    }

    public String toString() {

        String recipe = new String();

        for (int i = 0; i < _componentsString.size(); i++) {
            recipe += _componentsString.get(i);
            if (i != _componentsString.size() - 1)
                recipe += "#";
        }

        return "DESAGREGAÇÃO|" + super.getId() + "|" + super.getPartner().getKey() + "|" + super.getProduct().getId()
                + "|" + super.getQuantity() + "|" + Math.round(getBaseValue()) + "|" + Math.round(super.getValuePaid())
                + "|" + super.getTransactionDate().getPaymentDate() + "|" + recipe;
    }

}
