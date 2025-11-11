import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Random;
import java.util.Scanner;

class DigitalSignature{
        static void rsa() throws Exception{
        BigInteger p = BigInteger.probablePrime(256,new Random());
        BigInteger q = BigInteger.probablePrime(256, new Random());
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(65537);
        if(!phi.gcd(e).equals(BigInteger.ONE))
            e = BigInteger.valueOf(3);
        BigInteger d = e.modInverse(phi);

        System.out.println("Public key:"+e+" "+n);
        System.out.println("Private key:"+d+" "+n);

        System.out.print("Enter msg:");
        Scanner sc = new Scanner(System.in);
        String msg = sc.next();
 
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] data = digest.digest(msg.getBytes());
        String hash = HexFormat.of().formatHex(data);

        System.out.println("Hash of msg is:"+hash);

        BigInteger hashInt = new BigInteger(1,data);
        BigInteger signature = hashInt.modPow(d, n);

        System.out.println("Signature is:"+signature);

        data = digest.digest(msg.getBytes());
        String reHash = HexFormat.of().formatHex(data);

        System.out.println("Hash of msg is:"+reHash);
        
        BigInteger decrypt = signature.modPow(e, n);
        String decryptHash = decrypt.toString(16);

        System.out.println("Decrypted hash is:"+decryptHash);

        System.out.println(reHash.equals(decryptHash));
    }

    public static void main(String[] args) throws Exception{
        rsa();
    }
}

