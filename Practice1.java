import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Practice1 {

    static void caesar(Scanner sc){
        System.out.print("Enter text:");
        String text = sc.next();
        System.out.println("Enter key:(1-25):");
        int key = sc.nextInt();

        StringBuilder cipher = new StringBuilder();
        for(int i=0;i<text.length();i++){
            char base = Character.isUpperCase(text.charAt(i)) ? 'A' : 'a';
            char val = (char) ((text.charAt(i) - base + key) % 26 + base);
            cipher.append(val);
        }
        System.out.println("Encrypted Text is:"+cipher);

        StringBuilder plane = new StringBuilder();
        for(int i=0;i<cipher.length();i++){
            char base = Character.isUpperCase(cipher.charAt(i)) ? 'A' : 'a';
            char val = (char)((cipher.charAt(i) - base - key + 26) % 26 + base);
            plane.append(val);
        }
        System.out.println("Decrypted Text is:"+plane);
    }

    static void monoAlphabetic(Scanner sc){
        System.out.print("Enter text:");
        String text = sc.next();
        System.out.println("Enter 26 letters key:");
        String key = sc.next();
        if(key.length() != 26){
            System.out.println("Invalid key");
            return;
        }

        HashMap<Character,Character> map = new HashMap<>();
        char ch = 'A';
        char ch1 = 'a';
        for(char c:key.toCharArray()){
            map.put(ch++, c);
            map.put(ch1++,Character.toLowerCase(c));
        }
        StringBuilder cipher = new StringBuilder();
        for(char c:text.toCharArray())
            cipher.append(map.get(c));
        System.out.println("Encrypted Text:"+cipher);

        HashMap<Character,Character> map2 = new HashMap<>();
        for(Map.Entry<Character,Character> e:map.entrySet())
            map2.put(e.getValue(),e.getKey());
        
        StringBuilder plane = new StringBuilder();
        for(char c:cipher.toString().toCharArray())
            plane.append(map2.get(c));
        System.out.println("Decrypted Text:"+plane);
    }
   
    static void polyAlphabetic(Scanner sc){
        System.out.print("Enter text:");
        String text = sc.next();
        System.out.print("Enter key:(UPPERCASE only):");
        String key = sc.next();

        int keyIndex = 0;
        StringBuilder cipher = new StringBuilder();
        for(int i=0;i<text.length();i++){
            char base = Character.isUpperCase(text.charAt(i)) ? 'A' : 'a';
            int shift =  key.charAt(keyIndex) - 'A';
            char c = (char) ((text.charAt(i) - base + shift) % 26 + base);  
            cipher.append(c);
            keyIndex = (keyIndex + 1) % key.length(); 
        }
        System.out.println("Encrypted text is:"+cipher);

        StringBuilder plane = new StringBuilder();
        keyIndex = 0;
        for(int i=0;i<cipher.length();i++){
            char base = Character.isUpperCase(cipher.charAt(i)) ? 'A' : 'a';
            int shift = key.charAt(keyIndex) - 'A';
            char c = (char)((cipher.charAt(i) - base - shift + 26) % 26 + base);
            plane.append(c);
            keyIndex = (keyIndex + 1) % key.length();
        }

        System.out.println("Decrypted Text is:"+plane);
    }

    static void vernam(Scanner sc){
        System.out.print("Enter text:");
        String text = sc.next();
        System.out.print("Enter key:(UPPERCASE only):");
        String key = sc.next();
        if(text.length() != key.length()){
            System.out.println("Key must be of same length");
            return;
        }
        StringBuilder cipher = new StringBuilder();
        for(int i=0;i<text.length();i++){
            char base = Character.isUpperCase(text.charAt(i)) ? 'A' : 'a';
            char c = (char) (((text.charAt(i)-base) ^ (key.charAt(i)-'A')) + base);
            cipher.append(c);
        }
        System.out.println("Encrypted text is:"+cipher);
        StringBuilder plane = new StringBuilder();
        for(int i=0;i<cipher.length();i++){
            char base = Character.isUpperCase(text.charAt(i)) ? 'A' : 'a';
            char c = (char) (((cipher.charAt(i) - base) ^ (key.charAt(i) - 'A')) + base);
            plane.append(c);
        }
        System.out.println("Decrypted text is:"+plane);
    }

    static void oneTimePad(Scanner sc){
        System.out.print("Enter msg:");
        String msg = sc.next();
        System.out.print("Enter key:");
        String key = sc.next();
        key = key.toUpperCase();
        if(msg.length() != key.length())
            System.out.println("Key must be of same length");
        int[] cipher = new int[msg.length()];
        StringBuilder encrypt = new StringBuilder();

        for(int i=0;i<cipher.length;i++){
            char base = Character.isUpperCase(msg.charAt(i)) ? 'A' : 'a';
            cipher[i] = (msg.charAt(i) - base) + (key.charAt(i) - 'A');
            if(cipher[i] > 25)
                cipher[i] -= 26;
            char c = (char) (cipher[i] + base);
            encrypt.append(c);
        }

        System.out.println("Encrypt msg:"+encrypt);

        StringBuilder decrypt = new StringBuilder();
        int[] plane = new int[cipher.length];
        for(int i=0;i<encrypt.length();i++){
            char base = Character.isUpperCase(encrypt.charAt(i)) ? 'A' : 'a';
            plane[i] = (encrypt.charAt(i) - base) - (key.charAt(i) - 'A');
            if(plane[i] < 0)
                plane[i] += 26;
            char c = (char) (plane[i] + base);
            decrypt.append(c);
        }
        System.out.println("Decrypt msg:"+decrypt);
    }

    static void railFence(Scanner sc){
        System.out.print("Enter msg:");
        String msg = sc.next();
        System.out.print("Enter rails:");
        int rails = sc.nextInt();

        StringBuilder[] fence = new StringBuilder[rails];
        for(int i=0;i<rails;i++)
            fence[i] = new StringBuilder();

        int rail = 0;
        int dir = 1;
        for(char c:msg.toCharArray()){
            fence[rail].append(c);
            rail += dir;
            if(rail == 0 || rail == rails-1)
                dir = -dir;
        }

        StringBuilder encrypt = new StringBuilder();
        for(StringBuilder b:fence)
            encrypt.append(b);

        System.out.println("Encrypt:"+encrypt);

        int len = encrypt.length();
        char[] decrypt = new char[len];
        int index = 0;
        int cycle = 2 * rails - 2;

        for(int row=0;row<rails;row++){
            int pos = row;
            boolean down = true;

            while(pos<len){
                decrypt[pos] = encrypt.charAt(index++);
                if(row == 0 || row == rails-1)
                    pos += cycle;
                else{
                    if(down)
                        pos += 2 * (rails-row-1);
                    else 
                        pos += 2 * row;
                    down = !down;
                }
            }
        }
        
        System.out.println("Decrypt:"+new String(decrypt));

    }

    public static void main(String[] args) {
        int ch = 1;
        do{
            System.out.println("1.Caesar Ciphar");
            System.out.println("2.Monoalphabetic");
            System.out.println("3.Polyalphabetic");
            System.out.println("4.Vernam");
            System.out.println("5.Rail Fence");
            System.out.println("6.One time pad");
            System.out.println("7.Exit");
            System.out.print("Choose one operation:");
            Scanner sc = new Scanner(System.in);
            ch = sc.nextInt();

            switch (ch) {
                case 1:
                    caesar(sc);
                    break;
                case 2:
                    monoAlphabetic(sc);
                    break;
                case 3:
                    polyAlphabetic(sc);
                    break;
                case 4:
                    vernam(sc);
                    break;
                case 5:
                    railFence(sc);
                    break;
                case 6:
                    oneTimePad(sc);
                    break;
                default:
                    break;
            }
        }while(ch != 7);
    }
}
