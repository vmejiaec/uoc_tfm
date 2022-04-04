package cbr;

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.io.CSVImporter;

public class CBREngine {
	
	// set path to myCBR projects	
	//private static String data_path = "C:\\Users\\bach\\IIS\\dev\\workspace\\myCBRWebDemo\\project\\";
	//private static String data_path = "K:\\Student_Projects\\z_myCBR Material\\examples\\";
	//private static String data_path = "C:\\progs\\myCBR\\CBR-SpeedCraft\\";
	private static String data_path = "C:\\UOC\\S4\\TFM\\uoc_tfm\\cbr\\modelo\\";
	//C:\progs\myCBR\CBR-SpeedCraft\
	/* project specific: NewExampleProject*/
	// name of the project file
	//speedcraft.prj
	private static String projectName = "speedcraft.prj";
	// name of the central concept 
	private static String conceptName = "speedcraft_caso";
	// name of the csv containing the instances
	private static String csv = "speedcraft-casos.csv";
	// set the separators that are used in the csv file
	private static String columnseparator = ";";
	private static String multiplevalueseparator = ",";
	// name of the case base that should be used; the default name in myCBR is CB_csvImport
	private static String casebase = "CB_csvImport";
	// Getter for the Project meta data
	public static String getCaseBase() {
		return casebase;
	}

	public static void setCasebase(String casebase) {
		CBREngine.casebase = casebase;
	}

	public static String getProjectName() {
		return projectName;
	}	

	public static void setProjectName(String projectName) {
		CBREngine.projectName = projectName;
	}

	public static String getConceptName() {
		return conceptName;
	}

	public static void setConceptName(String conceptName) {
		CBREngine.conceptName = conceptName;
	}

	public static String getCsv() {
		return csv;
	}

	public static void setCsv(String csv) {
		CBREngine.csv = csv;
	}
	
	/**
	 * This methods creates a myCBR project and loads the project from a .prj file
	 */	
	public Project createProjectFromPRJ(){

		//System.out.println("Trying to load prj file with : "+data_path+ " "+projectName+" "+conceptName+" "); 

		Project project = null;

		try{

			project = new Project(data_path + projectName);

			// Sehr wichtig hier das Warten einzubauen, sonst gibts leere 
			// Retrieval Results, weil die Faelle noch nicht geladen sind wenn das 
			// Erste Retrieval laueft		
			while (project.isImporting()){
				Thread.sleep(1000);
				//System.out.print(".");
			}		
			System.out.print("\n");	//console pretty print			
		}
		catch(Exception ex){

			System.out.println("Fehler beim Laden des Projektes");
		}		
		return project;		
	}	

	/**
	 * This methods creates a myCBR project and loads the cases in this project.
	 * The specification of the project's location and according file names has to be
	 * done at the beginning of this class.
	 * @return Project instance containing model, sims and cases (if available)
	 */
	public Project createCBRProject(){

		Project project = null;
		try {
			// load new project
			project = new Project(data_path+projectName);
			// create a concept and get the main concept of the project; 
			// the name has to be specified at the beginning of this class
			while (project.isImporting()){
				Thread.sleep(1000);
				System.out.print(".");
			}
			System.out.print("\n");	//console pretty print
			Concept concept = project.getConceptByID(conceptName);
			// Initialize CSV Import  
			CSVImporter csvImporter = new CSVImporter(data_path+csv, concept);
			// set the separators that are used in the csv file
			csvImporter.setSeparator(columnseparator); // column separator
			csvImporter.setSeparatorMultiple(multiplevalueseparator); //multiple value separator
			// prepare for import
			csvImporter.readData();	
			csvImporter.checkData();
			csvImporter.addMissingValues();
			csvImporter.addMissingDescriptions();
			// do the import of the instances 
			csvImporter.doImport();
			// wait until the import is done
			System.out.print("Importing ");
			while (csvImporter.isImporting()){
				Thread.sleep(1000);
				System.out.print(".");
			}
			System.out.print("\n");	//console pretty print
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return project;
	}

	/**
	 * This methods creates an EMPTY myCBR project.
	 * The specification of the project's location and according file names has to be
	 * done at the beginning of this class.
	 * @return Project instance containing model, sims and cases (if available)
	 */
	public Project createemptyCBRProject(){

		Project project = null;
		try {
			// load new project
			project = new Project(data_path+projectName);
			// create a concept and get the main concept of the project; 
			// the name has to be specified at the beginning of this class
			while (project.isImporting()){
				Thread.sleep(1000);
				System.out.print(".");
			}
			System.out.print("\n");	//console pretty print
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return project;
	}

}
