# Projet Java 4A S2 MIN1  


Module : Langage Java  
Sujet : Rentmanager - Gestion de locations de voitures  


Instructions pour lancer le site :  
- Télécharger le code source  
- Installer Apache Maven : https://maven.apache.org/download.cgi  
- Ouvrir le projet, aller sur le fichier java FillDataBase dans /src/main/java/com/epf/rentmanager/persistence et run ce fichier  
- Une fois le fichier exécuté et le "Success" affiché dans le terminal, ouvrir un terminal et se placer dans l'arborescence : /Rentmanager-Project  
- Écrire la commande : "mvn tomcat7:run" pour lancer le serveur Apache  
- Si le tomcat s'exécute sans problème, aller dans un navigateur et se rendre à l'adresse : http://localhost:8080/rentmanager. Vous êtes maintenant sur le site web Rent Manager  
- En cas d'erreur avec le tomcat, écrire la commande "mvn clean install tomcat7:run". Si l'erreur persiste, vérifier que Maven est correctement installé.
    
    
Fonctionnalités réalisées :
- Client : affichage, création, suppression, modification, détails et recherche des clients par nom
- Véhicule : affichage, création, suppression, modification
- Réservations : affichage, création, suppression, modification
- Toutes les contraintes sont respectées
- Aucun tests réalisés
- Difficultés : ajouter l'attribut modèle à la table véhicule de la base de données
