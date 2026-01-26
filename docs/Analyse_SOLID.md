# Analyse SOLID (application initiale → version refactorisée)

Ce document résume les **principes SOLID** qui étaient problématiques dans la version initiale, et comment la version actuelle corrige ces problèmes (souvent via des patrons de conception).

## SRP — Single Responsibility Principle

**Problème (avant)**
- La logique de commande mélangeait plusieurs responsabilités : choix/description du plat, calcul du prix, paiement, persistance, notifications, et orchestration de la préparation.

**Correction (maintenant)**
- [FoodTruckManager.java](../src/main/java/ca/qc/cmaisonneuve/amp/projet/foodtruck/FoodTruckManager.java) reste un **orchestrateur** (responsabilité claire).
- Les responsabilités “variables” sont séparées :
  - Menu/plat/options : package `menu` (Decorator)
  - Paiement : package `paiement` (Strategy)
  - Notifications : package `notification` (Observer)
  - Persistance : [SqlDatabasePersistence.java](../src/main/java/ca/qc/cmaisonneuve/amp/projet/foodtruck/persistence/SqlDatabasePersistence.java)

## OCP — Open/Closed Principle

**Problème (avant)**
- Les ajouts (nouveau plat / nouveau paiement / nouveau canal de notification) forçaient typiquement des `if/switch` à modifier dans plusieurs endroits.

**Correction (maintenant)**
- **Paiement** : ajout d’un mode via une nouvelle implémentation de `PaymentMethod` et un enregistrement dans le `PaymentGateway`.
- **Notifications** : ajout d’un canal via une nouvelle implémentation de `OrderEventListener`.
- **Plats/options** : ajout d’un plat ou d’une option via de nouvelles classes `Plat` / décorateurs.

Résultat : l’ajout de `SALADE`, `VIREMENT`, `SMS` s’est fait principalement par **ajouts** de classes, sans réécrire l’architecture.

## DIP — Dependency Inversion Principle

**Problème (avant)**
- Le “haut niveau” (gestion de commande) dépendait directement de détails concrets (ex. paiement/notification codés en dur), ce qui rendait le code difficile à étendre/tester.

**Correction (maintenant)**
- Notifications via abstractions Observer : `OrderEventPublisher` + `OrderEventListener`.
- Paiement via abstraction Strategy : `PaymentMethod`.
- Cuisine : dépendance vers des **interfaces fines** (voir ISP).

## ISP + LSP — Interfaces fines & substituabilité (Cuisine)

**Problème (avant)**
- Une interface “cuisine” trop grosse forçait certaines implémentations à exposer des opérations qu’elles ne pouvaient pas faire.
- Concrètement, une station froide ne peut pas cuire / garder au chaud : cela menait à des méthodes “non supportées” (erreurs ou exceptions), ce qui casse le principe de substituabilité (LSP).

**Correction (maintenant)**
- La cuisine est découpée en interfaces simples (ISP) :
  - `CuissonService`, `AssemblageService`, `ExtrasService`, `ConservationFroidService`, `ConservationChaudService`
- Une station froide implémente seulement ce qu’elle supporte via `CuisineFroideStation`.
- `FoodTruckManager` dépend de ces abstractions plutôt que d’une interface monolithique.

## Bonus maintenabilité (évite le parsing de texte)

Au lieu de déduire la logique à partir de `getDescription()` (fragile), les options sont portées par les objets `Plat` eux-mêmes (ex. `hasExtraFromage()` / `isEpice()`), ce qui rend la préparation et la tarification plus robustes.
