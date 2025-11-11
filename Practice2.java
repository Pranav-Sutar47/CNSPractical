import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

class Practice2{

    static int findE(int p,int q,int n,int phi){
        for(int i=2;i<phi;i++){
            if(gcd(i,phi) == 1)
                return i;
        }
        return -1;
    }

    static int gcd(int e,int phi){
        if(e==0)
            return phi;
        else 
            return gcd(phi%e,e);
    }

    static int findD(int p,int q,int n,int phi,int e){
        for(int i=1;i<=9;i++){
            int x = 1 + (i * phi);
            if(x % e == 0)
                return x/e;
        }
        return -1;
    }

    static void rsa(Scanner sc){
        System.out.print("Enter p and q:");
        int p = sc.nextInt();
        int q = sc.nextInt();
        Instant start = Instant.now();
        int n = p * q;
        int phi = (p-1) * (q-1);

        int e = findE(p,q,n,phi);
        int d = findD(p,q,n,phi,e);
        Instant stop = Instant.now();

        System.out.println("Private key:"+d+" "+n);
        System.out.println("Public key:"+e+" "+n);
        Duration duration = Duration.between(start, stop);
        System.out.println("Time to generate key:"+duration.toMillis());

        System.out.print("Enter msg:");
        String text = sc.next();
        start = Instant.now();
        int[] cipher = new int[text.length()];

        for(int i=0;i<text.length();i++){
            int c = text.charAt(i) - 'A';
            BigInteger val = BigInteger.valueOf(c).modPow(BigInteger.valueOf(e), BigInteger.valueOf(n));
            cipher[i] = val.intValue();
        }
        stop = Instant.now();
        System.out.println("Encrypted text is:"+Arrays.toString(cipher));
        duration = Duration.between(start, stop);
        System.out.println("Time to encrypt msg:"+duration.toMillis());

        start = Instant.now();
        StringBuilder decrypt = new StringBuilder();
        for(int i=0;i<cipher.length;i++){
            BigInteger val = BigInteger.valueOf(cipher[i]).modPow(BigInteger.valueOf(d), BigInteger.valueOf(n));
            char c = (char)(val.intValue() + 'A');
            decrypt.append(c);
        }
        stop = Instant.now();
        duration = Duration.between(start,stop);
        System.out.println("Decrypted text is:"+decrypt);
        System.out.println("Time to decrypt msg:"+duration.toMillis());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        rsa(sc);
    }
}