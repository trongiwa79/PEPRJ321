/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trong.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import trong.order.OrderDTO;

/**
 *
 * @author DELL
 */
public class CartObj implements Serializable {
    private Map<String, OrderDTO> items;

    /**
     * @return the items
     */
    public Map<String, OrderDTO> getItems() {
        return items;
    }
    
    public void addItemToCart(String id, OrderDTO order) {
        if(this.items == null) {
            this.items = new HashMap<>();
        }

        for (String key : this.items.keySet()) {
            if (key.equals(id)) {
                int oldQuantity = this.items.get(key).getQuantity();
                float oldTotal = this.items.get(key).getTotal();
                order.setQuantity(order.getQuantity() + oldQuantity);
                order.setTotal(order.getTotal() + oldTotal);
            }
        }
        
        this.items.put(id, order);
    }
    
    public void removeItemFromCart(String id) {
        if(this.items == null) {
            return;
        }
        
        if (this.items.containsKey(id)) {
            this.items.remove(id);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }
    
    private float totalPrice = 0;

    /**
     * @return the totalPrice
     */
    public float getTotalPrice() {
        return totalPrice;
    }
    
    public void sumTotalPrice() {
        if(this.items == null) {
            return;
        }
        
        this.totalPrice = 0;
        
        for(OrderDTO order : this.items.values()) {
            this.totalPrice += order.getTotal();
        }
    }
}
