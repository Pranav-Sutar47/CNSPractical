import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

class DiffieHellman{

    static int findPrimitive(int p){
        for(int i=2;i<p-1;i++){
            if(isPrimitive(i,p))
                return i;
        }
        return -1;
    }

    static boolean isPrimitive(int g,int p){
        boolean[] seen = new boolean[p];
        int val = 1;
        for(int i=0;i<p-1;i++){
            val = (val * g) % p;
            if(seen[val])
                return false;
            seen[val] = true;
        }
        return true;
    } 

    static void diffie(Scanner sc){
        System.out.print("Enter prime no p:");
        int p = sc.nextInt();
        int g = findPrimitive(p);

        Random random = new Random();
        int a = random.nextInt(20)+1;
        int b = random.nextInt(20)+2;

        BigInteger x = BigInteger.valueOf(g).modPow(BigInteger.valueOf(a), BigInteger.valueOf(p));
        BigInteger y = BigInteger.valueOf(g).modPow(BigInteger.valueOf(b), BigInteger.valueOf(p));

        System.out.println("Public key of Alice:"+x);
        System.out.println("Public key of Bob:"+y);

        BigInteger sa = y.modPow(BigInteger.valueOf(a), BigInteger.valueOf(p));
        BigInteger sb = x.modPow(BigInteger.valueOf(b), BigInteger.valueOf(p));

        System.out.println("Secret key of Alice is:"+sa);
        System.out.println("Secret key of Bob is:"+sb);
        if(sa.equals(sb))
            System.out.println("Secured Communication.....");
    }

    static void manInMiddle(Scanner sc){
        System.out.print("Enter prime no p:");
        int p = sc.nextInt();
        int g = findPrimitive(p);

        Random random = new Random();
        int a = random.nextInt(20)+1;
        int b = random.nextInt(20)+2;

        BigInteger x = BigInteger.valueOf(g).modPow(BigInteger.valueOf(a), BigInteger.valueOf(p));
        BigInteger y = BigInteger.valueOf(g).modPow(BigInteger.valueOf(b), BigInteger.valueOf(p));

        int c = 5, d = 10;

        BigInteger px = BigInteger.valueOf(g).modPow(BigInteger.valueOf(d), BigInteger.valueOf(p));
        BigInteger py = BigInteger.valueOf(g).modPow(BigInteger.valueOf(c), BigInteger.valueOf(p));
   
        System.out.println("Original Public key of Alice:"+x);
        System.out.println("Original Public key of Bob:"+y);
        System.out.println("Public key of Alice from Mallory:"+px);
        System.out.println("Public key of Bob from Mallory:"+py);

        BigInteger sa = px.modPow(BigInteger.valueOf(a), BigInteger.valueOf(p));
        BigInteger sb = py.modPow(BigInteger.valueOf(b), BigInteger.valueOf(p));

        BigInteger sma = x.modPow(BigInteger.valueOf(d), BigInteger.valueOf(p));
        BigInteger smb = y.modPow(BigInteger.valueOf(c), BigInteger.valueOf(p));

        System.out.println("Secret key of Alice:"+sa);
        System.out.println("Secret key of Mallory with Alice:"+sma);

        System.out.println("Secret key of Bob:"+sb);
        System.out.println("Secret key of Mallory with Bob:"+smb);
    }

    public static void main(String[] args) {
        int ch = 1;
        do{
            System.out.println("1.Diffie Hellman Key-Exchange");
            System.out.println("2.Man In The Middle Attack");
            System.out.println("3.Exit");
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter choice:");
            ch = sc.nextInt();

            switch (ch) {
                case 1:
                    diffie(sc);
                    break;
            
                case 2:
                    manInMiddle(sc);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }while(ch != 3);
    }
}