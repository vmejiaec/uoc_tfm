package vmejiaec.com.citnpc.cbr;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.AmalgamationFct;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import vmejiaec.com.citnpc.var.Caso;

public class Recomendar {
    public CBREngine engine;
    public Project rec;
    public DefaultCaseBase cb;
    public Concept myConcept;


    public void loadengine () {
        engine = new CBREngine();
        rec = engine.createProjectFromPRJ();
        // create case bases and assign the case bases that will be used for submitting a query
        cb = (DefaultCaseBase)rec.getCaseBases().get(CBREngine.getCaseBase());
        // create a concept and get the main concept of the project;
        myConcept = rec.getConceptByID(CBREngine.getConceptName());
    }

    public Pair<String, Integer> solveOuery(Caso caso, int numberofcases ) {

        Pair<String, Integer> resultado = null;

        String answer="";
        // create a new retrieval
        Retrieval ret = new Retrieval(myConcept, cb);
        // specify the retrieval method
        ret.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
        // create a query instance
        Instance query = ret.getQueryInstance();

        // --------------------------------------------------------------------------------------

        // Insert values into the query: Symbolic Description
        SymbolDesc a_objetivoDesc = (SymbolDesc) myConcept.getAllAttributeDescs().get("a-objetivo");

        // Insert values into the query: Integer Description
        IntegerDesc alm1_inv_cacaoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm1-inv-cacao");
        IntegerDesc alm1_inv_huevoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm1-inv-huevo");
        IntegerDesc alm1_inv_lecheDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm1-inv-leche");
        IntegerDesc alm1_inv_trigoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm1-inv-trigo");

        IntegerDesc alm2_inv_cacaoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm2-inv-cacao");
        IntegerDesc alm2_inv_huevoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm2-inv-huevo");
        IntegerDesc alm2_inv_lecheDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm2-inv-leche");
        IntegerDesc alm2_inv_trigoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm2-inv-trigo");

        IntegerDesc cofre_galletaDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta");
        IntegerDesc cofre_galleta_ingr_cacaoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta-ingr-cacao");
        IntegerDesc cofre_galleta_ingr_huevoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta-ingr-huevo");
        IntegerDesc cofre_galleta_ingr_lecheDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta-ingr-leche");
        IntegerDesc cofre_galleta_ingr_trigoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta-ingr-trigo");

        IntegerDesc cofre_panDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan");
        IntegerDesc cofre_pan_ingr_cacaoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan-ingr-cacao");
        IntegerDesc cofre_pan_ingr_huevoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan-ingr-huevo");
        IntegerDesc cofre_pan_ingr_lecheDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan-ingr-leche");
        IntegerDesc cofre_pan_ingr_trigoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan-ingr-trigo");

        IntegerDesc cofre_pastelDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pastel");
        IntegerDesc cofre_pastel_ingr_cacaoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pastel-ingr-cacao");
        IntegerDesc cofre_pastel_ingr_huevoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pastel-ingr-huevo");
        IntegerDesc cofre_pastel_ingr_lecheDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pastel-ingr-leche");
        IntegerDesc cofre_pastel_ingr_trigoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pastel-ingr-trigo");

        try {
            query.addAttribute(a_objetivoDesc,a_objetivoDesc.getAttribute(caso.a_objetivo));

            query.addAttribute(alm1_inv_cacaoDesc,alm1_inv_cacaoDesc.getAttribute((Integer)caso.alm1.inv_cacao));
            query.addAttribute(alm1_inv_huevoDesc,alm1_inv_huevoDesc.getAttribute((Integer)caso.alm1.inv_huevo));
            query.addAttribute(alm1_inv_lecheDesc,alm1_inv_lecheDesc.getAttribute((Integer)caso.alm1.inv_leche));
            query.addAttribute(alm1_inv_trigoDesc,alm1_inv_trigoDesc.getAttribute((Integer)caso.alm1.inv_trigo));

            query.addAttribute(alm2_inv_cacaoDesc,alm2_inv_cacaoDesc.getAttribute((Integer)caso.alm2.inv_cacao));
            query.addAttribute(alm2_inv_huevoDesc,alm2_inv_huevoDesc.getAttribute((Integer)caso.alm2.inv_huevo));
            query.addAttribute(alm2_inv_lecheDesc,alm2_inv_lecheDesc.getAttribute((Integer)caso.alm2.inv_leche));
            query.addAttribute(alm2_inv_trigoDesc,alm2_inv_trigoDesc.getAttribute((Integer)caso.alm2.inv_trigo));

            query.addAttribute(cofre_galletaDesc,cofre_galletaDesc.getAttribute((Integer)caso.cofre_galleta.inv));
            query.addAttribute(cofre_galleta_ingr_cacaoDesc,cofre_galleta_ingr_cacaoDesc.getAttribute((Integer)caso.cofre_galleta.ingr_cacao));
            query.addAttribute(cofre_galleta_ingr_huevoDesc,cofre_galleta_ingr_huevoDesc.getAttribute((Integer)caso.cofre_galleta.ingr_huevo));
            query.addAttribute(cofre_galleta_ingr_lecheDesc,cofre_galleta_ingr_lecheDesc.getAttribute((Integer)caso.cofre_galleta.ingr_leche));
            query.addAttribute(cofre_galleta_ingr_trigoDesc,cofre_galleta_ingr_trigoDesc.getAttribute((Integer)caso.cofre_galleta.ingr_trigo));

            query.addAttribute(cofre_panDesc,cofre_panDesc.getAttribute((Integer)caso.cofre_pan.inv));
            query.addAttribute(cofre_pan_ingr_cacaoDesc,cofre_pan_ingr_cacaoDesc.getAttribute((Integer)caso.cofre_pan.ingr_cacao));
            query.addAttribute(cofre_pan_ingr_huevoDesc,cofre_pan_ingr_huevoDesc.getAttribute((Integer)caso.cofre_pan.ingr_huevo));
            query.addAttribute(cofre_pan_ingr_lecheDesc,cofre_pan_ingr_lecheDesc.getAttribute((Integer)caso.cofre_pan.ingr_leche));
            query.addAttribute(cofre_pan_ingr_trigoDesc,cofre_pan_ingr_trigoDesc.getAttribute((Integer)caso.cofre_pan.ingr_trigo));

            query.addAttribute(cofre_pastelDesc,cofre_pastelDesc.getAttribute((Integer)caso.cofre_pastel.inv));
            query.addAttribute(cofre_pastel_ingr_cacaoDesc,cofre_pastel_ingr_cacaoDesc.getAttribute((Integer)caso.cofre_pastel.ingr_cacao));
            query.addAttribute(cofre_pastel_ingr_huevoDesc,cofre_pastel_ingr_huevoDesc.getAttribute((Integer)caso.cofre_pastel.ingr_huevo));
            query.addAttribute(cofre_pastel_ingr_lecheDesc,cofre_pastel_ingr_lecheDesc.getAttribute((Integer)caso.cofre_pastel.ingr_leche));
            query.addAttribute(cofre_pastel_ingr_trigoDesc,cofre_pastel_ingr_trigoDesc.getAttribute((Integer)caso.cofre_pastel.ingr_trigo));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // --------------------------------------------------------------------------------------

        // perform retrieval
        ret.start();
        // get the retrieval result
        List <Pair<Instance, Similarity>> result = ret.getResult();
        // get the case name
        if(result.size()>0){

            // get the best case's name
            String casename = result.get(0).getFirst().getName();
            // get the similarity value
            Double sim = result.get(0).getSecond().getValue();
            resultado = new Pair(casename, sim);
            answer = answer + resultado.getFirst().toString() + " ";
            answer = answer + resultado.getSecond();
            //answer = " <br />I found "+casename+" with a similarity of "+sim+" as the best match.";
            //answer = answer+"The "+numberofcases+" best cases shown in a table: <br /> <br /> <table border=\"1\">";
            //answer = answer + "<table border=\"1\">";

            ArrayList<Hashtable<String, String>> liste = new ArrayList<Hashtable<String, String>>();
            // if more case results are requested than we have in our case base at all:
            if(numberofcases>=cb.getCases().size()){numberofcases = cb.getCases().size();}

            for(int i = 0; i<numberofcases; i++){

                liste.add(getAttributes(result.get(i), rec.getConceptByID(CBREngine.getConceptName())));
                System.out.println("liste "+liste.get(i).toString());
                //answer=answer+"<tr><td>"+result.get(i).getFirst().getName()+"</td>";
                //answer=answer+ "<td>"+liste.get(i).toString()+"</td></tr>";
            }

            //answer= answer+"</table>";
        }
        else{System.out.println("Retrieval Result is empty");}

        return resultado;
    }
    /**
     * This method delivers a Hashtable which contains the Attributs names (Attributes of the case) combined with their respective values.
     * @author weber,koehler,namuth
     * @param r = An Instance.
     * @param concept = A Concept
     * @return List = List containing the Attributes of a case with their values.
     */
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
    /**
     * This Method generates an ArrayList, which contains all Categories of aa Concept.
     * @author weber,koehler,namuth
     * @param r  =  An Instance.
     * @return List = List containing the Attributes names.
     */
    public static ArrayList<String> getCategories(Pair<Instance, Similarity> r) {

        ArrayList<String> cats = new ArrayList<String>();

        // Read all Attributes of a Concept
        Set<AttributeDesc> catlist = r.getFirst().getAttributes().keySet();

        for (AttributeDesc cat : catlist) {
            if (cat != null) {
                // Add the String literals for each Attribute into the ArrayList
                cats.add(cat.getName());
            }
        }
        return cats;
    }

    public String displayAmalgamationFunctions() {

        //ArrayList <String> amalgam = new ArrayList<String>();
        String listoffunctions="Currently available Amalgamationfunctions: <br /> <br />";
        AmalgamationFct current = myConcept.getActiveAmalgamFct();
        System.out.println("Amalgamation Function is used = "+current.getName());
        List<AmalgamationFct> liste = myConcept.getAvailableAmalgamFcts();

        for (int i = 0; i < liste.size(); i++){
            System.out.println(liste.get(i).getName());
            listoffunctions=listoffunctions+liste.get(i).getName()+"<br />";
        }

        listoffunctions=listoffunctions+(" <br /> <br /> Currently selected Amalgamationfunction: "+current.getName()+"\n");
        listoffunctions=listoffunctions+(" <br /> <br /> Please type the name of the Amalgamationfunction to use in the " +
                " Field \"Amalgamationfunction\" it will be automatically used during the next retrieval");
        System.out.println(listoffunctions);
        return listoffunctions;
    }
}
