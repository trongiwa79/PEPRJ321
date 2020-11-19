/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trong.order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import trong.utils.DBHelper;

/**
 *
 * @author DELL
 */
public class OrderDAO implements Serializable {
    private final String RESOURCE_NAME = "DBSHOP";
    
    public boolean checkOut(OrderDTO order) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Insert Into [Order] (itemId, title, quantity, total, custId) "
                           + "Values (?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, order.getItemId());
                stm.setString(2, order.getTitle());
                stm.setInt(3, order.getQuantity());
                stm.setFloat(4, order.getTotal());
                stm.setString(5, order.getCustId());
                
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
    
    
}
