import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Sender {

	private String hash;
	private String path;
	private KeyPair key;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public Sender() throws NoSuchAlgorithmException {
		key = KeyPairGenerator.getInstance("RSA").generateKeyPair();
		privateKey = key.getPrivate();
		publicKey = key.getPublic();
	}

	public void send(String algorithm, String text, PublicKey recipientPublicKey) throws IOException, NoSuchAlgorithmException, InvalidKeyException,
			SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
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
		case "SHA-1":
			try {
				hash = sha1(text); // 160-bits hash code
				writeFile("checksum", hash);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

		byte[] hashBytes = hash.getBytes();
		
		// Encrypt hash value with private key of the sender
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(privateKey);
		signature.update(hashBytes);

		byte[] digitalSignature = signature.sign();
		
		signature.initVerify(publicKey);
		signature.update(hashBytes);

		// Create session key and encrypt it
		SecureRandom random = new SecureRandom();
		byte[] aesBytes = new byte[16];
		random.nextBytes(aesBytes);
		SecretKey sessionKey = new SecretKeySpec(aesBytes, "AES");

		Cipher encryptedSessionKey = Cipher.getInstance("RSA");
		encryptedSessionKey.init(Cipher.ENCRYPT_MODE, recipientPublicKey);
		encryptedSessionKey.doFinal(sessionKey.toString().getBytes());

		// Encrypt the pain text using the session key
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, sessionKey);
		cipher.doFinal(text.getBytes());

		String message = encryptedSessionKey + "," + digitalSignature + "," + cipher;

		// Radix-64 conversion
		String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());

		writeFile("email", encodedMessage);
	}

	public void writeFile(String filename, String text) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			path = "inbox/" + filename + ".txt";
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getPath() {
		return path;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

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
		
		System.out.println("\n\n"+md.toString());

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
}
