## Analyse SOLID
Cette section résume les principales violations des principes SOLID observées dans l’implémentation initiale, avec des exemples concrets et des pistes de correction.

## 1. SRP — Single Responsibility Principle (Responsabilité unique)
Idée générale : une classe ne devrait faire qu’une seule chose.
Problème : la classe centrale fait trop de tâches différentes.
Exemples :
• 	 calcule les prix, gère le paiement, sauvegarde en base, envoie les notifications et coordonne la cuisine.
• 	La méthode  mélange logique métier, affichage console et appels à des services externes.
Conséquences :
• 	Le moindre changement (nouveau plat, nouveau paiement, nouvelle notification, nouvelle règle de prix) oblige à modifier la même classe, ce qui augmente les risques de bugs.
Solution :
• 	Séparer les responsabilités : un composant pour les prix, un pour le paiement, un pour la persistance, un pour la notification, un pour la préparation.
• 	Garder  uniquement comme orchestrateur.

## 2. OCP — Open/Closed Principle (Ouvert/Fermé)
Idée générale : on doit pouvoir ajouter des fonctionnalités sans modifier le code existant.
Problème : beaucoup de  et  obligent à modifier le code dès qu’on ajoute quelque chose.
Exemples :
• 	Ajouter un plat → modifier la logique de prix et la logique de préparation.
• 	Ajouter un mode de paiement → modifier le code de paiement.
• 	Ajouter un type de notification → modifier le code qui envoie les courriels.
Conséquences :
• 	Le code devient lourd, complexe, et plus fragile.
Solution :
• 	Paiement : utiliser une stratégie () avec des implémentations (, , etc.).
• 	Plats : modéliser les plats et options de manière extensible (ex. Decorator).
• 	Préparation : utiliser une fabrique ou un registre pour associer un type de plat à sa préparation.

## 3. ISP — Interface Segregation Principle (Ségrégation d’interfaces)
Idée générale : mieux vaut plusieurs petites interfaces ciblées qu’une grosse interface que tout le monde doit implémenter.
Problème :  regroupe des méthodes que certaines cuisines ne peuvent pas supporter.
Exemple :
• 	 ne peut pas cuire ni garder au chaud, et doit lancer des exceptions.
Conséquences :
• 	Les implémentations exposent des méthodes inutiles.
• 	Le code devient plus rigide et plus difficile à étendre.
Solution :
• 	Découper en interfaces plus petites :
• 	
• 	
• 	
• 	
• 	
• 	Chaque station implémente seulement ce qu’elle sait faire.

## 4. LSP — Liskov Substitution Principle (Substitution)
Idée générale : une implémentation doit pouvoir remplacer une autre sans casser le comportement attendu.
Problème : une station froide implémente  mais lance des exceptions pour certaines méthodes.
Conséquence :
• 	Le code appelant doit gérer des cas particuliers, ce qui casse l’uniformité.
Solution :
• 	La correction ISP règle aussi ce problème en supprimant les méthodes non pertinentes du contrat.

## 5. DIP — Dependency Inversion Principle (Inversion des dépendances)
Idée générale : dépendre d’interfaces, pas de classes concrètes.
Problème :  crée directement ses dépendances (, , etc.).
Conséquences :
• 	Difficile de tester, de remplacer une implémentation ou d’en ajouter une nouvelle.
Solution :
• 	Introduire des interfaces (, , , etc.).
• 	Injecter les implémentations via le constructeur.
• 	L’application principale () devient le point où tout est assemblé.