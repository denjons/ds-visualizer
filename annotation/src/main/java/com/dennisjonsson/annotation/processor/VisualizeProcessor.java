package com.dennisjonsson.annotation.processor;


import com.dennisjonsson.annotation.Include;
import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.markup.DataStructure;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.annotation.processor.parser.ASTProcessor;
import com.dennisjonsson.annotation.processor.parser.SourceProcessorFactory;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.ElementKind;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.HashMap;
import com.dennisjonsson.markup.DataStructureFactory;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.VisualizeArg;
import com.dennisjonsson.markup.Argument;
import com.dennisjonsson.markup.Method;


//@AutoService(Processor.class)
public class VisualizeProcessor extends AbstractProcessor {
	
	private Messager messager;
	private Filer filer;
	private HashMap<String, ASTProcessor> sourceFiles;
        private HashMap<String, ArrayList<DataStructure>> looseDataStructures;
        private HashMap<String, Element> loosePrints;
        private HashMap<String, HashMap<String, Method>> looseMethods;
        private HashMap<String, ArrayList<String>> includes;
        
	
        
        
	
	@Override
	public synchronized void init(ProcessingEnvironment env){
		messager = env.getMessager();		
		filer = env.getFiler();
		sourceFiles = new HashMap<>();
                looseDataStructures = new HashMap<>();
                loosePrints = new HashMap<>();
                looseMethods = new HashMap<>();
                includes = new HashMap<>();
	}
        

	/*
		process annot
	*/
	@Override
	public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env) { 
		
                processVisualClassPath(env);
		processVisualize(env);
                processVisualizeArg(env);
                processPrint(env);
                processInclude(env);
                
		for(ASTProcessor sourceProcessor : sourceFiles.values()){

                    if(!sourceProcessor.isWritten()){
                        
                        ArrayList<String> inc = includes.get(sourceProcessor.fullName);
                        if(inc != null){
                            sourceProcessor.setIncludes(inc);
                        }
                        
                        sourceProcessor.written();
                        // process new source file
                        sourceProcessor.loadSource();
                        sourceProcessor.processSource(null);
                        sourceProcessor.writeSource();

                    }
		}

		return true;
	}
        
        private void processInclude(RoundEnvironment env){
             for (Element annotatedElement : env.getElementsAnnotatedWith(Include.class)) {
                 if(annotatedElement.getKind() == ElementKind.CLASS){
                     addInclude(annotatedElement.toString(), 
                             ((Include)annotatedElement
                                     .getAnnotation(Include.class)).classes());
                 }
             }
        }
        
        private void addInclude(String className, String [] include){
                 ArrayList<String> inc = includes.get(className);
                 if(inc == null){
                     inc = new ArrayList<>();
                     includes.put(className, inc);
                 }
                 for(String str :include){
                     inc.add(str);
                 }
                 
                 
        }
        
        private void processVisualizeArg(RoundEnvironment env){
            for (Element annotatedElement : env.getElementsAnnotatedWith(VisualizeArg.class)) {
                
                Element classElement = findTop(annotatedElement, ElementKind.CLASS);
                
                
                if(classElement != null && annotatedElement.getKind() 
                    == ElementKind.METHOD){
                    
                    addArgument(annotatedElement, classElement.toString());
                }else{
                    throw new RuntimeException("No enclosing class "
                                + "found for Print");
                }
            }
            
        }
        
        private void addArgument(Element annotatedElement, String className){
            
            VisualizeArg annotation = (VisualizeArg)
                    annotatedElement.getAnnotation(VisualizeArg.class);
            
            String [] args = annotation.args();

            Method method = new Method(
                    className, 
                    annotatedElement.getSimpleName().toString(),
                    annotatedElement.toString());
            
            for(int pos = 0; pos < args.length; pos++){

                if(args[pos] != null){
                    
                    DataStructure ds = DataStructureFactory
                    .getDataStructure(args[pos], 
                            method.arguments[pos].trim(), null);
            
                    addDataStructure(ds, className);
                    // Argument(String method, int position, DataStructure dataStructure)
                    addArgument(new Argument(null,method,pos, ds));
                }
                
            }
 
        }
        
       
     
        
        private void addArgument(Argument arg){
            ASTProcessor processor = sourceFiles.get(arg.method.className);
            
            if(processor != null){
               // Argument(String method, int position, DataStructure dataStructure) {
               
                processor.addArgument(arg);
                //throw new RuntimeException("added: "+arg.method.name+", "+arg.name);
                //throw new RuntimeException("adding print to processor");
            }else{
                HashMap<String, Method> methods = looseMethods.get(arg.method.className);
                if(methods == null){
                    methods = new HashMap<>();
                    looseMethods.put(arg.method.className,methods);
                }
                if(methods.get(arg.method.name) == null){
                    methods.put(arg.method.name, arg.method);
                }
                
                methods.get(arg.method.name).addArgument(arg);
                //throw new RuntimeException("adding print to loose prints");
            }
        }
        
        
        private void processPrint(RoundEnvironment env){
            for (Element annotatedElement : env.getElementsAnnotatedWith(Print.class)) {
                
                //Print annotation = (Print)annotatedElement.getAnnotation(Print.class);
                
                Element classElement = findTop(annotatedElement, ElementKind.CLASS);
                
             
                if(classElement != null){
                    
                    addPrint(annotatedElement, classElement.toString());
                }else{
                    throw new RuntimeException("No enclosing class "
                                + "found for Print");
                }
                
            }
        }
        
        public void addPrint(Element annotatedElement, String className){
            ASTProcessor processor = sourceFiles.get(className);
            
            if(processor != null){
                
                processor.setPrint(annotatedElement);
                //throw new RuntimeException("adding print to processor");
            }else{

                loosePrints.put(className , annotatedElement);
                //throw new RuntimeException("adding print to loose prints");
            }
        }
	
	/*
		processes visualization annotations
	*/
	private void processVisualize(RoundEnvironment env){
		
            for (Element annotatedElement : env.getElementsAnnotatedWith(Visualize.class)) {

                Visualize annotation = (Visualize)annotatedElement.getAnnotation(Visualize.class);

                String abstractType = annotation.abstractType();

                DataStructure dataStructure = DataStructureFactory.getDataStructure(abstractType, 
                        annotatedElement.asType().toString(), 
                        annotatedElement.toString());
 
                Element classElement = findTop(annotatedElement, ElementKind.CLASS);
			
                if(classElement != null){	
                    
                    addDataStructure(dataStructure, classElement.toString());
                //throw new RuntimeException(classElement.toString());	
                }else{
                        // no enclosing class was found
                        messager.printMessage(Diagnostic.Kind.ERROR,
                                "enclosing class not found.");
                        throw new RuntimeException("No enclosing class "
                                + "found for dataStructure");
                }

            }
	}
        
        private void addDataStructure(DataStructure dataStructure, String className){
            ASTProcessor sourceProcessor = sourceFiles.get(className);

            if(sourceProcessor == null){
                 if(!looseDataStructures.containsKey(className)){
                        ArrayList<DataStructure> list = new ArrayList<>();
                        looseDataStructures.put(className,list);
                 //       throw new RuntimeException("class element: "+classElement.toString()+", source files: "+sourceFiles.keySet().size());
                    }
                    looseDataStructures.get(className).add(dataStructure);
            }else{
                sourceProcessor.addDataStructure(dataStructure);
               // throw new RuntimeException("class element: "+classElement.toString()+", source files: "+sourceFiles.keySet().size()+". added datastructure!");
            }
        }
        
        private void processVisualClassPath(RoundEnvironment env){
		
            for (Element annotatedElement : 
                env.getElementsAnnotatedWith(SourcePath.class)) {

                SourcePath annotation = 
                        (SourcePath)annotatedElement.getAnnotation(SourcePath.class);

                String className = annotatedElement.getSimpleName().toString();
                String path = annotation.path();
 

                if(!(sourceFiles.containsKey(annotatedElement.toString()))){


                    ASTProcessor sourceProcessor = 
                        (ASTProcessor)SourceProcessorFactory
                                .getProcessor(
                                        SourceProcessorFactory.Type.AST, 
                                        path, 
                                        className,
                                        annotatedElement.toString());

                    // add loose dataStructures
                    if(looseDataStructures.containsKey(annotatedElement.toString())){

                        sourceProcessor.getDataStructures()
                                .addAll(looseDataStructures
                                        .get(annotatedElement.toString()));

                    }

                    // loose prints
                    if(loosePrints.containsKey(annotatedElement.toString())){
                        sourceProcessor.setPrint(
                                loosePrints.get(annotatedElement.toString()));
                    }
                    
                    if(looseMethods.containsKey(annotatedElement.toString())){
                        sourceProcessor.addMethods(looseMethods.get(annotatedElement.toString()));
                    }
                    

                    sourceFiles.put(annotatedElement.toString(), sourceProcessor);

                    //throw new RuntimeException(annotatedElement.toString());
                }
            }
        }
   
	
        /*
            finds top parent Element of ElementKind kind from Element 'elm'
        */
        private Element findTop(Element elm, ElementKind kind){
            
            Element classElm = null;
            while( elm != null){
                elm = getNextElementOf(elm, kind);
                if(elm != null){
                     classElm = elm;
                }
            }
            return classElm;
        }
        
        /*
		finds next parent Element of ElementKind kind from Element 'elm'  
	*/
	private Element getNextElementOf(Element elm, ElementKind kind){
		
		Element temp = null; 
		Element nextOuter = elm.getEnclosingElement();
		
		while(nextOuter != null && nextOuter.getKind() !=  kind){
				
				temp = nextOuter;
				nextOuter = temp.getEnclosingElement();
		}
		
		if(nextOuter == null || nextOuter.getKind() != kind ){
			return null;
		}

		return nextOuter;
	}
	
	private void printAllElements(Element top){
		
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.addAll(top.getEnclosedElements());
		
		StringBuilder strBuilder = new StringBuilder();		
		
		while(elements.size() > 0){
			
			Element e = elements.get(elements.size()-1);
			strBuilder.append("\n"+e.getKind()+" "+e.getSimpleName()+" "+e.toString());
			elements.remove(elements.size()-1);
			elements.addAll(e.getEnclosedElements());
		}
		
		messager.printMessage(Diagnostic.Kind.NOTE, strBuilder.toString());
	}
	
      
	@Override
	public Set<String> getSupportedAnnotationTypes() { 
		Set<String> set = new LinkedHashSet<String>();
                set.add(SourcePath.class.getCanonicalName());
		set.add(Visualize.class.getCanonicalName());
                set.add(VisualizeArg.class.getCanonicalName());
                set.add(Include.class.getCanonicalName());
                set.add(Print.class.getCanonicalName());
		return set;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() { 
		return SourceVersion.latestSupported();
	}
        
         
        /*
         
       
*/
}