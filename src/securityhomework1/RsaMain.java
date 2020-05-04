/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityhomework1;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author ahmetihsan
 */
public class RsaMain {
    public static void main(String[] args) {
        
        BigInteger k1 = new BigInteger(128, new Random());
        System.out.println("K1 128 bit " +k1);
        BigInteger k2 = new BigInteger(256, new Random());
        System.out.println("K2 256 bit " +k2);
        
        String mesaj = "alican";
        System.out.println("K1 128 bit");
        RSA rsa = new RSA("ali");     
        System.out.println(rsa.encryptoText(k1));
        System.out.println(rsa.decryptoText(k1));
        
         System.out.println("K2 256 bit");
         RSA rsa1 = new RSA("alican");
        System.out.println(rsa1.encryptoText(k2));
        System.out.println(rsa1.decryptoText(k2));
        
    }
}
