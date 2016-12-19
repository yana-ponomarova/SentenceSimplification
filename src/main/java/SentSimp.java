import java.io.IOException;
import java.util.Scanner;

import de.mpii.sentensesimplification.SentenseSimplification;
import de.mpii.sentensesimplification.Clause;
import de.mpii.sentensesimplification.Proposition;

public class SentSimp {
    public static void main(String[] args) throws IOException {
        SentenseSimplification ss = new SentenseSimplification();
        ss.initParser();
        ss.getOptions().print(System.out, "# ");

        // input sentence
        // String sentence =
        // "Bell, a telecommunication company, which is based in Los Angeles, makes and distributes electronic, computer and building products.";
        // String sentence = "There is a ghost in the room";
        // sentence = "Bell sometimes makes products";
        // sentence = "By using its experise, Bell made great products in 1922 in Saarland.";
        // sentence = "Albert Einstein remained in Princeton.";
        // sentence = "Albert Einstein is smart.";
//        String sentence = " Bell, who lives in London, makes electronic, computer and building products.";

        Scanner input = new Scanner(System.in);
    	System.out.print("sentence? ");

        while (input.hasNextLine()) {
        	String sentence = input.nextLine();
        	
            System.out.println("Input sentence   : " + sentence);
            if ("QUIT".equals(sentence)) break;
            
            // parse tree
            System.out.print("Parse time       : ");
            long start = System.currentTimeMillis();
            ss.parse(sentence);
            long end = System.currentTimeMillis();
            System.out.println((end - start) / 1000. + "s");
            System.out.print("Dependency parse : ");
            System.out.println(ss.getDepTree().pennString()
                    .replaceAll("\n", "\n                   ").trim());
            System.out.print("Semantic graph   : ");
            System.out.println(ss.getSemanticGraph().toFormattedString()
                    .replaceAll("\n", "\n                   ").trim());

            // clause detection
            System.out.print("SentenseSimplification time     : ");
            start = System.currentTimeMillis();
            ss.detectClauses();
            ss.generatePropositions();
            end = System.currentTimeMillis();
            System.out.println((end - start) / 1000. + "s");
            System.out.print("Clauses          : ");
            String sep = "";
            for (Clause clause : ss.getClauses()) {
                System.out.println(sep + clause.toString(ss.getOptions()));
                sep = "                   ";
            }

            // generate propositions
            System.out.print("Propositions     : ");
            sep = "";
            for (Proposition prop : ss.getPropositions()) {
                System.out.println(sep + prop.toString());
                sep = "                   ";
            }
            System.out.println();
            System.out.print("sentence? ");
        	
        }
    }
}
