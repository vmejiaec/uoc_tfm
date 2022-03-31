package cbr;

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

	public String solveOuery(
			String a_objetivo, 

			Integer alm1_cacao,
			Integer alm1_leche,
			Integer alm1_trigo,
			
			Integer cofre_galleta,
			Integer cofre_galleta_cacao,
			Integer cofre_galleta_leche,
			Integer cofre_galleta_trigo,
			
			Integer cofre_pan,
			Integer cofre_pan_cacao,
			Integer cofre_pan_leche,
			Integer cofre_pan_trigo,
			
			Integer numberofcases
			) {

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
		IntegerDesc alm1_cacaoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm1-cacao");
		IntegerDesc alm1_lecheDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm1-leche");
		IntegerDesc alm1_trigoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("alm1-trigo");
		
		IntegerDesc cofre_galletaDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta");
		IntegerDesc cofre_galleta_cacaoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta-cacao");
		IntegerDesc cofre_galleta_lecheDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta-leche");
		IntegerDesc cofre_galleta_trigoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-galleta-trigo");
		
		IntegerDesc cofre_panDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan");
		IntegerDesc cofre_pan_cacaoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan-cacao");
		IntegerDesc cofre_pan_lecheDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan-leche");
		IntegerDesc cofre_pan_trigoDesc = (IntegerDesc) myConcept.getAllAttributeDescs().get("cofre-pan-trigo");

		try {
			query.addAttribute(a_objetivoDesc,a_objetivoDesc.getAttribute(a_objetivo));
			
			query.addAttribute(alm1_cacaoDesc,alm1_cacaoDesc.getAttribute(alm1_cacao));
			query.addAttribute(alm1_lecheDesc,alm1_lecheDesc.getAttribute(alm1_leche));
			query.addAttribute(alm1_trigoDesc,alm1_trigoDesc.getAttribute(alm1_trigo));
			
			query.addAttribute(cofre_galletaDesc,cofre_galletaDesc.getAttribute(cofre_galleta));
			query.addAttribute(cofre_galleta_cacaoDesc,cofre_galleta_cacaoDesc.getAttribute(cofre_galleta_cacao));
			query.addAttribute(cofre_galleta_lecheDesc,cofre_galleta_lecheDesc.getAttribute(cofre_galleta_leche));
			query.addAttribute(cofre_galleta_trigoDesc,cofre_galleta_trigoDesc.getAttribute(cofre_galleta_trigo));
			
			query.addAttribute(cofre_panDesc,cofre_panDesc.getAttribute(cofre_pan));
			query.addAttribute(cofre_pan_cacaoDesc,cofre_pan_cacaoDesc.getAttribute(cofre_pan_cacao));
			query.addAttribute(cofre_pan_lecheDesc,cofre_pan_lecheDesc.getAttribute(cofre_pan_leche));
			query.addAttribute(cofre_pan_trigoDesc,cofre_pan_trigoDesc.getAttribute(cofre_pan_trigo));

			
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
			answer = "I found "+casename+" with a similarity of "+sim+" as the best match.";
			answer = answer+"The "+numberofcases+" best cases shown in a table: <br /> <br /> <table border=\"1\">";	
			
			ArrayList<Hashtable<String, String>> liste = new ArrayList<Hashtable<String, String>>();	
			// if more case results are requested than we have in our case base at all:
			if(numberofcases>=cb.getCases().size()){numberofcases = cb.getCases().size();}

			for(int i = 0; i<numberofcases; i++){

				liste.add(getAttributes(result.get(i), rec.getConceptByID(CBREngine.getConceptName())));
				//System.out.println("liste "+liste.get(i).toString());
				String valores = liste.get(i).toString().replace("{", "<ul><li>").replace("}", "</li></ul>").replace(",", "</li><li>");
				//answer=answer+"<tr><td>"+result.get(i).getFirst().getName()+"</td><td>"+liste.get(i).toString()+"</td></tr>";
				answer=answer+"<tr><td>"+result.get(i).getFirst().getName()+"</td><td>"+valores+"</td></tr>";
			}

			answer= answer+"</table>";		
		}	
		else{System.out.println("Retrieval Result is empty");}

		return answer;
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
