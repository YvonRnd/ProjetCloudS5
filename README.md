## Instructions pour lancer le projet
## 1. Cloner le projet

Clonez ce dépôt Git sur votre machine locale :

git clone https://github.com/votre-utilisateur/votre-repo.git
cd votre-repo

## 2. Configuration des variables d’environnement

Si nécessaire, modifiez les variables dans le fichier .env ou créez un fichier .env.local pour personnaliser la configuration (facultatif).

Les paramètres principaux incluent :

APP_ENV=dev
APP_DEBUG=1
DATABASE_URL=mysql://db_user:db_password@mysql:3306/db_name

## 3. Construire et lancer les conteneurs

Exécutez la commande suivante pour construire et démarrer les conteneurs :

docker-compose up --build

## 4. Accéder à l’application

Une fois les conteneurs en cours d’exécution, accédez à votre navigateur et ouvrez : http://localhost:8000