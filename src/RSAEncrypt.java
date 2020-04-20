/**
 * Christopher K. Leung
 * CS4600 - Cryptography and Information Security
 * Project 2 - RSA Algorithm Implementation
 * RSAEncrypt.java - This program will be utilized to encrypt a message. This program requires the plaintext message to be
 *                   less than or equal to 128 bytes. This program takes 3 arguments (plain text source path, encrypted
 *                   destination path, and key file path).
 */

import java.io.IOException;
import java.math.BigInteger;

public class RSAEncrypt {
    public static void main(String[] args) throws IOException {
        if(args.length < 3){
            System.out.printf("Error, RSAEncrypt.java requires 3 files.\nProvided: %d\n",args.length);
            System.out.printf("USAGE: java RSAEncrypt plainText_SourcePath, cipherText_DestPath, key_Path\n");
            System.out.printf("Closing program now.\n");
            System.exit(-1);
        }
        String plainTextSrc = args[0];
        String cipherDest = args[1];
        String keySrc = args[2];

        RSA_IO RSA = new RSA_IO();
        RSA.readKeys(keySrc);
        String message = RSA.readMessage(plainTextSrc);
        BigInteger plainText = new BigInteger(message.getBytes());
        BigInteger pubKey = new BigInteger(RSA.getPublicExponent());
        BigInteger mod = new BigInteger(RSA.getModulus());
        BigInteger cipherText = plainText.modPow(pubKey,mod);
        RSA.writeBytes(cipherDest,cipherText.toByteArray());


    }
}
