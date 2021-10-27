package ggc.core.Product;

//import exceptions

import ggc.core.Notifications;
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
    private List<Component> _components;

    public Product(String id, float maxPrice, float lowestPrice, float highestPrice, int currentQuantity) {
        _id = id;
        _maxPrice = maxPrice;
        _lowestPrice = lowestPrice;
        _highestPrice = highestPrice;
        _currentQuantity = currentQuantity;
    }

    public Product (String id) {
        this (id, 0, 0, 0, 0);
    }

    public abstract float checkQuantity(int quantity, Partner p);




    

}
