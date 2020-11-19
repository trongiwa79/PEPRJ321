/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trong.registration;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class RegistrationDeleteAccountError implements Serializable {
    private String deleteYourselfErr;
    private String deleteAdminErr;

    public RegistrationDeleteAccountError() {
    }
    
    

    /**
     * @return the deleteYourselfErr
     */
    public String getDeleteYourselfErr() {
        return deleteYourselfErr;
    }

    /**
     * @param deleteYourselfErr the deleteYourselfErr to set
     */
    public void setDeleteYourselfErr(String deleteYourselfErr) {
        this.deleteYourselfErr = deleteYourselfErr;
    }

    /**
     * @return the deleteAdminErr
     */
    public String getDeleteAdminErr() {
        return deleteAdminErr;
    }

    /**
     * @param deleteAdminErr the deleteAdminErr to set
     */
    public void setDeleteAdminErr(String deleteAdminErr) {
        this.deleteAdminErr = deleteAdminErr;
    }
    
    
}
