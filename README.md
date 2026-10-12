# MUSLIM MOVIES Android APK

Ce projet transforme le site MUSLIM MOVIES en application Android hors ligne grâce à une WebView qui charge les fichiers locaux. Les liens externes ouvrent le navigateur.

## Construire gratuitement depuis Android
La méthode recommandée utilise GitHub Actions. Le téléphone n'a pas besoin de compiler Android localement.

### Option Termux + GitHub CLI
1. Installe Termux depuis une source fiable.
2. Copie le ZIP dans `Download` puis ouvre Termux.
3. Installe Git et GitHub CLI :
   ```sh
   pkg update
   pkg install git gh unzip
   ```
4. Autorise l'accès aux téléchargements et décompresse le ZIP (adapte le chemin si nécessaire) :
   ```sh
   termux-setup-storage
   cd ~/storage/downloads
   unzip MUSLIM-MOVIES-ANDROID.zip
   cd MUSLIM-MOVIES-ANDROID
   ```
5. Connecte GitHub :
   ```sh
   gh auth login
   ```
   Choisis GitHub.com, HTTPS et l'authentification via navigateur.
6. Crée le dépôt et envoie le code :
   ```sh
   git init
   git add .
   git commit -m "Initial MUSLIM MOVIES Android app"
   git branch -M main
   gh repo create MUSLIM-MOVIES-ANDROID --public --source=. --remote=origin --push
   ```
   Le dépôt public est nécessaire pour suivre la méthode sans secrets GitHub additionnels ; ne place aucun mot de passe ou clé privée dans le code.
7. Sur GitHub, ouvre le dépôt > Actions > `Build MUSLIM MOVIES APK` > `Run workflow`.
8. Quand le workflow est terminé, ouvre le run réussi et télécharge l'artifact `MUSLIM-MOVIES-APK`. Décompresse le ZIP d'artifact pour obtenir `app-debug.apk`, puis installe-le.

## Notes
- APK debug : signé avec une clé de débogage générée par Gradle; pratique pour test, pas idéale pour distribution publique.
- Cette première version affiche le catalogue de démonstration inclus dans l'app. Les fiches ouvrent des recherches web, elles ne contiennent pas de films préchargés.
- Android peut demander d'autoriser l'installation depuis le navigateur ou le gestionnaire de fichiers. N'installe que l'APK que tu as compilé.
- `targetSdk` et outils de build peuvent devoir être mis à jour si les exigences Android changent.
