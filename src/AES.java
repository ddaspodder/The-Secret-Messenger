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
		try{
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			secretKey = keyGen.generateKey();
			encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
			
		}
		catch(Exception e)
		{
		}
                return encodedKey;
	}
	public void encrypt(String str)
	{
		try{
			final int AES_KEYLENGTH = 128;	
			iv = new byte[AES_KEYLENGTH / 8];
			SecureRandom prng = new SecureRandom();
			prng.nextBytes(iv);
			Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(iv));
			strDataToEncrypt = str;
			byteDataToEncrypt = strDataToEncrypt.getBytes();
			byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);
			strCipherText = new BASE64Encoder().encode(byteCipherText);
			System.out.println("Cipher Text generated using AES is " + strCipherText);
		}
		catch(Exception e)
		{
			
		}
	}
	public void decrypt()
	{
		try{
			Cipher aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
			aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey,
					new IvParameterSpec(iv));
			byteDecryptedText = aesCipherForDecryption
					.doFinal(byteCipherText);
			strDecryptedText = new String(byteDecryptedText);
			System.out.println(" Decrypted Text message is " + strDecryptedText);
		}
		catch(Exception e)
		{
			
		}
	}
	
	
}