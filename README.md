# DominationGame
A 2D game written in Java and libGDX based on Phage Wars concept.

# Build prerequisites
    Java SE Development Kit (JDK) 8
    Java SE Runtime Environment (JRE) 8

# Build instructions
```
git clone https://github.com/daktyl/DominationGame.git
cd DominationGame/
chmod +x gradlew
./gradlew desktop:dist
```
To run the game execute the following:
```
cd desktop/build/libs
java -jar desktop-1.0.jar
```

# How to play?
The goal is to overtake all cells - both hostile and neutral ones.
Each non-neutral cell produces bacteria at a steady rate. The bacteria amount cannot be greater than 100 per cell.
When the game begins each player has one cell, the other ones are neutral and don't belong to any player.

To capture a hostile or neutral cell the player must send the bacteria from one of his cells.
The amount that is sent is always equal to the half of the current bacteria amount in the friendly cell.
While the bacteria are moving towards their destination the player cannot change their route or destination.

When the bacteria reach the destination cell, the destination's bacteria amount is lowered by the amount you have sent.
If you have sent more bacteria than the destination cell has, you take the ownership of that cell.
If the bacteria amount is the same in the destination cell, it converts to the neutral cell.

You can also strenghten your friendly cells when they are under attack. Just send the bacteria from one of your cells to another. However remember that the maximum bacteria amount in one cell cannot exceed the value of 100, so any additional
bacteria that "doesn't fit" will just be wasted and lost forever.

I hope you now understand the basics. Be careful tough, every attack makes your cells weaker and easier to capture by the enemy. Plan your moves wisely!

# Configuration
In the main menu you can choose one of the six AI algorithms you want to play against.
You can also watch two AIs fighting with each other. In this case select an AI algorithm of your choice instead of "Human".

# Controls
Use the left mouse button to select a source cell and use it again on a destination cell. You can also cancel your selection of the source cell by clicking it for the second time with the left mouse button.
Use ESC to return to main menu from the results board.

# Troubles during building process?
Make sure you are connected to the Internet as Gradle may need to download additional packages required for building.
