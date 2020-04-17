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
