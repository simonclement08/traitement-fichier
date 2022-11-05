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
 * Marque
 */
@Entity
@Table(name = "MARQUE")
public class Marque {

	/** Id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	/** Libelle */
	@Column(name = "LIBELLE", length = 20, nullable = false)
	private String libelle;

	/** Liste des produits */
	@OneToMany(mappedBy = "marque")
	private List<Produit> produits;

	/** Constructeur */
	public Marque() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param libelle Libelle
	 */
	public Marque(String libelle) {
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
