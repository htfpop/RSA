/**
 * Christopher K. Leung
 * CS4600 - Cryptography and Information Security
 * Project 2 - RSA Algorithm Implementation
 * RSADecrypt.java - This program will be used to decrypt a message that was previously encrypted by RSA
 *                   This program will utilize the BigInteger class to handle difficult exponentiation used
 *                   for the RSA algorithm. This program requires 3 arguments to work (cipherText source path, decrypted
 *                   message destination path, as well as the key file path).
 */
import java.io.IOException;
import java.math.BigInteger;

public class RSADecrypt {
    public static void main(String[] args) throws IOException {
        if(args.length < 3){
            System.out.printf("Error, RSADecrypt.java requires 3 files.\nProvided: %d\n",args.length);
            System.out.printf("USAGE: java RSADecrypt cipherText_SourcePath, decrypted_DestPath, key_Path\n");
            System.out.printf("Closing program now.\n");
            System.exit(-1);
        }

        String cipherTextSrc = args[0];
        String decryptedDst = args[1];
        String keySrc = args[2];

        RSA_IO RSA = new RSA_IO();
        RSA.readKeys(keySrc);
        BigInteger cipher = new BigInteger(RSA.readCipher(cipherTextSrc));
        BigInteger privateExponent = new BigInteger(RSA.getPrivateExponent());
        BigInteger mod = new BigInteger(RSA.getModulus());
        BigInteger plainText = cipher.modPow(privateExponent,mod);
        RSA.writeBytes(decryptedDst,plainText.toByteArray());
    }
}
