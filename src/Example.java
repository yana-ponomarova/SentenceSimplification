import java.io.IOException;

import de.mpii.SentenseSimplification.SentenseSimplification;
import de.mpii.SentenseSimplification.Clause;
import de.mpii.SentenseSimplification.Proposition;

public class Example {
    public static void main(String[] args) throws IOException {
        SentenseSimplification SentenseSimplification = new SentenseSimplification();
        SentenseSimplification.initParser();
        SentenseSimplification.getOptions().print(System.out, "# ");

        // input sentence
        // String sentence =
        // "Bell, a telecommunication company, which is based in Los Angeles, makes and distributes electronic, computer and building products.";
        // String sentence = "There is a ghost in the room";
        // sentence = "Bell sometimes makes products";
        // sentence = "By using its experise, Bell made great products in 1922 in Saarland.";
        // sentence = "Albert Einstein remained in Princeton.";
        // sentence = "Albert Einstein is smart.";
        String sentence = " Bell, who lives in London, makes electronic, computer and building products.";

        System.out.println("Input sentence   : " + sentence);

        // parse tree
        System.out.print("Parse time       : ");
        long start = System.currentTimeMillis();
        SentenseSimplification.parse(sentence);
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000. + "s");
        System.out.print("Dependency parse : ");
        System.out.println(SentenseSimplification.getDepTree().pennString()
                .replaceAll("\n", "\n                   ").trim());
        System.out.print("Semantic graph   : ");
        System.out.println(SentenseSimplification.getSemanticGraph().toFormattedString()
                .replaceAll("\n", "\n                   ").trim());

        // clause detection
        System.out.print("SentenseSimplification time     : ");
        start = System.currentTimeMillis();
        SentenseSimplification.detectClauses();
        SentenseSimplification.generatePropositions();
        end = System.currentTimeMillis();
        System.out.println((end - start) / 1000. + "s");
        System.out.print("Clauses          : ");
        String sep = "";
        for (Clause clause : SentenseSimplification.getClauses()) {
            System.out.println(sep + clause.toString(SentenseSimplification.getOptions()));
            sep = "                   ";
        }

        // generate propositions
        System.out.print("Propositions     : ");
        sep = "";
        for (Proposition prop : SentenseSimplification.getPropositions()) {
            System.out.println(sep + prop.toString());
            sep = "                   ";
        }
    }
}
