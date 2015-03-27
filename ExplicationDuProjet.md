# Guitta_Akoury_9366-F
smb214

I- Création de la base de données
La base de donné « gdf »
- Les informations d'identification utilisateur pour mon application sont stockées dans une base de données alors j’ai utilisé le domaine d'authentification GlassFish JDBC (jdbcRealm).
- Pour  la gestion du domaine de sécurité j’ai créé 2 tables : utilisateurs et groupe.

Création des tables requises sous MySQL
les commandes SQL de création de ces 2 tables sont : 

1- CREATE TABLE `utilisateurs` (
	 `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
	`password` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
	PRIMARY KEY (`username`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
INSERT INTO `utilisateurs` (`username`, `password`) VALUES
	('Guitta', 'e85fb4e5cd62509d621f5b2e719613b0263580e7e8ef77865852b0b8ebe621dc'),
	('Nadim', '7f1282974437f511dacee4013a0814bf7cbab38dd07040a4b4a82cebd50d134e');

2- CREATE TABLE `groupe` (
       `id` int(11) NOT NULL AUTO_INCREMENT COMMENT pour une utilisation simplifié avec JPA,
       `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
       `groupname` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
       `Description` text COLLATE utf8_unicode_ci,
       PRIMARY KEY (`id`),
       UNIQUE KEY `username_2` (`username`,`groupname`),
       UNIQUE KEY `username_3` (`username`,`groupname`),
       KEY `username` (`username`,`groupname`),
       CONSTRAINT `utilisateur` FOREIGN KEY (`username`) REFERENCES `utilisateurs` (`username`)
       ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


II- Configuration du JDBC Realm sous “GlassFish Administration Console”
	Définir :
		la référence JNDI vers cette base de donnés dans Glassfish
		La connexion pool
		La ressource
	Définir le Realm dans Glassfish


III- Création du projet: Création de l’application web JSF - PrimeFaces sous “NetBeans IDE”
    New Project …
      Java Web > Web Application
      Server : GlassFish
      Java EE Version : Java EE 7 Web
      Framework : JavaServer Faces (JSF)
      Component : PrimeFaces

 Choisir un mécanisme d'authentification
    login.xhtml 
      	j_security_check

    loginerror.xhtml
    
  IV- Configuration des contraintes de sécurité dans web.xml et Utilisation du Realm
      Login Configuration
        L'autentification à base de Formulaire « Form » et déclaration du « Form Login Page », « Error Page » et « Realm Name »
      Security Roles
        Déclaration de 2 rôles de sécurité : admin et users.
      Security Constraints
        Déclaration d’une contrainte de sécurité par rôle, donc de contrainte de sécurité : AdminConstraint et UserConstraint 
          Transport Guarantee : CONFIDENTIAL (SSL Secure Socket Layer) : sécurise les données entre le client et le serveur (https) 
      Security Constraints
        L’administrateur (admin) doit avoir accès à tout.

V- Configuration de la sécurité “Role Mappings” dans glassfish-web.xml
    Security Role Mappings
      Ajouter à chaque rôle son “groupname” correspondant dans la table “groupe”
      
VI- New Entity Classes From Database
    Database Tables
    Create Data Source
    Entity Classes
    Mapping Options – Collection Type : java.util.list

