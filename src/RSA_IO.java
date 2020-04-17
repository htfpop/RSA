import java.io.*;

public class RSA_IO {
    private byte[] modulus, publicExponent, privateExponent;
    private int modSize, pubExpoSize, privateExponentSize;

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

    public String readMessage(String source) throws IOException {
        FileInputStream inStream = null;
        byte[] byteMessage = null;
        try{
            inStream = new FileInputStream(source);
            byteMessage = inStream.readAllBytes();
            byteMessage = trimNull(byteMessage);
        } catch (FileNotFoundException e) {
            System.out.println("Error, cannot open message file.\nExiting Program now.\n");
            System.exit(-1);
        }
        inStream.close();
        return new String(byteMessage);
    }

    private byte[] trimNull(byte[] message)
    {
        int nonNullCounter = 0;
        for(int i = 0; i < message.length; i++) {
            if (message[i] != 0x00)
                nonNullCounter++;
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

    public byte[] getModulus(){ return this.modulus; }
    public byte[] getPublicExponent(){ return this.publicExponent; }
    public byte[] getPrivateExponent(){ return this.privateExponent; }
















//    private byte[] modulus;
//    private byte[] privateExponent;
//    private byte[] publicExponent;
//    private int modSize, privExpoSize, pubExpoSize;
//
//    public RSA_Keys(){
//        this(null,null,null);
//        this.modSize = 0;
//        this.privExpoSize = 0;
//        this.pubExpoSize = 0;
//    }
//
//    public RSA_Keys(byte[] modulus, byte[] pubExpo, byte[] privExpo){
//        this.modulus = modulus;
//        this.publicExponent = pubExpo;
//        this.privateExponent = privExpo;
//        this.modSize = modulus.length;
//        this.privExpoSize = privExpo.length;
//        this.pubExpoSize = pubExpo.length;
//    }
//
//    private byte[] getMod(){ return modulus; }
//
//    private byte[] getPrivateExponent(){ return privateExponent; }
//
//    private byte[] getPublicExponent(){ return publicExponent; }
//
//    private void writeBytes(String inputFile){
//        try {
//            FileOutputStream fos = new FileOutputStream(inputFile);
//            fos.write(modSize);
//            fos.write(pubExpoSize);
//            fos.write(privExpoSize);
//            fos.write(this.modulus);
//            fos.write(this.publicExponent);
//            fos.write(this.privateExponent);
//        }catch(IOException e){
//            System.out.println(e.getMessage());
//            System.exit(-1);
//        }
//    }

//    private boolean readBytes(String inputFile){
//
//    }



//    public RSA_IO(String destination) throws IOException {
//        try{ outStream = new FileOutputStream(destination);}
//        catch(IOException e) {
//            System.out.println(e.getMessage());
//            System.exit(-1);
//        }
//    }
//
//    public RSA_IO(String source, String destination, String key)throws IOException{
//        try{ inStream = new FileInputStream(source);}
//        catch(IOException e){
//            System.out.println(e.getMessage());
//            System.exit(-1);
//        }
//
//        try{ outStream = new FileOutputStream(destination);}
//        catch(IOException e){
//            System.out.println(e.getMessage());
//            System.exit(-1);
//        }
//
//        try{ keyStream = new FileInputStream(key);}
//        catch(IOException e){
//            System.out.println(e.getMessage());
//            System.exit(-1);
//        }
//    }
//
//    public void writeBytes(byte[] outByteArray){
//
//    }

}

