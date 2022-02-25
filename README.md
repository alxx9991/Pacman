# Pacman Recreation!
This is a recreation of the pacman game. This project is purely built as a coding exercise/for fun, and is not intended to be used for commerical purposes. I have no affiliation with the original creators.

## How to run
In the directory called "Final", type the command: `gradle run`
You must have JRE and Gradle installed to run.

### How to play
Use the arrow keys to move around. Eat all the fruit on the map to win, while avoiding the ghosts. The number of lives is displayed in the bottom. Press the spacebar to activate debug mode, which shows the direction of ghost movement.

### Configuation file
In the file "config.json" you can choose the game speed (1x or 2x), the number of lives and the duration of ghost chase/scatter cycles. You can also configure superfruit duration.

### Ghosts
Each ghost has a different algorithm for moving on the map. Press spacebar to see where each ghost is moving towards. They use a vector based greedy algorithm to chase the player. In scatter mode, they return to their corner.

### Superfruit
Superfruit is a larger looking fruit. It sends ghosts into panic mode for a short time (specified in the config file) where they move at random and can be killed by Pacman. Ghosts respawn if Pacman dies.

