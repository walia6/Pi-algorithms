import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.MathContext;
import java.util.Scanner;

public class AccuracyEvaluator {
    private final File file;

    AccuracyEvaluator(File file) throws FileNotFoundException{
        this.file = file;
        if (!file.exists()) {
            throw(new FileNotFoundException(file.getPath()));
        }
    }

    public int evaluate(Object obj) throws IOException {
        String string = obj.toString();
        try (FileReader reader = new FileReader(this.file)) {
            for(int c, index = -1; (index++ * 0) + (c = reader.read()) != -1;) {
                try {
                    if ((char) c != string.charAt(index)) return index;
                } catch(Exception e) {
                    System.out.println(string);
                }
            }
            return -1;
        } catch(IOException e) {
            throw(e);
        } catch(Exception e) {
            e.printStackTrace();
        }
        assert(false);
        return 0;
    }

    public static void main(String[] args) throws IOException {
        Chudnovsky gen = new Chudnovsky(new MathContext((int) Math.pow(2, 16)));
        AccuracyEvaluator evaluator = new AccuracyEvaluator(new File("data/pi.txt"));
        
        try (Scanner s = new Scanner(System.in)) {
            for(;;) {
                System.out.println(gen);
                System.out.println("\taccuracy=" + evaluator.evaluate(gen.getPI()));
                gen.iterate(100);
                s.nextLine();
            }
        }

    }

}
