/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityhomework1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author ahmetihsan
 */
public class AES256CBC {

    public static String initVector = "8765432187654321";

    public static String readFile(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            return everything;
        } finally {
            br.close();
        }
    }

    public static String encrypt(String key, File file) throws FileNotFoundException, IOException {
        try {
            long startTime = System.currentTimeMillis(); //encrypt işlemi için geçen süre başlangıcı

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));//init vector türü
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");// aes için private key

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//cipher çözümleme tipi
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);// cipher cinsi

            //byte[] encrypted = cipher.doFinal(Value.getBytes());
            FileInputStream inputStream = new FileInputStream(file); // dosyayı input stream ile açıyoruz
            byte[] inputBytes = new byte[(int) file.length()];  //dosyanın uzunluğunu byte cinsinden alıyoruz
            inputStream.read(inputBytes); //inputstram ile dosyayı okuyoruz


            byte[] outputBytes = cipher.doFinal(inputBytes);// şifreleme işlemi
            File outputFile = new File("/Users/ahmetihsan/NetBeansProjects/SecurityHomework1/src/securityhomework1/Aes256crypt.txt");
            //URL path = AES256CBC.class.getResource("Aes256crypt.txt");
            //File outputFile = new File(path.getFile());
            
            FileOutputStream outputStream = new FileOutputStream(outputFile); //Aes256crypt adlı yeni dosyayı file output yardımıyla
            outputStream.write(outputBytes); //üsütüne şifrelenmiş veriyi yazıyoruz

            inputStream.close();
            outputStream.close();
            long endTime = System.currentTimeMillis();
            long estimatedTime = endTime - startTime; // Geçen süreyi milisaniye cinsinden elde ediyoruz
            double seconds = (double) estimatedTime / 1000; // saniyeye çevirmek için 1000'e bölüyoruz.
            System.out.println("Geçen süre  " + seconds);

            return DatatypeConverter.printBase64Binary(inputBytes);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException
                | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static String decrypt(String key, File encrypted) throws FileNotFoundException, IOException {
        try {

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8")); //init vector türü
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES"); //aes için private key

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //cipher çözümleme tipi
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv); //cipher cinsi

            //byte[] orginal = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));
            FileInputStream inputStream = new FileInputStream(encrypted); // File input stream yardımıyla şifreleri dosyayı açıyoruz
            byte[] inputBytes = new byte[(int) encrypted.length()];//şifreli dosyanın byte sayısını alıyoruz.
            inputStream.read(inputBytes); // şifreli dosyayı okuma işlemi

            byte[] outputBytes;
            outputBytes = cipher.doFinal((inputBytes)); //şifreli dosyanın ilk haline getirilmesi

            File outputFile = new File("/Users/ahmetihsan/NetBeansProjects/SecurityHomework1/src/securityhomework1/Aes256decrypt.txt");
            //URL path = AES256CBC.class.getResource("Aes256decrypt.txt");
            //File outputFile = new File(path.getFile());
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes); //eski haline getirilen dverilerin yeni bir dosyaya yazma işlemi

            inputStream.close();
            outputStream.close();
            return new String(outputBytes);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException
                | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String key = "uxjdNijiyJDyOJ3RuxjdNijiyJDyOJ3R";
        //File f1 = new File("/Users/ahmetihsan/NetBeansProjects/SecurityHomework1/src/securityhomework1/Atsiz.txt");
        URL path = AES256CBC.class.getResource("Atsiz.txt");
        File f1 = new File(path.getFile());
        System.out.println("Encryption :");
        System.out.println(encrypt(key, f1));
        //File f2 = new File("/Users/ahmetihsan/NetBeansProjects/SecurityHomework1/src/securityhomework1/Aes256crypt.txt");
        URL path2 = AES256CBC.class.getResource("Aes256crypt.txt");
        File f2 = new File(path2.getFile());
        System.out.println("Decrypt :");
        System.out.println(decrypt(key, f2));
    }

}
