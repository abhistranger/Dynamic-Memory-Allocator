import java.util.Scanner;
public class Driver{
    public static void main(String args[]){
        //long startTime = System.nanoTime();
        int numTestCases;
        Scanner sc = new Scanner(System.in);
        numTestCases = sc.nextInt();
        while(numTestCases-->0){
            int size;
            size = sc.nextInt();
            A1DynamicMem obj = new A1DynamicMem(size,2);
            int numCommands = sc.nextInt();
            while(numCommands-->0) {
                String command;
                command = sc.next();
                int argument;
                argument = sc.nextInt();
                int result = -5;
                switch (command) {
                    case "Allocate":
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        result = obj.Free(argument);
                        break;
                    default:
                        break;
                }
                System.out.println(result);
            }
            
        }
        //long toc= System.nanoTime();
        //System.out.println((toc - startTime)/1000000000.0);
    }
}

/*import java.util.Arrays;
import java.util.Scanner;
public class Driver{
    public static void main(String args[]){
        int numTestCases;
        Scanner sc = new Scanner(System.in);
        numTestCases = sc.nextInt();
        while(numTestCases-->0){
            int size;
            size = sc.nextInt();
            A1DynamicMem obj = new A1DynamicMem(size);
            int numCommands = sc.nextInt();
            int arr[] = new int[5000];
            for(int y=0; y<5000; y++){
                arr[y]=-1;
            }
            int ind = 0;
            while(numCommands-->0) {
                String command;
                command = sc.next();
                int argument;
                argument = sc.nextInt();
                int result = -5;
                switch (command) {
                    case "Allocate":
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        result = obj.Free(argument);
                        break;
                    default:
                        break;
                }
                //System.out.println(result);
                if(result>=0) arr[ind] = result;
                ind++;
            }
            for (int k=0; k<5000; k++) {
                if (arr[k] >= 0) {
                    System.out.println(arr[k]);
                }
            }

        }
    }
}*/