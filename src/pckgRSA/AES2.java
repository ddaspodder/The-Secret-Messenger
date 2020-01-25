package pckgRSA;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

import sun.misc.BASE64Encoder;

public class AES2 {
	SecretKey secretKey;
	byte[] iv;
	String strDataToEncrypt;
	byte[] byteDataToEncrypt;
	byte[] byteCipherText;
	String strCipherText;
	byte[] byteDecryptedText;
	String strDecryptedText;
	public String gen()
	{
            String encodedKey="";
            String encoded="";
		try{
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			secretKey = keyGen.generateKey();
			encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
			final int AES2_KEYLENGTH = 128;	
			iv = new byte[AES2_KEYLENGTH / 8];
			SecureRandom prng = new SecureRandom();
			prng.nextBytes(iv);
                        System.out.println(new String(iv));
                       /* ivstr = new String(Base64.getEncoder().encode(iv));
                        System.out.println(ivstr);
                       byte[] iv = Base64.getDecoder().decode(ivstr.getBytes());
                       System.out.println(iv.toString());*/
                        //System.out.println(iv.toString());
                        encoded = Base64.getEncoder().encodeToString(iv);
                        System.out.println(encoded);       // Outputs "SGVsbG8="

                        
		}
		catch(Exception e)
		{
                    System.out.println(e);
		}
                return (encodedKey+";"+encoded);
	}
	public void modkey(String str)
	{
            byte[] decodedKey = Base64.getDecoder().decode(str);
            secretKey= new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
            
	}
        public void modkey2(String str,String str2)
        {
            System.out.println(str);
            System.out.println(str2);
            byte[] decodedKey = Base64.getDecoder().decode(str);
            secretKey= new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
          //  byte[] iv = Base64.getDecoder().decode(str2);
          //  System.out.println(iv.toString());
            String decoded = new String(Base64.getDecoder().decode(str2.getBytes()));
            System.out.println(decoded);
            iv=decoded.getBytes();
        }
	public String encrypt(String str)
	{
		try{
			
			Cipher AES2CipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding");
			AES2CipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(iv));
			strDataToEncrypt = str;
			byteDataToEncrypt = strDataToEncrypt.getBytes();
			byteCipherText = AES2CipherForEncryption.doFinal(byteDataToEncrypt);
			strCipherText = new BASE64Encoder().encode(byteCipherText);
                        
			//System.out.println("Cipher Text generated using AES2 is " + strCipherText);
		}
		catch(Exception e)
		{
			
		}
                return strCipherText;
	}
	public String decrypt(String str)
	{
		try{
			Cipher AES2CipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
			AES2CipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey,
					new IvParameterSpec(iv));
                        byte[] byteCipherText2 = new BASE64Decoder().decodeBuffer(str);
			byteDecryptedText = AES2CipherForDecryption
					.doFinal(byteCipherText2);
			strDecryptedText = new String(byteDecryptedText);
			System.out.println(" Decrypted Text message is " + strDecryptedText);
		}
		catch(Exception e)
		{
			
		}
                return strDecryptedText;
	}
	
	
}