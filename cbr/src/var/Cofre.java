package var;

public class Cofre {
	public int inv;
	public int ingr_cacao;
	public int ingr_huevo;
	public int ingr_leche;
	public int ingr_trigo;
	
	public Cofre() {}
	
	public Cofre(
		int inv,
		int ingr_cacao,
		int ingr_huevo,
		int ingr_leche,
		int ingr_trigo) {
		this.inv = inv;
		this.ingr_cacao = ingr_cacao;
		this.ingr_huevo = ingr_huevo;
		this.ingr_leche = ingr_leche;
		this.ingr_trigo = ingr_trigo;
	}
}
