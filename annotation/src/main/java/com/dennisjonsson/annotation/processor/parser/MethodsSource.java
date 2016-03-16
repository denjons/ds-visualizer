package com.dennisjonsson.annotation.processor.parser;

import com.dennisjonsson.markup.DataStructure;
import java.util.ArrayList;
import javax.lang.model.type.TypeMirror;

public class MethodsSource{
	
	ArrayList<String> types;
        private String [] primitives = {"int", "String", "boolean", "char", "double", "float"};

	public MethodsSource(){
		types = new ArrayList<String>();
		
	}
        
        public boolean isPrimitive(String primitive){
            for(int i = 0; i < primitives.length; i++){
               if(primitives[i].equalsIgnoreCase(primitive)){
                   return true;
               }
            }
            return false;
        }
	
	public String getMethods(DataStructure dStruct){
		
		for(String type : types){
			if(type.equalsIgnoreCase(dStruct.getType())){
				return "";
			}
		}
                
                
		types.add(dStruct.getType());
                String primitiveType = "";
                if(dStruct.getType().contains("[")){
                    primitiveType = dStruct.getType().toString().replaceAll("(\\[|\\])", "");
                    return getReadMethod(dStruct.getType())+
                        "\n"+getWriteMethod(primitiveType)+
                        "\n"+getPrimitiveEvals()
                        + "\n"+getArrayEvals(countDimension(dStruct.getType()),primitiveType);
                }
                else if(dStruct.getType().contains("<")){
                    primitiveType = dStruct.getType().replaceAll("(((\\w++\\.)*\\w++)*(\\<){1}?)","").replaceAll(">","");
                }
             
                return getReadMethod(dStruct.getType())
                        + "\n"+getWriteMethod(primitiveType)
                        + "\n"+getPrimitiveEvals()
                        + "\n"+getListEvals(dStruct.getType());
		
	}
	// logg(String op, String id, String uuid ,int index , int dimension){
	public String getReadMethod(String type){
		return "public static int read("
                        + "String name,"
                        + "String statementId, "
                        + "int dimension, "
                        + "int index){ "
                           // +"\nlogger.logg(\"read\", name ,uuid, index, dimension);\n"
                        + "\nlogger.loggRead(name, statementId ,index ,dimension);\n"
                       
                        + "return index; \n}";
	}
	
	public String getWriteMethod(String primitiveType){
		return "public static "+primitiveType+" write(String name, String statementId, "+primitiveType+" value){\n"
                       
                        + "logger.logWrite(name, statementId, value+\"\");\n"
                        + "return value;\n"
                        + "}";
	}
        
        public String getEval(String primitiveType){
            return "public static "+primitiveType+" eval(String statementId, "+primitiveType+" value, int statement){"
                    + "\n"
                    + "logger.logEval(statementId, value+\"\");\n"
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
        
        public String getArrayEvals(int dimensions, String primitiveType){
            StringBuilder builder = new StringBuilder();
           
            for(int i = 0; i <= dimensions; i++){
                if(!isPrimitive(primitiveType)){
                    builder.append(getEval(primitiveType));
                }
                primitiveType = primitiveType + "[]";
            }
             
            return builder.toString();
        }
        
        public String getListEvals(String type){
            StringBuilder builder = new StringBuilder();
                String last = "";
                while(type.contains("<")){
                    /*
                    if(!isPrimitive(type)){
                        builder.append(getEval(type));
                    }*/
                    last = type;
                    type = type.replaceFirst("(((\\w++\\.)*\\w++)*(\\<){1}?)","").replaceFirst(">","");
                }
                builder.append(getEval(last));
                //builder.append(getEval(type));
             
            return builder.toString();
        }
        
        private String getPrimitiveEvals(){
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < primitives.length; i++){
                builder.append(getEval(primitives[i]));
            }
            return builder.toString();
        }
        
        
}