package services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import entities.Additif;
import entities.Allergene;
import entities.Categorie;
import entities.Ingredient;
import entities.Marque;
import entities.Produit;

/**
 * Traitement du fichier open food fact
 */
public class TraitementFichier {

	/** Liste des categories. */
	public static HashMap<String, Categorie> categories;

	/** Liste des marques. */
	public static HashMap<String, Marque> marques;

	/** Liste des ingredients. */
	public static HashMap<String, Ingredient> ingredients;

	/** Liste des allergenes. */
	public static HashMap<String, Allergene> allergenes;

	/** Liste des additifs. */
	public static HashMap<String, Additif> additifs;

	/** Constructeur */
	private TraitementFichier() {
		super();
		TraitementFichier.categories = new HashMap<String, Categorie>();
		TraitementFichier.marques = new HashMap<String, Marque>();
		TraitementFichier.ingredients = new HashMap<String, Ingredient>();
		TraitementFichier.allergenes = new HashMap<String, Allergene>();
		TraitementFichier.additifs = new HashMap<String, Additif>();
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 * @throws IOException the io exception
	 */
	public static TraitementFichier getInstance() throws IOException {
		TraitementFichier traitementFichier = new TraitementFichier();

		Path path = Paths.get(".\\src\\main\\resources\\open-food-facts.csv");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.remove(0);

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("open_food_facts");
		EntityManager em = entityManagerFactory.createEntityManager();

		EntityTransaction transaction = em.getTransaction();

		int i = 1;
		transaction.begin();
		for (String line : lines) {

			String[] data = line.split("\\|", -1);

			Categorie categorie = TraitementFichier.traitementCategorieLine(em, data[0]);

			// INTEGRATION DU PRODUIT

			Produit produit = new Produit(categorie, data[2], data[3].toUpperCase());
			em.persist(produit);

			if (!data[5].trim().equals("")) {
				produit.setEnergie(Double.parseDouble(data[5]));
			}
			if (!data[6].trim().equals("")) {
				produit.setQuantiteGraisse(Double.parseDouble(data[6]));
			}
			if (data[27].equals("1")) {
				produit.setPresenceHuilePalme(true);
			}

			// INTEGRATION DES MARQUES
			String[] marques = data[1].split(",");

			for (String newMarque : marques) {
				TraitementFichier.traitementMarqueLine(em, produit, newMarque);
			}

			// INTEGRATION DES INGREDIENTS
			String[] ingredients = data[4].split(",");

			for (String newIngredient : ingredients) {
				TraitementFichier.traitementIngredientLine(em, produit, newIngredient);
			}

			// INTEGRATION DES ALLERGENES
			String[] allergenes = data[28].split(",");

			for (String newAllergenes : allergenes) {
				TraitementFichier.traitementAllergeneLine(em, produit, newAllergenes);
			}

			// INTEGRATION DES ADDITIFS

			String[] additifs = data[29].split(",");

			for (String newAdditifs : additifs) {
				TraitementFichier.traitementAdditifLine(em, produit, newAdditifs);
			}

			i++;
			if (i % 100 == 0) {
				transaction.commit();
				transaction.begin();
			}

		}
		transaction.commit();

		return traitementFichier;
	}

	/**
	 * Traitement d'une catégorie du fichier
	 * 
	 * @param em           Entity Manager
	 * @param newCategorie Catégorie à traiter présent sur la ligne
	 * @return la catégorie associé au produit de la ligne
	 */
	public static Categorie traitementCategorieLine(EntityManager em, String newCategorie) {

		Categorie categorie = categories.get(newCategorie);
		if (categorie == null) {
			categorie = new Categorie(newCategorie);
			categories.put(newCategorie, categorie);
			em.persist(categorie);
		}

		return categorie;
	}

	/**
	 * Traitement d'une marque du fichier
	 * 
	 * @param em        Entity Manager
	 * @param produit   Produit de la ligne
	 * @param newMarque Marque à traiter présent sur la ligne
	 */
	public static void traitementMarqueLine(EntityManager em, Produit produit, String newMarque) {

		Marque marque = marques.get(newMarque);

		if (marque == null && !newMarque.equals("")) {
			marque = new Marque(newMarque.trim());
			marques.put(newMarque, marque);
			em.persist(marque);
		}

		boolean checkProduitHaventMarque = true;
		for (Marque marqueProduit : produit.getMarques()) {
			if (marqueProduit.equals(marque)) {
				checkProduitHaventMarque = false;
			}
		}

		if (checkProduitHaventMarque && marque != null) {
			produit.getMarques().add(marque);
		}
	}

	/**
	 * Traitement d'un ingrédient du fichier
	 * 
	 * @param em            Entity Manager
	 * @param produit       Produit de la ligne
	 * @param newIngredient Ingrédient à traiter présent sur la ligne
	 */
	public static void traitementIngredientLine(EntityManager em, Produit produit, String newIngredient) {
		Ingredient ingredient = ingredients.get(newIngredient);

		if (ingredient == null && !newIngredient.equals("")) {
			ingredient = new Ingredient(newIngredient.trim());
			ingredients.put(newIngredient, ingredient);
			em.persist(ingredient);
		}

		boolean checkProduitHaventIngredient = true;
		for (Ingredient ingredientProduit : produit.getIngredients()) {
			if (ingredientProduit.equals(ingredient)) {
				checkProduitHaventIngredient = false;
			}
		}
		if (checkProduitHaventIngredient && ingredient != null) {
			produit.getIngredients().add(ingredient);
		}
	}

	/**
	 * Traitement d'un allegène du fichier
	 * 
	 * @param em           Entity Manager
	 * @param produit      Produit de la ligne
	 * @param newAllergene Allegène à traiter présent sur la ligne
	 */
	public static void traitementAllergeneLine(EntityManager em, Produit produit, String newAllergene) {
		Allergene allergene = allergenes.get(newAllergene);

		if (allergene == null && !newAllergene.equals("")) {
			allergene = new Allergene(newAllergene.trim());
			allergenes.put(newAllergene, allergene);
			em.persist(allergene);
		}

		boolean checkProduitHaventAllergene = true;
		for (Allergene allergeneProduit : produit.getAllergenes()) {
			if (allergeneProduit.equals(allergene)) {
				checkProduitHaventAllergene = false;
			}
		}
		if (checkProduitHaventAllergene && allergene != null) {
			produit.getAllergenes().add(allergene);
		}
	}

	/**
	 * Traitement d'un additif du fichier
	 * 
	 * @param em         Entity Manager
	 * @param produit    Produit de la ligne
	 * @param newAdditif Additif à traiter présent sur la ligne
	 */
	public static void traitementAdditifLine(EntityManager em, Produit produit, String newAdditif) {
		Additif additif = additifs.get(newAdditif);

		if (additif == null && !newAdditif.equals("")) {
			additif = new Additif(newAdditif.trim());
			additifs.put(newAdditif, additif);
			em.persist(additif);
		}

		boolean checkProduitHaventAdditif = true;
		for (Additif additifProduit : produit.getAdditifs()) {
			if (additifProduit.equals(additif)) {
				checkProduitHaventAdditif = false;
			}
		}
		if (checkProduitHaventAdditif && additif != null) {
			produit.getAdditifs().add(additif);
		}
	}

}
