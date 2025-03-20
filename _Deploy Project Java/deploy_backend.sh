#!/bin/bash

#Variable
PROJECT_DIR="C:/Users/enzod/IdeaProjects/prototype-ing1"
PROJECT_DIR_BACKEND="C:/Users/enzod/IdeaProjects/prototype-ing1/xmart-city-backend/target"
backend="xmart-zity-backend-1.0-SNAPSHOT-jar-with-dependencies.jar"
user_name="sirius"
host_name="	172.31.253.171" #ip vm  backend
SOURCE_FILE="/chemin/local/fichier.txt"
DESTINATION="sirius@172.31.250.22:backend.jar"


cd $PROJECT_DIR || { echo "Erreur : Impossible d'accéder au dossier du projet"; exit 1; }
# cd "C:\Users\enzod\IdeaProjects\prototype-ing1"
mvn clean

if [[ $? -ne 0 ]]; then
	echo "Erreur : échec mvn clean" 
	exit 1
fi

mvn package 


if [[ $? -ne 0 ]]; then
	echo "Erreur : échec mvn package" 
	exit 1
fi

#cd xmart-city-backend/target
cd $PROJECT_DIR_BACKEND || { echo "Erreur : Impossible d'accéder au dossier du projet"; exit 1; }
if [[ $? -ne 0 ]]; then
    echo "Erreur : dossier target non trouvé"
    exit 1
fi


# Utilisation de scp pour transférer le fichier
scp $backend $DESTINATION

# Vérification si le transfert a réussi
if [ $? -eq 0 ]; then
    echo "Transfert réussi !"
else
    echo "Erreur lors du transfert."
fi
 