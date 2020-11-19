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
public class RegistrationUpdateAccountError implements Serializable {
    private String passwordLengthErr;
    private String updateYourselfErr;
    private String updateAdminRoleErr;

    public RegistrationUpdateAccountError() {
    }

    /**
     * @return the passwordLengthErr
     */
    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    /**
     * @param passwordLengthErr the passwordLengthErr to set
     */
    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    /**
     * @return the updateYourselfErr
     */
    public String getUpdateYourselfErr() {
        return updateYourselfErr;
    }

    /**
     * @param updateYourselfErr the updateYourselfErr to set
     */
    public void setUpdateYourselfErr(String updateYourselfErr) {
        this.updateYourselfErr = updateYourselfErr;
    }

    /**
     * @return the updateAdminRoleErr
     */
    public String getUpdateAdminRoleErr() {
        return updateAdminRoleErr;
    }

    /**
     * @param updateAdminRoleErr the updateAdminRoleErr to set
     */
    public void setUpdateAdminRoleErr(String updateAdminRoleErr) {
        this.updateAdminRoleErr = updateAdminRoleErr;
    }
    
    
    
}
