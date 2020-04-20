/**
 * Christopher K. Leung
 * CS4600 - Cryptography and Information Security
 * Project 2 - RSA Algorithm Implementation
 * RSA_IO.java - This class will be used to handle all of the Input/Output required for the RSA algorithm.
 *               This class includes, writing keys to a binary file, reading plain text messages, reading
 *               cipher text messages, obtaining the keys from a binary file as well as storing all of RSA
 *               key components (modulus, public exponent, private exponent) internally.
 */

import java.io.*;
public class RSA_IO {
    private byte[] modulus, publicExponent, privateExponent;
    private int modSize, pubExpoSize, privateExponentSize;

    /**
     * The writeKeys method takes 3 byte arrays and 1 string to open the file output. This method will
     * attempt to open a binary file given the absolute/relative file path and initially write the sizes of
     * the modulus, public exponent and private exponent.
     * @param n - modulus byte array
     * @param e - public exponent byte array
     * @param d - private exponent byte array
     * @param file - string to file output
     * @throws IOException - throws this exception if there is an error writing to file
     */

    public void writeKeys(byte[] n, byte[] e, byte[] d, String file) throws IOException {
        FileOutputStream outStream = null;
        try{
            outStream = new FileOutputStream(file);
            outStream.write(n.length);
            outStream.write(e.length);
            outStream.write(d.length);
            outStream.write(n);
            outStream.write(e);
            outStream.write(d);
        }
        catch(FileNotFoundException m) {
            System.out.println("Cannot open file to write to.\nExiting Program Now.\n");
            System.exit(-1);
        }
        catch(IOException m){
            System.out.println("Error, cannot write to file.\nExiting Program Now.\n");
            System.exit(-1);
        }
        outStream.close();
    }

    /**
     * The readMessage method will read a plaintext file as a single string. It will parse this string
     * for any null bytes and strip them if necessary. This will return a string that has been stripped of all
     * null characters.
     * @param source - input file string
     * @return new String with no null characters
     * @throws IOException - will throw exception if file cannot be read
     */
    public String readMessage(String source) throws IOException {
        FileInputStream inStream = null;
        byte[] byteMessage = null;
        try{
            inStream = new FileInputStream(source);
            byteMessage = inStream.readAllBytes();
            byteMessage = trimNull(byteMessage);
            if(byteMessage.length > 128) {
                System.out.println("Error, message file cannot be over 128 bytes.\nExiting now\n");
                System.exit(-1);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error, cannot open message file.\nExiting Program now.\n");
            System.exit(-1);
        }
        inStream.close();
        return new String(byteMessage);
    }

    /**
     * The trimNull method will take in a byte array and parse this array for any 0x00 characters.
     * will create a new byte array to return back to caller
     * @param message - byte array that will need parsing
     * @return byte array that does not have any null characters
     */
    private byte[] trimNull(byte[] message)
    {
        int nonNullCounter = 0;
        Boolean nullFlag = false;
        for(int i = 0; i < message.length; i++) {
            if (message[i] != 0x00) {
                nonNullCounter++;
                continue;
            }
            nullFlag = true;
        }

        if(nullFlag){
            System.out.println("Null bytes detected in plaintext, stripping null bytes now.");
        }

        int counter = 0;
        int correctBytes = 0;
        byte[] returnByte = new byte[nonNullCounter];
        while(correctBytes != nonNullCounter){
            if(message[counter] != 0x00){
                returnByte[correctBytes++] = message[counter];
            }
            counter++;
        }
        return returnByte;
    }

    /**
     * The readCipher method will read in a cipher text from file into a byte array and return the byte
     * array to the caller
     * @param source - file to be opened
     * @return byte array with all contents of cipher text
     * @throws IOException - if there is an issue reading from the cipher text file
     */

    public byte[] readCipher(String source) throws IOException {
        FileInputStream inStream = null;
        byte[] byteMessage = null;
        try{
            inStream = new FileInputStream(source);
            byteMessage = inStream.readAllBytes();
        } catch (FileNotFoundException e) {
            System.out.println("Error, cannot open message file.\nExiting Program now.\n");
            System.exit(-1);
        }
        inStream.close();
        return byteMessage;
    }

    /**
     * This readKeys method will take in a file path and store the modulus, public exponent, and private exponent
     * internally. Can be used in combination with the "get" methods to obtain these values. This method will initially
     * read in the sizes and create respective byte arrays for them.
     * @param keyFile - binary file to be read
     * @throws IOException - will throw an exception if there is an issue reading the binary file
     */
    public void readKeys(String keyFile) throws IOException
    {
        FileInputStream inStream = null;
        try{
            inStream = new FileInputStream(keyFile);
            modSize = inStream.read();
            pubExpoSize = inStream.read();
            privateExponentSize = inStream.read();

            modulus = inStream.readNBytes(modSize);
            publicExponent = inStream.readNBytes(pubExpoSize);
            privateExponent = inStream.readNBytes(privateExponentSize);
        } catch (FileNotFoundException e) {
            System.out.println("Error, cannot open key file.\nExiting Program now.\n");
            System.exit(-1);
        }
        inStream.close();
    }

    /**
     * The writeBytes method will be used to write data to a file. If the file cannot be created then throw
     * and exception
     * @param file - File destination path
     * @param data - data that will be written to file
     * @throws IOException - Will throw exception if file cannot be written to
     */
    public void writeBytes(String file, byte[] data)throws IOException{
        FileOutputStream outStream = null;
        try{
            outStream = new FileOutputStream(file);
            outStream.write(data);
        }catch(FileNotFoundException m){
            System.out.println("Error, cannot open output file to write to.\nExiting program now.\n");
            System.exit(-1);
        }
        catch(IOException m){
            System.out.println("Error in writing to file.\nExiting program now.\n");
            System.exit(-1);
        }
        outStream.close();
    }

    /**
     * The getModulus method will return the modulus as a byte array
     * @return - byte array representation for the modulus
     */
    public byte[] getModulus(){ return this.modulus; }

    /**
     * The getPublicExponent method will return the public exponent as a byte array
     * @return - byte array representation for the public exponent
     */
    public byte[] getPublicExponent(){ return this.publicExponent; }

    /**
     * The getPrivateExponent method will return the private exponent as a byte array
     * @return - byte array representation for the private exponent
     */
    public byte[] getPrivateExponent(){ return this.privateExponent; }

}

