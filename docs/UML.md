# Diagramme UML (Mermaid)

classDiagram
direction LR

%% ====== Commande / orchestration ======
class FoodTruckManager {
  -paymentProcessor : PaymentProcessor
  -database : OrderPersistence
  -cuisine : CuisineService
  -comptoirFroid : CuisineFroideStation
  -menu : Menu
  -preparations : Map~String, PreparationStrategy~
  -eventPublisher : OrderEventPublisher
  +traiterCommande(destinataire, type, extraFromage, epice, modePaiement, tailleSalade, vinaigretteSalade) void
  +registerPreparation(type, strategy) void
}

class Menu {
  -plats : Map~String, Supplier~
  +createBase(type) Plat
  +register(type, factory) void
}

FoodTruckManager --> Menu : utilise
FoodTruckManager --> PaymentProcessor : dépend
FoodTruckManager --> OrderPersistence : dépend
FoodTruckManager --> OrderEventPublisher : publie
FoodTruckManager --> PreparationStrategy : exécute

%% ====== Plats (Decorator) ======
class Plat {
  <<interface>>
  +getType() String
  +getDescription() String
  +getPrix() double
  +extraFromagePrix() double
  +epicePrix() double
  +hasExtraFromage() boolean
  +isEpice() boolean
}

class BurgerPlat
class TacoPlat
class WrapPlat
class SaladePlat

Plat <|.. BurgerPlat
Plat <|.. TacoPlat
Plat <|.. WrapPlat
Plat <|.. SaladePlat

class PlatOptionDecorator {
  <<abstract>>
  #base : Plat
}
Plat <|.. PlatOptionDecorator
PlatOptionDecorator o-- Plat : wrap

class ExtraFromageOption
class EpiceOption
class SaladeTailleOption
class SaladeVinaigretteOption

PlatOptionDecorator <|-- ExtraFromageOption
PlatOptionDecorator <|-- EpiceOption
PlatOptionDecorator <|-- SaladeTailleOption
PlatOptionDecorator <|-- SaladeVinaigretteOption

Menu --> Plat : crée

%% ====== Paiement (Strategy) ======
class PaymentProcessor {
  <<interface>>
  +payer(mode, montant) boolean
}

class PaymentGateway {
  -methods : Map~String, PaymentMethod~
  +register(code, method) void
  +payer(mode, montant) boolean
}
PaymentProcessor <|.. PaymentGateway

class PaymentMethod {
  <<interface>>
  +pay(montant) void
}
class CardPayment
class CashPayment
class BankTransferPayment

PaymentMethod <|.. CardPayment
PaymentMethod <|.. CashPayment
PaymentMethod <|.. BankTransferPayment
PaymentGateway o-- PaymentMethod : contient

%% ====== Persistance (DIP) ======
class OrderPersistence {
  <<interface>>
  +sauvegarderCommande(dest, type, prix, extraFromage, epice) void
}
class SqlDatabasePersistence
OrderPersistence <|.. SqlDatabasePersistence

%% ====== Notifications (Observer) ======
class OrderEventPublisher {
  -listeners : List~OrderEventListener~
  +addListener(listener) void
  +removeListener(listener) void
  +publish(event) void
}

class OrderEventListener {
  <<interface>>
  +onOrderEvent(event) void
}

class EmailOrderEventListener
class SmsOrderEventListener

OrderEventListener <|.. EmailOrderEventListener
OrderEventListener <|.. SmsOrderEventListener

OrderEventPublisher o-- OrderEventListener : notifie

class OrderEvent {
  -type : OrderEventType
  -recipient : String
  -itemType : String
  -amount : double
}

class OrderEventType {
  <<enumeration>>
  PLACED
  READY
}

OrderEvent --> OrderEventType
OrderEventPublisher --> OrderEvent

%% ====== Cuisine (Strategy préparation) ======
class PreparationStrategy {
  <<interface>>
  +prepare(plat) boolean
}

class BurgerPreparation
class TacoPreparation
class WrapPreparation
class SaladePreparation

PreparationStrategy <|.. BurgerPreparation
PreparationStrategy <|.. TacoPreparation
PreparationStrategy <|.. WrapPreparation
PreparationStrategy <|.. SaladePreparation

class CuisineService {
  <<interface>>
}
class CuisineFroideStation {
  <<interface>>
}

BurgerPreparation --> CuisineService
TacoPreparation --> CuisineService
WrapPreparation --> CuisineFroideStation
SaladePreparation --> CuisineFroideStation
