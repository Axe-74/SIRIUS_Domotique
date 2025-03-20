#!/bin/bash

#Variable
PROJECT_DIR="C:/Users/enzod/IdeaProjects/prototype-ing1/xmart-frontend/target"
frontend="xmart-frontend-1.0-SNAPSHOT-jar-with-dependencies.jar"


cd $PROJECT_DIR || { echo "Erreur : Impossible d'accéder au dossier du projet"; exit 1; }

java -jar $frontend
if [[ $? -ne 0 ]]; then
echo "Erreur : execution $frontend impossible" 
exit 1
fi


