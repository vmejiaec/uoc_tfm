package cbr;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hola mundo CBR SpeedCraft");
		Recomendar  remy;
		remy = new Recomendar();
		remy.loadengine();
		
		String FuncionTipo = remy.myConcept.getActiveAmalgamFct().getType().name();
		System.out.println("Nombre de la función: "+FuncionTipo);
		
		String res = remy.solveOuery(
				"pan",
				(Integer)15,(Integer)20,(Integer)30,    // Almacen 1
				(Integer)0,
				(Integer)0,(Integer)0,(Integer)0,
				(Integer)0,
				(Integer)0,(Integer)0,(Integer)0,
				(Integer)2
		);
		System.out.println(res);
		
		String res1 = remy.solveOuery(
				"galleta",
				(Integer)15,(Integer)15,(Integer)20,    // Almacen 1
				(Integer)0,
				(Integer)0,(Integer)0,(Integer)0,
				(Integer)5,
				(Integer)0,(Integer)0,(Integer)0,
				(Integer)2
		);
		System.out.println(res1);
		
		String res2 = remy.solveOuery(
				"galleta",
				(Integer)5,(Integer)15,(Integer)20,    // Almacen 1
				(Integer)0,
				(Integer)5,(Integer)0,(Integer)0,
				(Integer)5,
				(Integer)0,(Integer)0,(Integer)0,
				(Integer)2
		);
		System.out.println(res2);
			
		String fun = remy.displayAmalgamationFunctions();
		System.out.println("Funciones: "+fun);
	}

}
