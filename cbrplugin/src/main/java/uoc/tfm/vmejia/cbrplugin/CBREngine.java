package uoc.tfm.vmejia.cbrplugin;

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.io.CSVImporter;

public class CBREngine {

    //private static String data_path = "C:\\UOC\\S4\\TFM\\uoc_tfm\\cbr\\modelo\\";
    private static String data_path = ConfigManager.getData_Path();
    //private static String projectName = "speedcraft.prj";
    private static String projectName = ConfigManager.getProjectName();
    //private static String conceptName = "speedcraft_caso";
    private static String conceptName = ConfigManager.getConceptName();
    //private static String csv = "speedcraft-casos.csv";
    private static String csv = ConfigManager.getCSV();
    // Nombre del caso base, por defecto es CB_csvImport
    private static String casebase = ConfigManager.getCaseBase();
    private static String columnseparator = ConfigManager.getColumnSeparator();
    private static String multiplevalueseparator = ConfigManager.getMultipleValueSeparator();

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


    // Crear proyecto MyCBR y leer el modelo del archivo .prj
    public Project createProjectFromPRJ(){
        //System.out.println("Cargando el proyecto : "+data_path+ " "+projectName+" "+conceptName+" ");
        Project project = null;
        try{
            project = new Project(data_path + projectName);
            while (project.isImporting()){
                Thread.sleep(1000);
                //System.out.print(".");
            }
            System.out.print("\n");
        }
        catch(Exception ex){
            System.out.println("Error al cargar el proyecto");
        }
        return project;
    }

    // Crea un proyecto myCBR con los casos del modelo
    public Project createCBRProject(){

        Project project = null;
        try {
            // carga el proyecto
            project = new Project(data_path+projectName);
            while (project.isImporting()){
                Thread.sleep(1000);
                System.out.print(".");
            }
            System.out.print("\n");
            Concept concept = project.getConceptByID(conceptName);

            // Configurando la lectura del CSV
            CSVImporter csvImporter = new CSVImporter(data_path+csv, concept);
            csvImporter.setSeparator(columnseparator);
            csvImporter.setSeparatorMultiple(multiplevalueseparator);

            // prepara la importación
            csvImporter.readData();
            csvImporter.checkData();
            csvImporter.addMissingValues();
            csvImporter.addMissingDescriptions();

            csvImporter.doImport();

            System.out.print("Importing ");
            while (csvImporter.isImporting()){
                Thread.sleep(1000);
                System.out.print(".");
            }
            System.out.print("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    // Crear un proyecto myCBR vacio
    public Project createemptyCBRProject(){
        Project project = null;
        try {
            // cargar el archivo del modelo
            project = new Project(data_path+projectName);
            while (project.isImporting()){
                Thread.sleep(1000);
                System.out.print(".");
            }
            System.out.print("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

}
