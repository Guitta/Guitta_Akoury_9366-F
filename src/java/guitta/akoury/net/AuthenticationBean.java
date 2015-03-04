package guitta.akoury.net;

import guitta.akoury.jsfClasses.UtilisateursController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

@ManagedBean(name = "authenticationBean")
@SessionScoped
public class AuthenticationBean {

    private String username;
    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirmation;
    private String dbuserName;
    private String dbpassword;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    String SQL;
    @ManagedProperty(value = "#{utilisateursController}")
    UtilisateursController uc;

    public UtilisateursController getUc() {
        return uc;
    }

    public void setUc(UtilisateursController uc) {
        this.uc = uc;
    }

    
    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }

    public String getDbuserName() {
        return dbuserName;
    }

    public void setDbuserName(String dbuserName) {
        this.dbuserName = dbuserName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public void dbData(String username) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gdf?zeroDateTimeBehavior=convertToNull", "root", "admin");
            statement = connection.createStatement();
            SQL = "Select * from users where username like ('" + username + "')";
            resultSet = statement.executeQuery(SQL);
            resultSet.next();
            dbuserName = resultSet.getString(1).toString();
            dbpassword = resultSet.getString(2).toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception Occured in the process :" + ex);
        }
    }

    public String changePassword() throws SQLException {
        dbData(username);

        if (username.equalsIgnoreCase(dbuserName)) {

            if (currentPassword.equals(dbpassword)) {
                if ((!this.newPassword.equals(this.currentPassword)) && (this.newPassword.equals(this.newPasswordConfirmation))) {
                    updatePassword();
                } else ////1)Checking whether the new password is equal to the current one or not
                if (this.newPassword.equals(this.currentPassword)) {
                    System.out.println("The current password and the new one can't be the same! Try again.");
                }//2)Checking whether both new password and password confirmation are the same or not
                else if (!this.newPassword.equals(this.newPasswordConfirmation)) {
                    System.out.println("The new password doesn't match with the new password confirmation! Try again.");
                }

            }

        }
        return "change_password";
    }

    private void updatePassword() throws SQLException {
        // Updating the user's crypted password in the table 'utilisateurs'...
        try {
            SQL = "UPDATE utilisateurs SET password = ? " + " WHERE username like ('" + username + "')";

            PreparedStatement pst = connection.prepareStatement(SQL);
            pst.setString(1, this.username);
            pst.setString(2, HashedPasswordGenerator.generateHash(this.newPassword));

            pst.executeUpdate();
            System.out.println("Updated Successfully!");

            connection.close();

        } catch (SQLException e) {
            System.out.println("Exception 1!");
            e.printStackTrace();
        }

    }
}
