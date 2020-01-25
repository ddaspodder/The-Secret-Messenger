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

public class AES {
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
            //String ivstr="";
		try{
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			secretKey = keyGen.generateKey();
			encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
                       // ivstr=new String(iv);
                       // System.out.println(iv.toString());
		}
		catch(Exception e)
		{
		}
                return encodedKey;
	}
	public void modkey(String str)
	{
            byte[] decodedKey = Base64.getDecoder().decode(str);
            secretKey= new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
            
	}
        public void modkey2(String str,String str2)
        {
            
            byte[] decodedKey = Base64.getDecoder().decode(str);
            secretKey= new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            iv=str2.getBytes();
            System.out.println(iv.toString());
            
        }
	public String encrypt(String str)
	{
		try{
			
			final int AES_KEYLENGTH = 128;	
			iv = new byte[AES_KEYLENGTH / 8];
			SecureRandom prng = new SecureRandom();
                        System.out.println(prng.toString());
			prng.nextBytes(iv);
			Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(iv));
			strDataToEncrypt = str;
			byteDataToEncrypt = strDataToEncrypt.getBytes();
			byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);
			strCipherText = new BASE64Encoder().encode(byteCipherText);
                        
			//System.out.println("Cipher Text generated using AES is " + strCipherText);
		}
		catch(Exception e)
		{
			
		}
                return strCipherText;
	}
	public String decrypt(String str)
	{
		try{
			Cipher aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
			aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey,
					new IvParameterSpec(iv));
                        byte[] byteCipherText2 = new BASE64Decoder().decodeBuffer(str);
			byteDecryptedText = aesCipherForDecryption
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