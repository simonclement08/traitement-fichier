package entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Catégorie
 */
@Entity
@Table(name = "CATEGORIE")
public class Categorie {

	/** Id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	/** Libelle */
	@Column(name = "LIBELLE", length = 150, nullable = false)
	private String libelle;

	/** Liste des produits */
	@OneToMany(mappedBy = "categorie")
	private List<Produit> produits;

	/** Constructeur */
	public Categorie() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param libelle Libelle
	 */
	public Categorie(String libelle) {
		super();
		this.libelle = libelle;
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
	 * Getter pour l'attribut produits
	 *
	 * @return the produits
	 */
	public List<Produit> getProduits() {
		return produits;
	}

}
