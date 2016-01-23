/**
 * Created by Anas Khchaf on 22/01/16.
 */
import de.mpii.SentenseSimplification._
import scala.collection.JavaConversions._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Logger, Level}

object Main {

  def simplify(sentence: String) : String= {
    val simplifier: SentenseSimplification = new SentenseSimplification

    simplifier.initParser()
    simplifier.getOptions.print(System.out, "# ")
    simplifier.parse(sentence)

    val dependencyParse = simplifier.getDepTree.pennString.replaceAll("\n", "\n                   ").trim()
    val semanticGraph = simplifier.getSemanticGraph().toFormattedString().replaceAll("\n", "\n                   ").trim()

    // clause & propositions detections

    simplifier.detectClauses()
    simplifier.generatePropositions()

    var clauses : String = "CLAUSES:"
    for(clause <- simplifier.getClauses()){
      clauses = concat(clauses, clause.toString)
    }

    var propositions: String = "PROPO:"
    for(prop <- simplifier.getPropositions()){
      propositions = concat(propositions,prop.toString)
    }
    concat(clauses, propositions)

  }

  def concat(ss: String*) = ss filter (_.nonEmpty) mkString "; "


  def main(args: Array[String]) :Unit = {

    //Environement Configuration
    val rootLogger = Logger.getRootLogger();
    rootLogger.setLevel(Level.ERROR);


    // One example of sentence simplificaion
    val sentence = " Bell, who lives in London, makes electronic, computer and building products."
    println(simplify(sentence))


    //Bulk Simplification
    val filePath = "/home/osboxes/workspace/Munichre/SentenceSimplification/data/sentences-test.txt"

    val conf = new SparkConf().setAppName("Entity Extraction").setMaster("local")
    val sc = new SparkContext(conf)

    val file = sc.textFile(filePath).map(simplify)

    file.saveAsTextFile(filePath+"result.txt")





  }
}
