
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * 
 * @author User
 */


public class LexicalAnalysis {
    
  
    private static final Pattern RegEx = Pattern.compile("for|do|to|end|end.|read|var|begin|program|write|[;]|[*]|[)]|[(]|:=|[+]|[,]");  
    private static final Pattern IdRegEx = Pattern.compile("^[A-Za-z][A-Za-z0-9]*");  

    
    
    private static HashMap<String,Integer> values = null;

            
            
    public LexicalAnalysis() {
        values = new HashMap<>();
        values.put("program", 1);
        values.put("for", 6);
        values.put("do", 10);
        values.put("to", 9);
        values.put("end", 4);
        values.put("end.", 5);
        values.put("read", 7);
        values.put("write", 8);
        values.put("(",15);
        values.put(")", 16);
        values.put("var", 2);
        values.put("begin", 3);
        values.put(";", 11);
        values.put(":=", 12);
        values.put("+", 13);
        values.put("*", 18);
        values.put(",", 0);

    }
    
    public ArrayList<Pair<String,Integer>> analyser (String[] args) {
      
        ArrayList<Pair<String,Integer>> tokens = new ArrayList<Pair<String,Integer>>();
      for(int i = 0 ; i < args.length; i++) {
        String value = args[i].toLowerCase();
        if (value.trim().length() > 0){
             Matcher m = RegEx.matcher(value);
         Pair<String,Integer> pair = null;
         if (m.find()){
              pair = new Pair<String,Integer>(value,values.get(value));
            } else {
            	   Matcher idMatcher = IdRegEx.matcher(value);
            	if (idMatcher.find()) {
                    pair = new Pair<String,Integer>(value,17);
            	} else {
            		System.out.println("Error");
            		System.exit(0);
            	}
            }      
         tokens.add(pair);
        }
      
      }
        return tokens;
    }
    
    
    public void test() {
//        String x = " PROGRAM BASICS\n" +
//            " VAR\n" +
//            " X,Y,A,B,C,Z\n" +
//            " BEGIN\n" +
//            " READ ( X,Y,Z,B )\n" +
//            " A := X + B ;\n" +
//            " C := X + Z ;\n" +
//            " C := C * B ;\n" +
//            " Z := A + B + C + Y ;\n" +
//            " WRITE ( A,C,Z )\n" +
//            " END.";
    	
//    	String x ="PROGRAM EEXP \n" + 
//    			"VAR\n" + 
//    			"  a , b , left , right , result\n" + 
//    			"BEGIN \n" + 
//    			"READ ( a , b )\n" + 
//    			"WRITE ( a , b )\n" + 
//    			"left := a + b + left ; \n" + 
//    			"right := right + a + b ; \n" + 
//    			"result := left * right ;\n" + 
//    			"WRITE ( result ) \n" + 
//    			"END. ";
    	
    	String x ="PROGRAM EEXP \n" + 
    			"VAR \n" + 
    			"   a , b , c , f , h \n" + 
    			"BEGIN \n" + 
    			"READ ( a , b , c )\n" + 
    			"f :=  a + b ; \n" + 
    			"h :=  a * b ;\n" + 
    			"WRITE ( f , h )\n" + 
    			"END. \n" + 
    			"";
//        
//    	String x = " PROGRAM BASICS\n" +
//                " VAR\n" +
//                " X,Y,A,B,C,Z\n" +
//                " BEGIN\n" +
//                " A := X + B * C + D ;\n" +
//                " WRITE ( A,C,Z )\n" +
//                " END.";
            
        String z = x.replace(",", "~,~");
        String[] tokens = z.split("\\s+|~");
        ArrayList<Pair<String,Integer>> v = analyser(tokens);
//        for (int i = 0; i < v.size(); i++) {
//            Pair<String, Integer> y = v.get(i);
//            System.out.println(y.getValue() + " " + y.getKey());
//            
//        }
        new Parser(v);
        
    }
    
    
}
