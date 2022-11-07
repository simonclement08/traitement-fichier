
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entities.Additif;
import entities.Allergene;
import entities.Categorie;
import entities.Ingredient;
import entities.Marque;
import entities.Produit;

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

		Path path = Paths.get(".\\src\\main\\resources\\open-food-facts.csv");
		List<String> lines = null;

		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			lines.remove(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("open_food_facts");
		EntityManager em = entityManagerFactory.createEntityManager();

		EntityTransaction transaction = em.getTransaction();

		int i = 1;
		transaction.begin();
		for (String line : lines) {
			
			
			String[] data = line.split("\\|", -1);
			
			//INTEGRATION DES CATEGORIES

			TypedQuery<Categorie> queryCategorie = em.createQuery("SELECT c FROM Categorie c "
			 + "WHERE c.libelle = :libelle", Categorie.class);
			queryCategorie.setParameter("libelle", data[0]);

			Categorie categorie = null;
			
			try {
				categorie = queryCategorie.getSingleResult();
			} catch (NoResultException e) {
			}

			if (categorie == null) {
				categorie = new Categorie(data[0]);
				em.persist(categorie);
			}
			
			//INTEGRATION DES PRODUITS
			
			Produit produit = new Produit(categorie, data[2], data[3].toUpperCase());
			em.persist(produit);
			if(!data[5].trim().equals("")) {
				produit.setEnergie(Double.parseDouble(data[5]));
			}
			if(!data[6].trim().equals("")) {
				produit.setQuantiteGraisse(Double.parseDouble(data[6]));
			}
			if(data[27].equals("1")) {
				produit.setPresenceHuilePalme(true);
			}
			
			//INTEGRATION DES MARQUES
			
			String[] marques = data[1].split(",");
			
			for (String newMarque : marques) {
				TypedQuery<Marque> queryMarque = em.createQuery("SELECT m FROM Marque m "
						+ "WHERE m.libelle = :libelle", Marque.class);
				queryMarque.setParameter("libelle", newMarque.trim());
				
				Marque marque = null;
				
				try {
					marque = queryMarque.getSingleResult();
				} catch (NoResultException e) {
				}
				
				boolean checkNewMarque = false;
				if (marque == null) {
					marque = new Marque(newMarque.trim());
					checkNewMarque = true;
				}
				
				boolean checkHaventMarque = true;
				for (Marque marqueProduit : produit.getMarques()) {
					if(marqueProduit.equals(marque)) {
						checkHaventMarque = false;
					}
				}
				if(checkHaventMarque && !marque.getLibelle().equals("")) {
					if(checkNewMarque){
						em.persist(marque);
					}
					produit.getMarques().add(marque);
				}
			}
			
			//INTEGRATION DES INGREDIENTS
			
			String[] ingredients = data[4].split(",");
			
			for (String newIngredient : ingredients) {
				TypedQuery<Ingredient> queryIngredient = em.createQuery("SELECT i FROM Ingredient i "
						+ "WHERE i.libelle = :libelle", Ingredient.class);
				queryIngredient.setParameter("libelle", newIngredient.trim());
				
				Ingredient ingredient = null;
				
				try {
					ingredient = queryIngredient.getSingleResult();
				} catch (NoResultException e) {
				}
				
				boolean checkNewIngredient = false;
				if (ingredient == null) {
					ingredient = new Ingredient(newIngredient.trim());
					checkNewIngredient = true;
				}
				
				boolean checkHaventIngredient = true;
				for (Ingredient ingredientProduit : produit.getIngredients()) {
					if(ingredientProduit.equals(ingredient)) {
						checkHaventIngredient = false;
					}
				}
				if(checkHaventIngredient && !ingredient.getLibelle().equals("")) {
					if(checkNewIngredient){
						em.persist(ingredient);
					}
					produit.getIngredients().add(ingredient);
				}
			}
			
			//INTEGRATION DES ALLERGENES
			
			String[] allergenes = data[28].split(",");
			
			for (String newAllergenes : allergenes) {
				TypedQuery<Allergene> queryAllergene = em.createQuery("SELECT a FROM Allergene a "
						+ "WHERE a.libelle = :libelle", Allergene.class);
				queryAllergene.setParameter("libelle", newAllergenes.trim());
				
				Allergene allergene = null;
				
				try {
					allergene = queryAllergene.getSingleResult();
				} catch (NoResultException e) {
				}
				
				boolean checkNewAllergene = false;
				if (allergene == null) {
					allergene = new Allergene(newAllergenes.trim());
					checkNewAllergene = true;
				}
				
				boolean checkHaventAllergene = true;
				for (Allergene allergeneProduit : produit.getAllergenes()) {
					if(allergeneProduit.equals(allergene)) {
						checkHaventAllergene = false;
					}
				}
				if(checkHaventAllergene && !allergene.getLibelle().equals("")) {
					if(checkNewAllergene){
						em.persist(allergene);
					}
					produit.getAllergenes().add(allergene);
				}
			}
			
			//INTEGRATION DES ADDITIFS
			
			String[] additifs = data[29].split(",");
			
			for (String newAdditifs : additifs) {
				TypedQuery<Additif> queryAdditif = em.createQuery("SELECT a FROM Additif a "
						+ "WHERE a.libelle = :libelle", Additif.class);
				queryAdditif.setParameter("libelle", newAdditifs.trim());
				
				Additif additif = null;
				
				try {
					additif = queryAdditif.getSingleResult();
				} catch (NoResultException e) {
				}
				
				boolean checkNewAdditif = false;
				if (additif == null) {
					additif = new Additif(newAdditifs.trim());
					checkNewAdditif = true;
				}
				
				boolean checkHaventAdditif = true;
				for (Additif additifProduit : produit.getAdditifs()) {
					if(additifProduit.equals(additif)) {
						checkHaventAdditif = false;
					}
				}
				if(checkHaventAdditif && !additif.getLibelle().equals("")) {
					if(checkNewAdditif){
						em.persist(additif);
					}
					produit.getAdditifs().add(additif);
				}
			}
			
			i++;
			if(i%100 == 0) {
				transaction.commit();
				transaction.begin();
			}
			
		}
		transaction.commit();
	}

}
