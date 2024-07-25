# Platformer 2D

## Description
Platformer 2D is an exciting Java-based 2D platformer game that combines classic gameplay mechanics with modern design principles. With multiple levels, enemies, and smooth controls for player movements and action. The project has tried to follow object-oriented programming practices to its best.


<img src="https://i.imgur.com/i516Pch.png" alt="okay" width="300" height="150"><img src="https://i.imgur.com/2QCox1F.png" alt="okay" width="300" height="150">

### Note
Modular nature of codebase makes it easy to maintain and extend. You may clone and add your own functionalities.


## Features
- **Engaging Gameplay:** Smooth running, jumping, and attacking mechanics
- **Room for Enemy Variety:** Currently featuring only one enemy type (Crabby)
- **Level Design:** Multiple levels with different difficulties and unique challenges.
- **Modular Levels:** A type of pixel is designated to be specific game element (tiles, enemies, objects, player spawn)->(pixel-to-element mapping) This approach simplifies level design. Levels are created as .png files, LoadSave then code then adds these levels to the game.
- **Interactive Objects:** Interact with various game objects like cannons, potions, and spikes
- **Polished UI:** Intuitive menus, pause functionality, and in-game overlays
- **Performance Optimized:** Robust game loop targeting 120 FPS and 200 UPS
- **Save System:** Save and load your game progress
- **Audio Management:** To be added


<img src="https://i.imgur.com/Wl41Eao.png" alt="okay" width="300" height="150"><img src="https://i.imgur.com/qaypHAv.png" alt="okay" width="300" height="150">



## Used

- **Tech Stack:** Java,  Java AWT and Swing for rendering
- **Resource Management:** Efficient sprite atlas and level data loading
- **Performance:** FPS and UPS game loop implementation for consistent updates and rendering
- **Serialization:** Java's built-in serialization for save/load functionality


## Object-Oriented Design

The core of the game's design revolves around the interaction of well-structured classes:

- **Entities (Base Class):**  The `Entity` class is the cornerstone for all game objects. It provides essential properties and methods for:
    - Position management (x, y coordinates)
    - Movement capabilities
    - Collision detection and response
- **Inheritance & Polymorphism:** Specialized classes like `Player` and `Enemy` inherit from the `Entity` class. This means they:
    - Share common functionalities (position, movement, collision)
    - Override or extend methods to implement unique behaviors (player controls, enemy AI)
- **Encapsulation:** Each class is responsible for its own data and logic. This encapsulation promotes:
    - Code organization and modularity
    - Easier maintenance and debugging
- **Managers:** To handle groups of objects efficiently, the game employs manager classes. Examples include:
    - `EnemyManager`: Controls the spawning, updates, and interactions of multiple enemies.
    - `LevelManager`:  Manages the loading, layout, and transitions between game levels.

## Game Loop and Performance

- **Decoupling FPS (Frames Per Second) and UPS (Updates Per Second):** 
    - **FPS:** Controls how often the visuals are updated (how many frames are drawn per second).
    - **UPS:** Determines how often the game logic is processed (physics, AI, input handling).
    - Decoupling these ensures consistent game logic even if the frame rate fluctuates.
- **Delta Timing (`deltaU`, `deltaF`):**
    - The game tracks the time elapsed between updates (`deltaU`) and frames (`deltaF`).
    - This allows the game to adjust its calculations based on the actual time passed, making it adaptable to different hardware and frame rates.
- **Prioritizing Updates:**
    - The game loop prioritizes processing game logic (updates) before rendering new frames.
    - This ensures that even if the rendering takes longer than expected, the game logic remains consistent.
 
## Installation

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Git (optional for cloning the repository)

### Steps
1. Clone the repository (or download the ZIP file): git clone https://github.com/yourusername/Platformer2D.git

2. Navigate to the project directory:
cd Platformer2D

3. Compile the Java files:
javac -d bin src/**/*.java
   
4. Run the game:
java -cp bin main.MainClass
   

Alternatively, if you're using an IDE like Eclipse or IntelliJ IDEA:

1. Open the project in your IDE
2. Ensure the project is set up with the correct JDK
3. .classpath ensures src and res are recognized as the source, but in case it does not, right-click on each folder (src, res) and select "Build Path" > "Use as Source Folder."
4. Run the `MainClass.java` file located in the `src/main/` directory

Enjoy!
