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

## Modes

1. **Mode Basique (Séquentiel)** : Les drones se déplacent un par un
2. **Mode Concurrent (Threads)** : Les drones se déplacent simultanément
3. **Mode Serveur (Réseau)** : Configuration envoyée par le réseau

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

### Commandes
- `L` : Rotation 90° à gauche
- `R` : Rotation 90° à droite
- `M` : Avancer d'une case
- `B` : Reculer d'une case

### Orientations
- `N` : Nord (haut)
- `S` : Sud (bas)
- `E` : Est (droite)
- `W` : Ouest (gauche)
