import java.util.Scanner;

public class CustomCipher {

    static String decryption(String cipher,String key){
        StringBuilder s = new StringBuilder(cipher);
        s = s.reverse();
        key = key.toUpperCase();

        int keyIndex = 0;
        StringBuilder decrypt = new StringBuilder();
        for(int i=0;i<s.length();i++){
            char base = Character.isUpperCase(s.charAt(i)) ? 'A' : 'a';
            char val = (char)(((s.charAt(i) - base) ^ (key.charAt(keyIndex) - 'A'))+base);
            decrypt.append(val);
            keyIndex = (keyIndex + 1) % key.length();
        }

        return decrypt.reverse().toString();
    }

    static String encryption(String msg,String key){
        StringBuilder m = new StringBuilder(msg);
        m = m.reverse();
        key = key.toUpperCase();
        int keyIndex = 0;
        StringBuilder encrypt = new StringBuilder();
        for(int i=0;i<m.length();i++){
            char base = Character.isUpperCase(m.charAt(i)) ? 'A' : 'a';
            char c = (char) (((m.charAt(i) - base) ^ (key.charAt(keyIndex)-'A')) + base);
            encrypt.append(c);
            keyIndex = (keyIndex + 1) % key.length();
        }

        return encrypt.reverse().toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter msg:");
        String msg = sc.next();

        System.out.print("Enter key:");
        String key = sc.next();

        String cipher = encryption(msg,key);

        String decrypt = decryption(cipher,key);

        System.out.println("Encrypted string:"+cipher);
        System.out.println("Decrypted String:"+decrypt);
    }
}
