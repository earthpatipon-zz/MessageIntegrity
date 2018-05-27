import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
<<<<<<< HEAD
=======
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Base64;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.Base64.encodeBase64String;
>>>>>>> 38175450b8471d80b6378155cdf630b6b1e7ad15

public class Sender {

	private String hash;

	public Sender() {
	}

	public void send(String algorithm, String text) throws IOException {
		writeFile("email", text);

		switch (algorithm) {
		case "SHA-256":
			try {
				hash = sha256(text);
				writeFile("checksum", hash);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		case "MD5":
			try {
				hash = md5(text);
				writeFile("checksum", hash);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
<<<<<<< HEAD
=======

		byte[] hashBytes = hash.getBytes();
		
		// Encrypt hash value with private key of the sender
//		Signature signature = Signature.getInstance("SHA1withRSA");
//		signature.initSign(privateKey);
//		signature.update(hashBytes);
//
//		byte[] digitalSignature = signature.sign();
//		
//		signature.initVerify(publicKey);
//		signature.update(hashBytes);
		
		byte[] digitalSignature = encryptor(privateKey, hashBytes.toString(), "RSA");
		
		System.out.println("\ndigital signature: "+digitalSignature);

		// Create session key and encrypt it
		SecureRandom random = new SecureRandom();
		byte[] aesBytes = new byte[16];
		random.nextBytes(aesBytes);
		SecretKey sessionKey = new SecretKeySpec(aesBytes, "AES");
		
		System.out.println("1: ---------------------");
		byte[] encryptedSessionKey = encryptor(recipientPublicKey, sessionKey.toString(), "RSA");
		System.out.println("\nsession key: "+encryptedSessionKey);
//
//		Cipher encryptedSessionKey = Cipher.getInstance("RSA");
//		encryptedSessionKey.init(Cipher.ENCRYPT_MODE, recipientPublicKey);
//		encryptedSessionKey.doFinal(sessionKey.toString().getBytes());

		// Encrypt the pain text using the session key
		byte[] cipherText = encryptor(sessionKey, text, "AES");
		System.out.println("\ncipher text: "+cipherText);
//		Cipher cipher = Cipher.getInstance("AES");
//		cipher.init(Cipher.ENCRYPT_MODE, sessionKey);
//		cipher.doFinal(text.getBytes());
//
//		System.out.println("\ncipher: "+cipher+"\n");
//		System.out.println("\ncipher: "+cipher.doFinal(text.getBytes())+"\n");
		
		String message = encryptedSessionKey + "," + digitalSignature + "," + cipherText;

//		String message = encryptedSessionKey.doFinal(sessionKey.toString().getBytes()) + "," + digitalSignature + "," + cipher.doFinal(text.getBytes());

		// Radix-64 conversion
		String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());

		writeFile("email", encodedMessage);
>>>>>>> 38175450b8471d80b6378155cdf630b6b1e7ad15
	}

	public void writeFile(String filename, String text) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter("inbox/" + filename + ".txt");
			bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String md5(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(text.getBytes());

		// convert byte to hex
		byte[] mdBytes = md.digest();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			// set strBuffer
			strBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println("Checksum with MD5 (Sender): " + strBuffer.toString());
		return strBuffer.toString();
	}

	public String sha1(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(text.getBytes());

		// convert byte to hex
		byte[] mdBytes = md.digest();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			// set strBuffer
			strBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println("Checksum with SHA-1 (Sender): " + strBuffer.toString());
		return strBuffer.toString();
	}
	
<<<<<<< HEAD
	public String sha256(String text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes());

		// convert byte to hex
		byte byteData[] = md.digest();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				strBuffer.append('0');
			strBuffer.append(hex);
		}
		System.out.println("Checksum with SHA-256 (Sender): " + strBuffer.toString());
		return strBuffer.toString();
=======
	private static byte[] encryptor(Key key, String text, String algo) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = Cipher.getInstance(algo);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		System.out.println("\n-------------------------------------------------------");
		System.out.println(cipher.doFinal(text.getBytes()));
		System.out.println(Base64.getEncoder().encode(cipher.doFinal(text.getBytes())));
		
		return Base64.getEncoder().encode(cipher.doFinal(text.getBytes()));
>>>>>>> 38175450b8471d80b6378155cdf630b6b1e7ad15
	}
}
