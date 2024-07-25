```mermaid
graph TD
    subgraph src["src"]
        subgraph Main
            Game --> GamePanel & GameWindow
            GamePanel --renders--> Playing & Menu & GameOptions
            GameWindow --contains--> GamePanel
            MainClass --starts--> Game
        end

        subgraph Gamestates["Gamestates"]
            Playing --> Player & LevelManager & EnemyManager & ObjectManager
            Menu --> MenuButton
            GameOptions --> AudioOptions
        end

        subgraph Entities["Entities"]
            Entity --> Player
            Entity --> Enemy
            EnemyManager --> Enemy
        end
        
        subgraph Levels
            LevelManager --> Level
        end
        
        subgraph Objects
            ObjectManager --> Cannon & Projectile & Potion & Spike
        end
        
        subgraph UI["UI"]
            AudioOptions --> SoundButton & VolumeButton
            GameOverOverlay
            LevelCompletedOverlay
            PauseButton
            PauseOverlay
            UrmButton
        end

        subgraph Inputs["Inputs"]
            Game --> KeyboardInputs & MouseInputs
        end
        
        subgraph Utilz
            LoadSave
            HelpMethods
            Constants
        end
    end

    res("res")
