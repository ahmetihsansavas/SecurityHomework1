/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityhomework1;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author ahmetihsan
 */
//1.soru
public class RSA {

    Random r = new Random();
    private static char[] myArray;
    private static char[] cryptoText;
    BigInteger nBig;
    BigInteger eBig;
    BigInteger dBig;
    BigInteger asciiBig;
    String mesaj;
    String key;

    public RSA(String mesaj) {

        this.mesaj = mesaj;

        myArray = mesaj.toCharArray();
        //öncelikle asal p ve q sayıları seçiyoruz ve bu doğrultuda n z e yi belirlemek 
        //için aşağıdaki işlemleri yapıyoruz
        int p = 59;
        int q = 61;
        int n = p * q;
        int z = (p - 1) * (q - 1);
        int e;
        Random random = new Random();
        do {

            e = random.nextInt(z);

        } while (!asalMi(e));

        int d = dHesaplama(z, e);

        nBig = BigInteger.valueOf(n);
        eBig = BigInteger.valueOf(e);
        dBig = BigInteger.valueOf(d);
        System.out.println("public key (" + n + "," + e + " )");
        System.out.println("private key (" + n + "," + d + " )");
    }
//e nin asal sayı kontrolünü yapıyoruz.
    private static boolean asalMi(int e) {

        for (int i = 2; i < e; i++) {

            if (e % i == 0) {
                return false;
            }
        }

        return true;
    }
    
// d sayısını seçerken öyle bir d sayısı olmalı ki z*d+1 mod e == 0 olmalı
    private static int dHesaplama(int z, int e) {

        for (int k = 1; k < z; k++) {

            if (((z * k + 1) % e) == 0) {

                return (((z * k) + 1) / e);
            }
        }

        return 0;
    }
   
    // Dhesaplama2 yi hem encrypt heöde decrypt yaparken kullanıyoruz.
    private static BigInteger dHesaplama2(BigInteger nBig, BigInteger ascii, BigInteger e,BigInteger key) {

        BigInteger asciiExpE = BigInteger.valueOf(1);
      
        for (int i = 1; i <= e.intValue(); i++) {

            asciiExpE = ascii.multiply(asciiExpE);
            //asciiExpE = ascii.multiply(key);
        }

        BigInteger c = BigInteger.valueOf(1);
        c = ascii.multiply(key);
        
        c = asciiExpE.mod(nBig);

        return c;
    }
    
// encrypt text kodu parametre olarak key alıyor
    public char[] encryptoText(BigInteger key) {

        cryptoText = new char[myArray.length];

        int cryptoIndex = 0;

        for (char letter : myArray) {

            int ascii = (int) letter;

            asciiBig = BigInteger.valueOf(ascii);

            BigInteger c = dHesaplama2(nBig, asciiBig, eBig,key);

            cryptoText[cryptoIndex++] = (char) c.intValue();
        }

        return cryptoText;

    }
    
    //decrypt text kodu 
    public char[] decryptoText(BigInteger key) {

        char[] decryptoText = new char[myArray.length];

        int decryptoIndex = 0;

        for (char letter : cryptoText) {

            BigInteger c = BigInteger.valueOf((int) letter);

            BigInteger de = dHesaplama2(nBig, c, dBig,key);

            decryptoText[decryptoIndex++] = (char) de.intValue();

        }

        return decryptoText;

    }
}
