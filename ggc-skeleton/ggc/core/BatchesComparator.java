package ggc.core;

import java.util.Comparator;

public class BatchesComparator implements Comparator<Batch> {

	@Override
	public int compare(Batch b1, Batch b2) {

		if (b1.getId().toLowerCase().compareTo(b2.getId().toLowerCase()) != 0) {
			return b1.getId().toLowerCase().compareTo(b2.getId().toLowerCase());
		} else {
			if (b1.getPartnerKey().toLowerCase().compareTo(b2.getPartnerKey().toLowerCase()) != 0) {
				return b1.getPartnerKey().toLowerCase().compareTo(b2.getPartnerKey().toLowerCase());
			} else {
				if (b1.getPrice() != b2.getPrice()) {
					return (int) (b1.getPrice() - b2.getPrice());
				} else {
					return (b1.getQuantity() - b2.getQuantity());
				}
			}
		}
	}
}
