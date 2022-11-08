
import java.io.IOException;
import services.TraitementFichier;

/**
 * Intégration
 */
public class IntegrationOpenFoodFacts {

	/**
	 * Mise en base de données de chaque produit extrait du fichier
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			TraitementFichier.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
