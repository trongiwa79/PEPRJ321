/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trong.item;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trong.utils.DBHelper;

/**
 *
 * @author DELL
 */
public class ItemDAO implements Serializable{
    private final String RESOURCE_NAME = "DBSHOP";
    
    private List<ItemDTO> listItems = null;

    /**
     * @return the listItems
     */
    public List<ItemDTO> getListItems() {
        return listItems;
    }
    
    public void searchItem(String keyword) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Select id, title, quantity, price "
                           + "From Item "
                           + "Where title Like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + keyword + "%");
                
                rs = stm.executeQuery();
                while(rs.next()) {
                    if(this.listItems == null) {
                        this.listItems = new ArrayList<>();
                    }
                    ItemDTO item = new ItemDTO();
                    item.setId(rs.getString("id"));
                    item.setTitle(rs.getString("title"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getFloat("price"));
                    
                    this.listItems.add(item);
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(stm != null) {
                stm.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }
    
    public boolean updateItemQuantity(String id, int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Update Item "
                           + "Set quantity = ? "
                           + "Where id = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, id);
                
                int row = stm.executeUpdate();
                if(row > 0) {
                    return true;
                }
            }
        } finally {
            if(stm != null) {
                stm.close();
            }
            if(con != null) {
                con.close();
            }
        }
        return false;
    }
    
    private int oldQuantity = 0;

    /**
     * @return the oldQuantity
     */
    public int getOldQuantity() {
        return oldQuantity;
    }
    
    public void findItemQuantity(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try{
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Select quantity "
                           + "From Item "
                           + "Where id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                
                rs = stm.executeQuery();
                if(rs.next()) {
                    this.oldQuantity = rs.getInt("quantity");
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(stm != null) {
                stm.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }
}
