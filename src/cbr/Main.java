package cbr;

import var.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hola mundo CBR SpeedCraft");
		Recomendar  remy;
		remy = new Recomendar();
		remy.loadengine();
		
		Caso caso1 = new Caso("pan");
		caso1.alm1 = new Almacen(25, 0, 3,10);
		caso1.alm2 = new Almacen(10,17,16,10);
		caso1.cofre_galleta = new Cofre(0, 0, 0, 0, 0);
		caso1.cofre_pan 	= new Cofre(3, 0, 2, 0,11);
		caso1.cofre_pastel	= new Cofre(0, 0, 0, 0, 0);
		
		var res1 = remy.solveOuery(caso1,1);
		System.out.println(res1.getFirst() + " " +res1.getSecond());
		
		Caso caso2 = new Caso("pastel");
		caso2.alm1 = new Almacen( 5, 0, 3,10);
		caso2.alm2 = new Almacen(10, 0, 0, 0);
		caso2.cofre_galleta = new Cofre( 5, 5, 0, 5, 0);
		caso2.cofre_pan 	= new Cofre(10, 0, 5, 0,11);
		caso2.cofre_pastel	= new Cofre( 3, 1, 1, 3, 0);
		
		var res2 = remy.solveOuery(caso2,1);
		System.out.println(res2.getFirst() + " " +res2.getSecond());
		
		Caso caso3 = new Caso("galleta");
		caso3.alm1 = new Almacen(15, 0, 3,10);
		caso3.alm2 = new Almacen(10, 7,16,10);
		caso3.cofre_galleta = new Cofre( 0,10, 0, 0, 0);
		caso3.cofre_pan 	= new Cofre(10, 0, 5, 0, 0);
		caso3.cofre_pastel	= new Cofre( 0, 0, 0, 0, 0);
		
		var res3 = remy.solveOuery(caso3,1 );
		System.out.println(res3.getFirst() + " " +res3.getSecond());
			
	}
}
