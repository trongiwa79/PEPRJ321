/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trong.registration;

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
public class RegistrationDAO implements Serializable {
    private final String RESOURCE_NAME = "DBREG";
    
    private RegistrationDTO user = null;
    
    /**
     * @return the user
     */
    public RegistrationDTO getUser() {
        return user;
    }
    
    public void checkLogin(String username, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Select lastname, isAdmin "
                           + "From Registration "
                           + "Where username = ? And password = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                
                rs = stm.executeQuery();
                if(rs.next()) {
                    if(this.user == null) {
                        this.user = new RegistrationDTO();
                    }
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setFullname(rs.getString("lastname"));
                    user.setRole(rs.getBoolean("isAdmin"));
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

    private List<RegistrationDTO> listUsers = null;

    /**
     * @return the listUsers
     */
    public List<RegistrationDTO> getListUsers() {
        return listUsers;
    }
    
    public void searchLastname(String keyword) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Select username, password, lastname, isAdmin "
                           + "From Registration "
                           + "Where lastname Like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + keyword + "%");
                
                rs = stm.executeQuery();
                while(rs.next()) {
                    if(this.listUsers == null) {
                        this.listUsers = new ArrayList<>();
                    }
                    RegistrationDTO dto = new RegistrationDTO();
                    dto.setUsername(rs.getString("username"));
                    dto.setPassword(rs.getString("password"));
                    dto.setFullname(rs.getString("lastname"));
                    dto.setRole(rs.getBoolean("isAdmin"));
                    
                    this.listUsers.add(dto);
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
    
    public boolean deleteAccount(String pk) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Delete From Registration "
                           + "Where username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, pk);
                
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
    
    public boolean updateAccount(String username, String password, boolean isAdmin) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Update Registration "
                           + "Set password = ?, isAdmin = ? "
                           + "Where username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, isAdmin);
                stm.setString(3, username);
                
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
    
    public boolean createAccount(String username, String password, String fullname, boolean role) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Insert Into Registration (username, password, lastname, isAdmin) "
                           + "Values (?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, fullname);
                stm.setBoolean(4, role);
                
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
    
    private boolean admin = false;
    
    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }
    
    public void checkIfAdmin(String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.makeConnection(RESOURCE_NAME);
            if(con != null) {
                String sql = "Select isAdmin "
                           + "From Registration "
                           + "Where username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                
                rs = stm.executeQuery();
                if(rs.next()) {
                    this.admin = rs.getBoolean("isAdmin");
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
