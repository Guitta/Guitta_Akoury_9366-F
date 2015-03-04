/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitta.akoury.net;

/**
 *
 * @author Guitta
 */
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class HashedPasswordGenerator {
    public static String generateHash (String password){
        String output = Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
        return output;
    }    
}
