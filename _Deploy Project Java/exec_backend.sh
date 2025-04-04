#!/bin/bash

#Variable
user_name="sirius"
host_name="172.31.250.22" #ip vm  backend
file="backend.jar"
PROCESS_NAME="java"

ssh $user_name@$host_name <<EOF
    echo "Connexion réussie à la VM : $host_name"
	
	#kill du processus
	
	# Tuer le processus
pkill $PROCESS_NAME

# Vérifier si le processus a été tué
if [ $? -eq 0 ]; then
    echo "Le processus '$PROCESS_NAME' a été terminé avec succès."
else
    echo "Erreur : Impossible de terminer le processus '$PROCESS_NAME' ou il n'existe pas."
fi
	
    if [[ ! -f "$file" ]]; then
        echo "Erreur : fichier $file introuvable sur la VM"
        exit 1
    fi
	java -jar $file
EOF

exit 


