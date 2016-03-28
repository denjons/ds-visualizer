package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.markup.DataStructure;
import java.util.ArrayList;
import java.util.Arrays;
import javax.lang.model.type.TypeMirror;

public class MethodsSource{
	
	ArrayList<String> types;
        private ArrayList<String> primitives = 
                new ArrayList<>();
        private ArrayList<String> looseTypes = 
                new ArrayList<>(Arrays.asList("int", "String", "boolean", "char", "double", "float", "Object"));
	public MethodsSource(){
		types = new ArrayList<String>();

	}
        
        
        public boolean isPrimitive(String primitive){
            for(String str : primitives){
               if(str.equalsIgnoreCase(primitive)){
                   return true;
               }
            }
            return false;
        }
	
	public String getMethods(DataStructure dStruct){
		
            
            // check type already exists as method
		for(String type : types){
			if(type.equalsIgnoreCase(dStruct.getType())){
				return "";
			}
		}
                types.add(dStruct.getType());
              
                String primitiveType = "";
                if(dStruct.getType().contains("[")){
                    primitiveType = dStruct.getType().toString().replaceAll("(\\[|\\])", "");
                    return getReadMethod(dStruct.getType())
                        + "\n"+getArrayEvalsAndWrites(
                                countDimension(dStruct.getType()),
                                primitiveType);
                }
               
             
                return getReadMethod(dStruct.getType())
                        + "\n"+getWriteMethod(primitiveType)
                        + "\n"+getPrimitiveEvals();
		
	}
	// logg(String op, String id, String uuid ,int index , int dimension){
	public String getReadMethod(String type){
            
            if(primitives.contains(type)){
                return "";
            }
            return "public static int read("
                    + "String name,"
                    + "String statementId, "
                    + "int dimension, "
                    + "int index){ "
                       // +"\nlogger.logg(\"read\", name ,uuid, index, dimension);\n"
                    + "\nlogger.read(name, statementId ,index ,dimension);\n"

                    + "return index; \n}";
	}
	
	public String getWriteMethod(String primitiveType){
           
            /*
		return "public static "+primitiveType+" write(String name, String statementId, "+primitiveType+" value){\n"
                       
                        + "logger.write(name, statementId, value+\"\");\n"
                        + "return value;\n"
                        + "}";*/
            return "public static "+primitiveType+" write(String name, "+primitiveType+" value, int sourceType, int targetType ){\n"
                       
                        + "logger.write(name, value+\"\", sourceType, targetType);\n"
                        + "return value;\n"
                        + "}";
            

             
	}
        
        public String getEval(String primitiveType){
            /*
            return "public static "+primitiveType+" eval(String statementId, "+primitiveType+" value, int statement){"
                    + "\n"
                    + "logger.eval(statementId, value+\"\");\n"
                    + "return value;\n"
                    + "}\n";
*/
            return "public static "+primitiveType+" eval(String targetId, "+primitiveType+" value, int expressionType){"
                    + "\n"
                    + "logger.eval(targetId, value+\"\", expressionType);\n"
                    + "return value;\n"
                    + "}\n";
        }
        
        
          public int countDimension(String type){
            int i = 0; 
            int j = 0;
            
            while(i != -1){
                i = type.indexOf("[", i+1);
                j++;
            }
            return j;
        }
          
        public String getArrayEvalsAndWrites(int dimensions, String primitiveType){
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i <= dimensions; i++){
                if(!isPrimitive(primitiveType)){
                    builder.append(getEval(primitiveType));
                    builder.append(getWriteMethod(primitiveType));
                    primitives.add(primitiveType);
                }
                primitiveType = primitiveType + "[]";
            }
             
            return builder.toString();
        }
        
        
        public String getPrimitiveEvals(){
            StringBuilder builder = new StringBuilder();
         
            for(String str : looseTypes){
                if(!isPrimitive(str)){
                    builder.append(getWriteMethod(str));
                    builder.append(getEval(str));
                }
            }
            return builder.toString();
        }
        
        
}