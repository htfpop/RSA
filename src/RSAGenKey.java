/**
 * Christopher K. Leung
 * CS4600 - Cryptography and Information Security
 * Project 2 - RSA Algorithm Implementation
 * RSAGenKey.java - This program will be used to generate the key components for RSA. This will generate a 128 byte modulus
 *                  that will be used during the encryption/decryption process. This program will also ensure that the 2 primes
 *                  generated are in fact prime, as well as other quality assurance checks along the way. This program will take
 *                  1 argument (key destination path).
 */
import java.io.*;
import java.math.BigInteger;
import java.util.Random;

public class RSAGenKey {
    private static final int BITLENGTH = 512;
    private static final int MODBITLENGTH = 1024;
    public static void main(String args[]) throws IOException {
        BigInteger firstPrime = BigInteger.ZERO;
        BigInteger secondPrime = BigInteger.ZERO;
        BigInteger modulus = BigInteger.valueOf(0);
        BigInteger pubExpo = BigInteger.valueOf(65537);
        BigInteger privExpo = BigInteger.valueOf(0);
        BigInteger phi_n = BigInteger.valueOf(0);
        Boolean validParams = false;

        if(args.length < 1) {
            System.out.printf("Error, please provide file destination for key generation.\nExiting Program now.\n");
            System.exit(-1);
        }

        String keyFile = args[0];

        /** check for valid data that was generated by BigInteger class. if any conditions fail regenerate new pair of primes **/
        while(!validParams)
        {
            firstPrime = genPrime();
            secondPrime = genPrime();
            modulus = firstPrime.multiply(secondPrime);
            phi_n = (firstPrime.subtract(BigInteger.valueOf(1))).multiply(secondPrime.subtract(BigInteger.valueOf(1)));
            privExpo = pubExpo.modInverse(phi_n);
            validParams = checkParams(firstPrime, secondPrime, modulus, privExpo, pubExpo, phi_n);
        }

        /** Instantiation of RSA object to write modulus, public exponent, and private exponent to file **/
        RSA_IO RSA = new RSA_IO();
        RSA.writeKeys(modulus.toByteArray(), pubExpo.toByteArray(), privExpo.toByteArray(), keyFile);
    }

    /**
     * This method conducts quality checks on RSA prime numbers, public/private exponents and phi_n
     * @param p - Prime integer 1 - first prime generated using genPrime()
     * @param q - Prime integer 2 - second prime generated using genPrime()
     * @param n - p * q
     * @param d - e^-1 mod phi(n)
     * @param e - 65537
     * @param phi_n - (p-1)(q-1)
     * @return boolean value - true if conditions all pass / false if any condition fails
     **/
    private static boolean checkParams(BigInteger p, BigInteger q, BigInteger n, BigInteger d, BigInteger e, BigInteger phi_n){
        if(!(p.isProbablePrime(10))) return false;                      /* if p is not prime return false */
        if(!(q.isProbablePrime(10))) return false;                      /* if q is not prime return false */
        if(!(n.bitLength() <= MODBITLENGTH)) return false;                      /* if n's bitlength is greater than the modulus bit length return false */
        if(p.equals(q)) return false;                                           /* if p == q return false */
        if(!(e.gcd(phi_n)).equals(BigInteger.ONE)) return false;                /* if gcd(e,phi_n) != 1 return false */
        if(!(d.multiply(e).mod(phi_n)).equals(BigInteger.ONE)) return false;    /* if d * e mod phi_n != 1 return false */
        if(!((e.multiply(d)).mod(phi_n)).equals(BigInteger.ONE)) return false;  /* if e* d mod phi_n != 1 return false */
        if((d.bitCount() > n.bitCount()) || ((n.bitCount() - d.bitCount()) > 15)) return false; /* if d's bitcount is > n's bitlength OR
                                                                                                    the difference in bits is > 15 return false (project specs) */
        return true;
    }

    /**
     *  genPrime function generates large prime numbers using the BigInteger class
     *  Utilizes Probable prime = (1 - 1/2^certainty) to calculate the probability that a number is prime
     *
     * @return BigInteger that is at least 99.9% prime (truncated due to int cast)
     *  **/
    private static BigInteger genPrime(){
        Random rand = new Random();
        double certainty = Math.log(1/0.001)/Math.log(2);
        return new BigInteger(BITLENGTH, (int) certainty, rand);
    }

}
