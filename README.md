#Sheriff of Nottingham


  Simulation of a board game (Sheriff of Nottingham). The project is organised
in three packages, each one having the following roles:

- main, which contains the following classes:
  - Main
    - contains the internal classes GameInputLoader and GameInput, used for
      reading and storing the player strategies and goods ids;
    - calls methods from the Game class;

  - Game
    - can be instantiated only once; uses the Singleton Pattern;
    - contains the game logic and methods used for creating the decks and the
      players, sorting and printing the leaderboard, playing the rounds;

    - GameInput
      - stores the lists of player strategies and goods ids, as read from the
        file;

- goods, which contains the following classes:
  - Goods
    - stores constants that describe different types of goods;

  - IDConverter
    - converts the id of a good to its type;

  - MerchantBag
    - contains information about the goods placed in the merchant bag, the
      bribe and the declared type;

  - MerchantStand
    - stores a list of goods and their occurences;
    - contains methods used for computing king's and queen's bonus, adding the
      bonus resulted from the illegal goods, adding goods to the stand and
      counting their occurences;


- players, which contains the following classes:
  - Player
    - abstract class, contains the default strategies used by the players, such
      as extracting cards from the deck, calculating their final scores, giving
      and receiving coins, inspecting a player; these methods can't be
      overridden;
    - contains the abstract method sheriff, which is implemented in the derived
      classes;
    - implements the merchant method, which can be overridden in the derived
      classes;
    - each player has a certain amount of coins, a merchant stand, a merchant
      bag and a set of goods in his hands;
    - the relationship between Player and MerchantBag is aggregation, whereas
      the ones between Player and MerchantStand and Player and InHand are
      compositions;

  - BasePlayer
    - inherits the Player class;
    - implements the sheriff method, where he inspects all the players;

  - BribePlayer
    - inherits the Player class;
    - overrides the merchant method, where he tries to put in his bag as many
      illegal goods as he can; if that's impossible, he uses the implementation
      from the parent class;
    - implements the sheriff method; inspects the players situated on his left
      and his right;

  - GreedyPlayer
    - inherits the Player class;
    - overrides the merchant method; he uses the implementation from the parent
      class, but, when the round is even, adds an illegal good to the bag;
    - implements the sheriff method, where he inspects all the players that
      don't bribe him;

  - WizardPlayer
    - inherits the Player class;
    - overrides the merchant method; if the round is odd, he uses the
      implementation from the parent class, otherwise uses an updated version
      of the royal strategy;
    - implements the sheriff method, where he inspects a player which tries to
      bribe him or which has more than two goods in his merchant bag;

  - InHand
    - contains a list of goods that a player has at a certain point in his
      hands;
    - implements helper methods such as getting the most profitable illegal or
      legal good from the hand, counting the number of occurences of a certain
      good, getting the most common goods from the hand or checking whether
      there the player has any illegal goods.
