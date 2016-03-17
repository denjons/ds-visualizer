package com.dennisjonsson.annotation.processor.parser;



import com.dennisjonsson.markup.DataStructure;
import java.util.ArrayList;
import java.util.UUID;


public class TextProcessor extends SourceProcessor{
    
    private TextParser parser;
    
    private static final String INSERTION_COMMENT = "/*end visualize*/";
    
    private static final String LOGGER_FIELD = "public static com.dennisjonsson.log.Logger logger = \n"
                                    + "new com.dennisjonsson.log.Logger();";
    
    public String LISTENER = "read";
    
    TextProcessor(String path, String className){
        super(path, className, new ArrayList<DataStructure>());
        
    }
     

    public String printDataStructures(){
        StringBuilder builder = new StringBuilder();
        builder.append("new String [] {");
        for(DataStructure dataStructure : dataStructures){
            builder.append("\""+dataStructure.getAbstractType()+"\",");
            builder.append("\""+dataStructure.getType()+"\",");
            builder.append("\""+dataStructure.getIdentifier()+"\",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }

    public String getSource(){
            return source;
    }

    @Override
    public void processSource(Object arg) {
        
        String newClass = (String)arg;
        
        parser = new TextParser(source);
        parser.removeAnnotations();
        parser.renameClass(className, newClass);
        className = newClass;
        parser.insertInterceptionCalls(dataStructures);
        parser.insertInterceptorMethods(dataStructures); 
        parser.insertField("public static com.dennisjonsson.log.Logger logger = \n"
                +   "new com.dennisjonsson.log.Logger(\n"
                +   printDataStructures()
                +   ");", className);
        parser.replace(INSERTION_COMMENT, "\nlogger.printLog();\n");
        
        source = parser.getSource();
    }
	
}