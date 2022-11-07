package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Produit
 */
@Entity
@Table(name = "PRODUIT")
public class Produit {

	/** Id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	/** Catégorie */
	@ManyToOne
	@JoinColumn(name = "CATEGORIE_ID", nullable = false)
	private Categorie categorie;

	/** Liste des marques */
	@ManyToMany
	@JoinTable(name="PRODUIT_MARQUE",
		uniqueConstraints = { @UniqueConstraint(columnNames = {"PRODUIT_ID","MARQUE_ID"}) },
	    joinColumns= @JoinColumn(name="PRODUIT_ID", referencedColumnName="ID"),
	    inverseJoinColumns= @JoinColumn(name="MARQUE_ID", referencedColumnName="ID")
	)
	private List<Marque> marques = new ArrayList<Marque>();

	/** Libelle */
	@Column(name = "LIBELLE", length = 255, nullable = false)
	private String libelle;

	/** Score nutritionnel */
	@Column(name = "SCORE_NUTRITIONNEL", length = 1, nullable = false)
	private String scoreNutritionnel;

	/** Liste des ingredients */
	@ManyToMany
	@JoinTable(name="PRODUIT_INGREDIENT",
		uniqueConstraints = { @UniqueConstraint(columnNames = {"PRODUIT_ID","INGREDIENT_ID"}) },
	    joinColumns= @JoinColumn(name="PRODUIT_ID", referencedColumnName="ID"),
	    inverseJoinColumns= @JoinColumn(name="INGREDIENT_ID", referencedColumnName="ID")
    )
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();

	/** Energie en joules pour 100g */
	@Column(name = "ENERGIE", nullable = true)
	private double energie;

	/** Quantité de graisse pour 100g */
	@Column(name = "QUANTITE_GRAISSE", nullable = true)
	private double quantiteGraisse;

	/** Présence ou non d'huilde de palme */
	@Column(name = "PRESENCE_HUILE_PALME", nullable = false)
	private boolean presenceHuilePalme;

	/** Liste des allergènes */
	@ManyToMany
	@JoinTable(name="PRODUIT_ALLERGENE",
		uniqueConstraints = { @UniqueConstraint(columnNames = {"PRODUIT_ID","ALLERGENE_ID"}) },
	    joinColumns= @JoinColumn(name="PRODUIT_ID", referencedColumnName="ID"),
	    inverseJoinColumns= @JoinColumn(name="ALLERGENE_ID", referencedColumnName="ID")
	)
	private List<Allergene> allergenes = new ArrayList<Allergene>();

	/** Liste des additifs */
	@ManyToMany
	@JoinTable(name="PRODUIT_ADDITIF",
		uniqueConstraints = { @UniqueConstraint(columnNames = {"PRODUIT_ID","ADDITIF_ID"}) },
	    joinColumns= @JoinColumn(name="PRODUIT_ID", referencedColumnName="ID"),
	    inverseJoinColumns= @JoinColumn(name="ADDITIF_ID", referencedColumnName="ID")
	)
	private List<Additif> additifs = new ArrayList<Additif>();

	public Produit() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param categorie         Catégorie
	 * @param libelle           Libelle
	 * @param scoreNutritionnel Score nutritionnel
	 */
	public Produit(Categorie categorie, String libelle, String scoreNutritionnel) {
		super();
		this.categorie = categorie;
		this.libelle = libelle;
		this.scoreNutritionnel = scoreNutritionnel;
		this.presenceHuilePalme = false;
	}

	/**
	 * Getter pour l'attribut id
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter pour l'attribut id
	 *
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter pour l'attribut categorie
	 *
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}

	/**
	 * Setter pour l'attribut categorie
	 *
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	/**
	 * Getter pour l'attribut marques
	 *
	 * @return the marques
	 */
	public List<Marque> getMarques() {
		return marques;
	}

	/**
	 * Getter pour l'attribut libelle
	 *
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Setter pour l'attribut libelle
	 *
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Getter pour l'attribut scoreNutritionnel
	 *
	 * @return the scoreNutritionnel
	 */
	public String getScoreNutritionnel() {
		return scoreNutritionnel;
	}

	/**
	 * Setter pour l'attribut scoreNutritionnel
	 *
	 * @param scoreNutritionnel the scoreNutritionnel to set
	 */
	public void setScoreNutritionnel(String scoreNutritionnel) {
		this.scoreNutritionnel = scoreNutritionnel;
	}

	/**
	 * Getter pour l'attribut energie
	 *
	 * @return the energie
	 */
	public double getEnergie() {
		return energie;
	}

	/**
	 * Setter pour l'attribut energie
	 *
	 * @param energie the energie to set
	 */
	public void setEnergie(double energie) {
		this.energie = energie;
	}

	/**
	 * Getter pour l'attribut quantiteGraisse
	 *
	 * @return the quantiteGraisse
	 */
	public double getQuantiteGraisse() {
		return quantiteGraisse;
	}

	/**
	 * Setter pour l'attribut quantiteGraisse
	 *
	 * @param quantiteGraisse the quantiteGraisse to set
	 */
	public void setQuantiteGraisse(double quantiteGraisse) {
		this.quantiteGraisse = quantiteGraisse;
	}

	/**
	 * Getter pour l'attribut presenceHuilePalme
	 *
	 * @return the presenceHuilePalme
	 */
	public boolean isPresenceHuilePalme() {
		return presenceHuilePalme;
	}

	/**
	 * Setter pour l'attribut presenceHuilePalme
	 *
	 * @param presenceHuilePalme the presenceHuilePalme to set
	 */
	public void setPresenceHuilePalme(boolean presenceHuilePalme) {
		this.presenceHuilePalme = presenceHuilePalme;
	}

	/**
	 * Getter pour l'attribut ingredients
	 *
	 * @return the ingredients
	 */
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Getter pour l'attribut allergenes
	 *
	 * @return the allergenes
	 */
	public List<Allergene> getAllergenes() {
		return allergenes;
	}

	/**
	 * Getter pour l'attribut additifs
	 *
	 * @return the additifs
	 */
	public List<Additif> getAdditifs() {
		return additifs;
	}

}
