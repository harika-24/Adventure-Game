# Adventure-Game


## About Project:

The project game consists of a dungeon, a network of tunnels and caves that are interconnected
so that player can explore the entire world by traveling from cave to cave through the tunnels
that connect them. The dungeon is a 2D grid, each of the location in the grid could be a cave or 
tunnel based on the number of the entrances each location has. Each cave can have 1,3 or 4 entrances
and a tunnel can have exactly 2 entrances. The caves can possess treasure or arrows which can be collected by 
the player while he travels through the dungeon. The dungeon could be wrapping which means that 
the end nodes of the dungeon would be connected. The player will be given a random start and 
end location, the path between the both must be minimum 5. There is at least one Otyugh in the dungeon 
located at the specially designated end cave and the number of Otyughs are specified by the user. The
player will have arrows which he can use to kill the Otyughs or can be killed by them in the process.
The dungeon also has pits and when player will walk through a tunnel or cave and encounters a pit the game
will end.
The game will be both text-based and GUI-Based which will ask the user to give commands or
interact with the interface and give inputs when prompted for it.


## List of Features

The following are the features of the game that are implemented as part of the project.The functionalities
are designed in such a way they can incorporate changes in the future.
- The dungeon is constructed based on the parameters that the user is passing through the command line.
- Based on the interconnectivity that is passed by the user, kruskal's algorithm is being used to generate the 
wrapping and non-wrapping dungeon.
- The treasure is allocated at the caves at random based on the treasure coverage that the user enters as a
parameter.
- There is method that makes sure that at least there are three types of treasure: diamonds, rubies, and sapphires.
- The printDescription() returns the description of the player that, at a minimum, includes a description 
of what treasure the player has collected.
- The describeLocation() returns the description in the location, that at the minimum includes a description of treasure in 
the room and the possible moves (north, east, south, west) that the player can make from their current location.
- There is a move() function in the player which allows him to move when the valid direction is provided.
- The pickup() would allow the player to pick the treasure or the arrows at the location based on the choice he makes.
- The shoot() will ask for the direction and distance upon which he can fire the arrow and hurt the Otyugh or kill the already
hurt Otyugh.
- The dungeon will have pits which when encountered during the player movement will end the game.
- The GUI has the features to restart the game with same or new dungeon and also quit the game.

## How to Run

- Download the jar file from the /res directory in the zip file.
- Open the command prompt and change the directory to the directory where you have downloaded the
  jar file.
- Make sure the path is correct in the command prompt and run the following command.
- Since the jar can be used to run both the text based and GUI game there are two ways to run the jar.

#### GUI game
````sh
 java -jar filename.jar
`````

- The treasure coverage has to be between 0.2 - 1.
#### Text based game
````sh
 java -jar filename.jar <int rows> <int columns> <boolean wrap> <int interconnectivity> 
 <double treasure coverage> <int noOfMonster> <int noofPits>
`````

## How to use the program
#### GUI Game
- In order to run the program, the user has to run the project jar file.
- The user will see the welcome screen where he clicks on the Game Settings to create/configure the dungeon.
- Once he submits he can see the screen which displays the dungeon and the information regarding the location
and the player. 
- To move around the dungeon the player can either use the arrows keys ( up,down,right and left) or click on the 
possible move direction.
- To pick up the treasure the player has to press the "t" key.
- To pick up the arrow the player has to press the "a" key.
- To shoot an arrow the player has to press the "s" key first, followed by arrow keys for the direction and then 
the number keys for the distance to shoot the arrow.



#### Text-based Game
- In order to run the program, the user has to run the project jar file where the arguments/parameters
for the dungeon are entered. 
- Based on the parameters that are entered in the command line, the object of the dungeon will be created.
- The dungeon object is passed to the controller with the Readable and Appendable objects.
- Then call the playGame() using the Controller object.
- Once the game starts the user has to enter the inputs based on the text displayed to them.


## Description of the example
#### GUI Based Game
When the user will run the jar, he will be prompted with the welcome screen where he can click on the game settings.
After that a dungeon creation form will open up which will create the dungeon for the user. Once the user clicks on 
submit the dungeon game screen will open.
- The screenshot of the landing screen shows where the game settings are on the screen.
- The screenshot of the dungeon_form shows how to enter the values into the form and how to create the dungeon.
- The screenshot of the shooting_arrow shows how the player will get the feedback when the arrow hits the monster.
- The screenshot of the playerKilled shows how the player will be prompted when he enters the cave with an Otyugh and he is killed.

#### Text-based Game
A text based sample run capturing the different conditions: player navigating through the dungeon, player killing an Otyugh, and 
player winning the game by reaching the end.
##### Example Run 1:
This run captures navigating through the dungeon , killing an otyugh and winning the game. The dungeon has 5 rows, 5 columns, 
non wrapping, 0 interconnectivity, 0.3 treasure coverage and 4 Otyughs.
- The player is presented with the choice of Move(M), PickUp(P) and Shoot(S).
- He navigates through the dungeon using Move(M) and kills the Otyughs using Shoot(S).
- When he reaches the final location he will win the game.


## Design/Model Changes
The changes in the design are because they were few aspects of the problem statement that were not
anticipated before. Following are the changes made to old UML design:
- A new parameter number of pits is added to the dungeon, which is used to allocate the number of pits in the 
dungeon. 
- There are two different View interfaces implemented because the methods in each view seemed to differ.
- A new read only model is implemented for the dungeon in order to pass it to the View.

## Assumptions
- I am assuming that the tunnel is type of cave.
- Also assuming that when the player goes to a particular location he will collect all the treasure or arrows
present at that location.
- The start location of the player when the game begins is always assigned randomly.
- Based on the number of pits that the user enters, I am randomly allocating them in the dungeon.

## Limitations
- The player will not have choice in picking up the treasure.
- The game will continue until the player reaches the end or is killed by the Otyughs.
- There is no indication for the player when there is a pit nearby.

## Citations

No research papers have been referred in the process of the developing the system. The following
websites were referred to understand the functioning of tree maps:

- https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum.
- https://stackoverflow.com/questions/10997139/how-to-get-a-list-of-specific-fields-values-from-objects-stored-in-a-list/42092643
- https://stackoverflow.com/questions/2623995/swings-keylistener-and-multiple-keys-pressed-at-the-same-time

  

