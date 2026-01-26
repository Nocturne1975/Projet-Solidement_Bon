# Plan de tests (mini)

Ce plan valide les fonctionnalités demandées (SALADE, VIREMENT, SMS, notifications à 2 moments) ainsi que les régressions sur les plats existants.

## Préparation

1. Compiler :
   - `mvn clean package`
2. Lancer :
   - `java -jar target/food-truck-1.0.0.jar`

> Note (Windows) : si `mvn clean` échoue à supprimer `target`, fermer les terminaux/processus qui utilisent le dossier, ou supprimer `target` manuellement.

## Tests fonctionnels (manuel)

### T1 — Notification EMAIL (commande passée + prête)
- Entrées :
  - Plat : `BURGER`
  - Notification : `EMAIL`
  - Destinataire : une adresse courriel (ex: `client@test.com`)
  - Extra fromage : `n`
  - Épicé : `n`
  - Paiement : `COMPTANT`
- Résultat attendu :
  - 2 notifications EMAIL s’affichent dans la console :
    - une confirmation de commande (PLACED)
    - une notification “commande prête” (READY)

### T2 — Notification SMS (commande passée + prête)
- Entrées :
  - Plat : `TACO`
  - Notification : `SMS`
  - Destinataire : un numéro (ex: `5145551234`)
  - Extra fromage : `o`
  - Épicé : `o`
  - Paiement : `CARTE`
- Résultat attendu :
  - 2 lignes `[SMS]` (PLACED puis READY)
  - La description du plat inclut les options (extra fromage, épicé)

### T3 — Paiement VIREMENT (nouveau mode)
- Entrées :
  - Plat : `WRAP`
  - Notification : `EMAIL`
  - Destinataire : `client@test.com`
  - Extra fromage : `o`
  - Épicé : `n`
  - Paiement : `VIREMENT`
- Résultat attendu :
  - Le paiement affiche un message de type “Virement bancaire recu: <montant>”
  - La commande se poursuit (sauvegarde + notifications + préparation)

### T4 — SALADE (petite, vinaigrette maison)
- Entrées :
  - Plat : `SALADE`
  - Notification : `SMS`
  - Destinataire : `5145551234`
  - Extra fromage : `n`
  - Épicé : `n`
  - Taille : `PETITE`
  - Vinaigrette : `MAISON`
  - Paiement : `COMPTANT`
- Résultat attendu :
  - Le journal de préparation indique une commande prête avec description contenant :
    - “salade petite”
    - “vinaigrette maison”
  - 2 SMS émis (PLACED + READY)

### T5 — SALADE (grande, vinaigrette cesar, options existantes)
- Entrées :
  - Plat : `SALADE`
  - Notification : `EMAIL`
  - Destinataire : `client@test.com`
  - Extra fromage : `o`
  - Épicé : `o`
  - Taille : `GRANDE`
  - Vinaigrette : `CESAR`
  - Paiement : `CARTE`
- Résultat attendu :
  - La description inclut : “salade grande”, “vinaigrette cesar”, “extra fromage”, “epice”
  - Le prix est supérieur au cas “petite” (supplément de taille)
  - 2 EMAIL s’affichent (PLACED + READY)

### T6 — Validation entrées: notification invalide
- Entrées :
  - Plat : `BURGER`
  - Notification : `PIGEON`
  - (le reste au choix)
- Résultat attendu :
  - L’application retombe sur `EMAIL` par défaut (message d’avertissement affiché)

### T7 — Validation entrées: paiement invalide
- Entrées :
  - Plat : `TACO`
  - Notification : `EMAIL`
  - Paiement : `BITCOIN`
- Résultat attendu :
  - L’application retombe sur `COMPTANT` par défaut (message d’avertissement)
  - Le paiement est effectué (message “Argent comptant recu: …”)

### T8 — Régression: WRAP ne doit pas échouer avec option épicé
- Entrées :
  - Plat : `WRAP`
  - Épicé : `o`
  - (le reste au choix)
- Résultat attendu :
  - La commande se traite sans erreur
  - Le prix ne change pas (dans l’implémentation actuelle, épicé coûte 0 pour WRAP)

## Tests “structure/OCP” (revue rapide)

### T9 — Ajout d’un nouveau mode de paiement (preuve OCP)
- Étape : ajouter une nouvelle classe `PaymentMethod` et l’enregistrer dans `PaymentGateway`.
- Résultat attendu :
  - Aucun changement requis dans la logique du gestionnaire de commande (pas de nouveau `if/else` dans le traitement).

### T10 — Ajout d’une nouvelle option (preuve OCP)
- Étape : ajouter un nouveau Decorator d’option de plat (ex: “sans oignons”).
- Résultat attendu :
  - Aucun changement requis dans les classes des plats de base.
  - L’option se compose avec les autres options.
