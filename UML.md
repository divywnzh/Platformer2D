```mermaid
classDiagram
    class Game {
        +update()
        +render()
    }
    class GamePanel {
        +updateGame()
        +paintComponent()
    }
    class Entity {
        #float x
        #float y
        +updateHitbox()
    }
    class Player {
        +attack()
        +jump()
    }
    class Enemy {
        +patrol()
    }
    class EnemyManager {
        +updateEnemies()
    }
    class State {
        +update()
        +draw()
    }
    class Playing {
        +checkEnemyHit()
    }
    class Menu {
        +handleButtons()
    }
    class LevelManager {
        +loadNextLevel()
    }
    class ObjectManager {
        +updateObjects()
    }
    class GameObject {
        #int objType
        +update()
    }
    class LoadSave {
        +LoadLevels()
        +LoadSprites()
    }

    Game *-- GamePanel
    Entity <|-- Player
    Entity <|-- Enemy
    Enemy <|-- Crabby
    State <|-- Playing
    State <|-- Menu
    State <|-- GameOptions
    GameObject <|-- Cannon
    GameObject <|-- Potion
    GameObject <|-- Projectile
    Playing o-- Player
    Playing o-- LevelManager
    Playing o-- EnemyManager
    Playing o-- ObjectManager
    EnemyManager o-- Enemy
    ObjectManager o-- GameObject
    LevelManager o-- Level

