import org.antlr.runtime.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.antlr.runtime.debug.*;

public class ANTLRDemo {
  
  static String readFile(String path) throws IOException 
  {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, "UTF-8");
  }

  public static void main(String[] args) throws Exception {
    ANTLRStringStream in = new ANTLRStringStream(readFile(args[0]));
    sql2003Lexer lexer = new sql2003Lexer(in);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    // create a debug event listener that builds parse trees
    ParseTreeBuilder builder = new ParseTreeBuilder("query_specification");
    sql2003Parser parser = new sql2003Parser(tokens, builder);
    parser.query_specification();
    System.out.println(builder.getTree().toStringTree());
    System.out.println("Done!");
  }
   
}

