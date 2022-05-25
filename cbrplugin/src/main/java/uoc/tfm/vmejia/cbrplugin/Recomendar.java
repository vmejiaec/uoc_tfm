package uoc.tfm.vmejia.cbrplugin;

import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Recomendar {
    public CBREngine engine;
    public Project rec;
    public DefaultCaseBase cb;
    public Concept myConcept;

    // Cargar el motor del CBR
    public void loadengine () {
        engine = new CBREngine();
        rec = engine.createProjectFromPRJ();
        // create case bases and assign the case bases that will be used for submitting a query
        cb = (DefaultCaseBase)rec.getCaseBases().get(CBREngine.getCaseBase());
        // create a concept and get the main concept of the project;
        myConcept = rec.getConceptByID(CBREngine.getConceptName());
    }

    // Retorna el caso más cercano a los valores entregados
    public Pair<String, Integer> solveOuery(String a_objetivo, String[] nombres, int[] valores, int numberofcases ) {

        Pair<String, Integer> resultado = null;

        String answer="";
        Retrieval ret = new Retrieval(myConcept, cb);
        ret.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
        Instance query = ret.getQueryInstance();

        // Prepara los valores para realizar la consulta
        SymbolDesc a_objetivoDesc = (SymbolDesc) myConcept.getAllAttributeDescs().get("a-objetivo");
        IntegerDesc[] intdescs = new IntegerDesc[nombres.length];
        for (int i=0;i< nombres.length;++i) {
            intdescs[i] =(IntegerDesc) myConcept.getAllAttributeDescs().get(nombres[i]);
        }
        // Ejecuta la consulta
        try {
            query.addAttribute(a_objetivoDesc,a_objetivoDesc.getAttribute(a_objetivo));
            for (int i=0;i< valores.length;++i){
                query.addAttribute(intdescs[i],intdescs[i].getAttribute((Integer)valores[i]));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Ejecuta la consulta
        ret.start();
        // recupera el resultado
        List <Pair<Instance, Similarity>> result = ret.getResult();
        // get the case name
        if(result.size()>0){
            // Recupera el nombre del caso que mejor se ajusta a las variables
            String casename = result.get(0).getFirst().getName();
            // Recupera la ristra de valores del caso que mejor se ajusta
            Double sim = result.get(0).getSecond().getValue();
            resultado = new Pair(casename, sim);
            answer = answer + resultado.getFirst().toString() + " ";
            answer = answer + resultado.getSecond();

            ArrayList<Hashtable<String, String>> liste = new ArrayList<Hashtable<String, String>>();

            // Si solicita más resultados que los casos de la base
            if(numberofcases>=cb.getCases().size()){numberofcases = cb.getCases().size();}

            for(int i = 0; i<numberofcases; i++){
                liste.add(getAttributes(result.get(i), rec.getConceptByID(CBREngine.getConceptName())));
                System.out.println("lista "+liste.get(i).toString());
            }
        }
        else{System.out.println("El resultado recuperado está vacío.");}

        return resultado;
    }

    // Entrega un Hashtable con los nombres de los Atributos del caso combinados con sus valores.
    public static Hashtable<String, String> getAttributes(Pair<Instance, Similarity> r, Concept concept) {

        Hashtable<String, String> table = new Hashtable<String, String>();
        ArrayList<String> cats = getCategories(r);
        // Add the similarity of the case
        table.put("Sim", String.valueOf(r.getSecond().getValue()));
        for (String cat : cats) {
            // Add the Attribute name and its value into the Hashtable
            table.put(cat, r.getFirst().getAttForDesc(concept.getAllAttributeDescs().get(cat)).getValueAsString());
        }
        return table;
    }

    // Generar un arreglo con todas las categorías de un concepto
    public static ArrayList<String> getCategories(Pair<Instance, Similarity> r) {
        ArrayList<String> cats = new ArrayList<String>();

        // Lee los atributos
        Set<AttributeDesc> catlist = r.getFirst().getAttributes().keySet();

        for (AttributeDesc cat : catlist) {
            if (cat != null) {
                // Añade los nombres de cada atributo
                cats.add(cat.getName());
            }
        }
        return cats;
    }
}
