# Drone Fleet Simulator

Simulateur de flotte de drones de livraison se déplaçant sur une carte urbaine rectangulaire avec obstacles.

## Compilation

```bash
mvn clean package
```

## Exécution

```bash
java -jar target/drone-simulator-1.0.jar
```

## Tests

```bash
mvn test
```

5 classes de test couvrent les 3 versions : `DroneTest`, `SimulatorMapTest`, `ConfigFileReaderTest`, `SimulatorBasicTest`, `SimulatorConcurrentTest`.

## Modes

1. **Mode Basique (Séquentiel)** : Les drones se déplacent un par un, affichage après chaque commande.
2. **Mode Concurrent (Threads)** : Les drones se déplacent simultanément dans des threads séparés, carte mise à jour en direct.
3. **Mode Serveur (Réseau)** : Le serveur exécute la simulation concurrente et renvoie les positions finales au client.

## Mode Serveur

**Terminal 1 (serveur)** :
```bash
java -jar target/drone-simulator-1.0.jar
Choisissez un mode : 3
```

**Terminal 2 (client)** :
```bash
java -cp target/drone-simulator-1.0.jar com.drone.simulator.client.DroneClient
```

Alternativement, lancer directement :
```bash
# Terminal 1
java -cp target/drone-simulator-1.0.jar com.drone.simulator.server.DroneServer
# Terminal 2
java -cp target/drone-simulator-1.0.jar com.drone.simulator.client.DroneClient
```

## Format du fichier drones.txt

```
LARGEUR HAUTEUR
nombre_de_drones
x y orientation commandes
```

Exemple :
```
20 20
2
0 5 N LMLMLMLMM
5 0 S MMRMMRMRRM
```

## Commandes
- `L` : Rotation 90° à gauche
- `R` : Rotation 90° à droite
- `M` : Avancer d'une case
- `B` : Reculer d'une case

## Orientations
- `N` : Nord (haut)
- `S` : Sud (bas)
- `E` : Est (droite)
- `W` : Ouest (gauche)
