package ggc.core.product;

import ggc.core.Partner;
import ggc.core.product.Product;

import java.util.Comparator;
import java.lang.Math;

public class Batch {

	private double _price;
	private int _quantity;
	private Product _product;
	private Partner _partner;

	public Batch(double price, int quantity, Product product, Partner partner) {
		_price = price;
		_quantity = quantity;
		_product = product;
		_partner = partner;
	}

	public double getPrice() {return _price; }

	public int getQuantity() { return _quantity; }

	public String toString() {
		return _product.getId() + "|" + _partner.getKey() + "|" + Math.round(_price) + "|" + _quantity;
	}

	public String getPartnerKey() {
		return _partner.getKey();
	}

	public String getId() {return _product.getId(); }


	protected final static class BatchesComparator implements Comparator<Batch> {

    @Override
    public int compare(Batch b1, Batch b2) {

      if (b1.getId().compareTo(b2.getId()) != 0) {
        return b1.getId().compareTo(b2.getId());
      }
      else {

        if (b1.getPartnerKey().compareTo(b2.getPartnerKey()) != 0) {
          return b1.getPartnerKey().compareTo(b2.getPartnerKey());
        } 
        else {

          if (b1.getPrice() != b2.getPrice()) {
            return (int) (b1.getPrice() - b2.getPrice());
          }
          else {
            return (int) (b1.getQuantity() - b2.getQuantity());
          }

        }

      }

    }
  }
}