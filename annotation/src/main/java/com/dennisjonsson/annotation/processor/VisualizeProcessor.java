package com.dennisjonsson.annotation.processor;


import com.dennisjonsson.annotation.Interpreter;
import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.Run;
import com.dennisjonsson.annotation.markup.DataStructure;
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
import com.dennisjonsson.annotation.markup.DataStructureFactory;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.markup.Argument;
import com.dennisjonsson.annotation.markup.Method;
import com.dennisjonsson.annotation.processor.exec.ProgramExecutor;
import com.dennisjonsson.annotation.util.ADVicePrinter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;


//@AutoService(Processor.class)
public class VisualizeProcessor extends AbstractProcessor {
	
	private Messager messager;
	private Filer filer;
	private HashMap<String, ASTProcessor> sourceFiles;
        private HashMap<String, ArrayList<DataStructure>> looseDataStructures;
        private HashMap<String, Element> loosePrints;
        private HashMap<String, HashMap<String, Method>> looseMethods;
        //private HashMap<String, ArrayList<String>> includes;
        private String interpreterClassName = null;
        private String SOURCE_PATH = null;
        private ArrayList<String> annotatedClasses;
        private String mainSourceName;

	
	@Override
	public synchronized void init(ProcessingEnvironment env){
		messager = env.getMessager();		
		filer = env.getFiler();
                
		sourceFiles = new HashMap<>();
                looseDataStructures = new HashMap<>();
                loosePrints = new HashMap<>();
                looseMethods = new HashMap<>();
               // includes = new HashMap<>();
                annotatedClasses = new ArrayList<>();
	}
        
	/*
		process annot
	*/
	@Override
	public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env) { 
		
                processVisualClass(env);
                processSourcePath(env);
		processVisualize(env);
                processPrint(env);
                processInterpreter(env);
                processRun(env);
                
                processSources(env);
                
		runVisualizationProgram(env);
                

		return true;
	}
        
        private void runVisualizationProgram(RoundEnvironment env){
            ASTProcessor source = sourceFiles.get(mainSourceName);
            if(env.processingOver() && (source != null)){
                ProgramExecutor program = 
                        new ProgramExecutor(source);
                program.execute();
            }else{
                ADVicePrinter.info("No runnable class defined");
            }
        }
        
        
        private void processSources(RoundEnvironment env){
           
            if(!env.processingOver() || (SOURCE_PATH == null)){
                return;
            }
            
            for(ASTProcessor sourceProcessor : sourceFiles.values()){

                if(!sourceProcessor.isWritten()){

                    //ArrayList<String> inc = includes.get(sourceProcessor.fullName);
                    //if(inc != null){
                    sourceProcessor.setIncludes(annotatedClasses);
                    //}
                    if(interpreterClassName != null){
                        sourceProcessor.setInterpreter(interpreterClassName);
                    }

                    sourceProcessor.setPath(SOURCE_PATH);

                    sourceProcessor.written();
                    // process new source file
                    sourceProcessor.loadSource();
                    sourceProcessor.processSource(null);
                    sourceProcessor.writeSource();

                }
            }
        }
        
        private void processRun(RoundEnvironment env){
            for (Element annotatedElement : env.getElementsAnnotatedWith(Run.class)) {
                    if(annotatedElement.getKind() == ElementKind.CLASS){
                        mainSourceName = annotatedElement.toString();
                        ADVicePrinter.info("main source: "+annotatedElement.toString() );
                    }
            }
        }
        
        private void processSourcePath(RoundEnvironment env){
            for (Element annotatedElement : env.getElementsAnnotatedWith(SourcePath.class)) {
                if(annotatedElement.getKind() == ElementKind.CLASS){
                    
                    SourcePath annotation = 
                        (SourcePath)annotatedElement.getAnnotation(SourcePath.class);
                    SOURCE_PATH = annotation.path();
                    ADVicePrinter.info("source path: "+SOURCE_PATH);
                }
            }
        }
        
        private void addArgument(String className, Element methodElm, DataStructure ds){
            
            Method method = new Method(className, methodElm.toString()); 
            
            ArrayList<String> scope = new ArrayList<String>();
            scope.add(method.name);
            scope.add(className);
            addDataStructure(ds, className, scope);
            addArgument(new Argument(ds.identifier , ds.simpleIdentifier, method,ds));
                
        }
        

        private void addArgument(Argument arg){
            ASTProcessor processor = sourceFiles.get(arg.method.className);
            
            if(processor != null){
              
                processor.addArgument(arg);
           
            }/*else{
                HashMap<String, Method> methods = looseMethods.get(arg.method.className);
                if(methods == null){
                    methods = new HashMap<>();
                    looseMethods.put(arg.method.className,methods);
                }
                if(methods.get(arg.method.name) == null){
                    methods.put(arg.method.name, arg.method);
                }
                
                methods.get(arg.method.name).addArgument(arg);
           
            }*/
        }
        
        
        private void processPrint(RoundEnvironment env){
            for (Element annotatedElement : env.getElementsAnnotatedWith(Print.class)) {
                
                Element classElement = findTop(annotatedElement, ElementKind.CLASS);
                
                if((annotatedElement.getKind() == ElementKind.METHOD) && 
                        classElement != null){
                    
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
            }else{

                loosePrints.put(className , annotatedElement);
            }
        }
	
	/*
		processes visualization annotations
	*/
	private void processVisualize(RoundEnvironment env){
		
            for (Element annotatedElement : env.getElementsAnnotatedWith(Visualize.class)) {

                Visualize annotation = (Visualize)annotatedElement.getAnnotation(Visualize.class);

                String abstractType = annotation.abstractType();
                Element classElement = findTop(annotatedElement, ElementKind.CLASS);
                
                if(classElement == null){
                    throw new RuntimeException("No enclosing class "
                                + "found for dataStructure");
                }
                
                DataStructure dataStructure = DataStructureFactory.getDataStructure(abstractType, 
                        annotatedElement.asType().toString(), 
                        annotatedElement.toString());
                
                if(annotatedElement.getKind() == ElementKind.PARAMETER ){
                    addArgument(
                            classElement.toString(), 
                            annotatedElement.getEnclosingElement(), 
                            dataStructure);
                    //System.out.println("parameter: "+annotatedElement.toString());
                    //System.out.println("enclosing: "+annotatedElement.getEnclosingElement().toString());
                    
                }else if(annotatedElement.getKind() == ElementKind.FIELD){
                    ArrayList<String> scope = new ArrayList<String>();
                    scope.add(classElement.toString());
                    addDataStructure(dataStructure, classElement.toString(), scope);
                    
                }

            }
	}
        
        private void addDataStructure(DataStructure dataStructure, String className, ArrayList<String> scope){
            ASTProcessor sourceProcessor = sourceFiles.get(className);
            
            // set scope of datastructure
            dataStructure.setScope(scope);
            
            ADVicePrinter.info("Visualize "+dataStructure.getAbstractType()
                    +" "+dataStructure.identifier);
            
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
        
        private void processVisualClass(RoundEnvironment env){
		
            for (Element annotatedElement : 
                env.getElementsAnnotatedWith(VisualClass.class)) {

                String className = annotatedElement.getSimpleName().toString();

                if((annotatedElement.getKind() == ElementKind.CLASS) && 
                        !(sourceFiles.containsKey(annotatedElement.toString()))){
                    
                    addAnnotatedClass(annotatedElement.toString());

                    ASTProcessor sourceProcessor = 
                        (ASTProcessor)SourceProcessorFactory
                                .getProcessor(
                                        SourceProcessorFactory.Type.AST, 
                                        className,
                                        annotatedElement.toString());
                    
                    ADVicePrinter.info("VisualCLass "+sourceProcessor.fullName);
                    // add loose dataStructures
                    if(looseDataStructures.containsKey(sourceProcessor.fullName)){

                        sourceProcessor.getDataStructures()
                                .addAll(looseDataStructures
                                        .get(sourceProcessor.fullName));
                    }

                    // loose prints
                    if(loosePrints.containsKey(sourceProcessor.fullName)){
                        sourceProcessor.setPrint(
                                loosePrints.get(sourceProcessor.fullName));
                    }
                    
                    /*
                    if(looseMethods.containsKey(annotatedElement.toString())){
                        sourceProcessor.addMethods(looseMethods.get(sourceProcessor.fullName));
                    }*/
                    

                    sourceFiles.put(sourceProcessor.fullName, sourceProcessor);
                    adMethodsForClass(annotatedElement);
                    //throw new RuntimeException(annotatedElement.toString());
                }
            }
        }
        
        private void adMethodsForClass(Element classElement){
            handleClassElements(classElement, classElement.toString());
        }
        
        private void addAnnotatedClass(String fullClassName){
            if(!annotatedClasses.contains(fullClassName)){
                annotatedClasses.add(fullClassName);
            }
            
        }
        
        private void processInterpreter(RoundEnvironment env){
		
            for (Element annotatedElement : 
                env.getElementsAnnotatedWith(Interpreter.class)) {
                if(annotatedElement.getKind() == ElementKind.CLASS){
                    this.interpreterClassName = annotatedElement.toString();
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
	
	private void handleClassElements(Element top, String className){
		
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.addAll(top.getEnclosedElements());
		
		StringBuilder strBuilder = new StringBuilder();		
		
		while(elements.size() > 0){
			
			Element e = elements.get(elements.size()-1);
			strBuilder.append("\n"+e.getKind()+" "+e.getSimpleName()+" "+e.toString());
                        handleElement(e, className);
			elements.remove(elements.size()-1);
			elements.addAll(e.getEnclosedElements());
		}
		
		messager.printMessage(Diagnostic.Kind.NOTE, strBuilder.toString());
	}
        
        private void handleElement(Element e, String className){
            if(e.getKind() == ElementKind.METHOD){
                sourceFiles.get(className)
                    .AddMethod(new Method(className, e.toString()));
            }
            
        }
	
      
	@Override
	public Set<String> getSupportedAnnotationTypes() { 
		Set<String> set = new LinkedHashSet<String>();
                set.add(SourcePath.class.getCanonicalName());
		set.add(Visualize.class.getCanonicalName());
                set.add(Interpreter.class.getCanonicalName());
                set.add(VisualClass.class.getCanonicalName());
                set.add(Print.class.getCanonicalName());
                set.add(Run.class.getCanonicalName());
		return set;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() { 
		return SourceVersion.latestSupported();
	}
        
         
        /*
         
       
*/
}