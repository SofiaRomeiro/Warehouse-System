package ggc.core.Product;

//import exceptions

import ggc.core.Notification;
import ggc.core.Partner;

import java.io.Serializable;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Locale;
import java.text.Collator;

public abstract class Product {
    
    private String _id;
    private float _maxPrice;
    private float _lowestPrice;
    private float _highestPrice;
    private int _currentQuantity;

    public Product (String id) {
        _id = id;
    }

    public abstract float checkQuantity(int quantity, Partner p);

    

}
