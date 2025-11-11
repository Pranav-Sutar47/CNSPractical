import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Practice8 {

    static int encryption(Scanner sc){
        System.out.println("Enter file path:");
        String path = sc.next();
        try{
            FileInputStream fs = new FileInputStream(path);
            byte[] data = new byte[fs.available()];
            System.out.print("Enter key:");
            int key = sc.nextInt();

            fs.read(data);
            int i = 0;
            for(byte b:data)
                data[i++] = (byte) (b ^ key);

            FileOutputStream fo = new FileOutputStream("Encrypt.png");
            fo.write(data);
            fo.close();
            fs.close();

            return key;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
    

    static void decryption(int key){
        try{
            FileInputStream fs = new FileInputStream("Encrypt.png");
            byte[] data = new byte[fs.available()];
            fs.read(data);
            
            int i = 0;
            for(byte b:data)
                data[i++] = (byte) (b ^ key);
            FileOutputStream fo = new FileOutputStream("Decrypt.png");
            fo.write(data);
            fo.close();
            fs.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int key = encryption(sc);
        decryption(key);
    }
}
