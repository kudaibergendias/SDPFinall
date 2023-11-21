import java.util.Random;
import java.util.Scanner;

// Observer Pattern
interface Observer {
    void update(Event event);
}

class EventObserver implements Observer {
    private Character character;

    public EventObserver(Character character) {
        this.character = character;
    }

    @Override
    public void update(Event event) {
        System.out.println("Event observed: " + event.description);
        character.handleEvent(event);
    }
}

// Singleton Pattern
class GameManager {
    private static GameManager instance;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
}

// Strategy Pattern
interface BendingStrategy {
    void performBending();
}

class AirBendingStrategy implements BendingStrategy {
    @Override
    public void performBending() {
        System.out.println("Performing Air Bending!");
    }
}

// Adapter Pattern
interface SpiritsBending {
    void connectWithSpirits();
}

class AvatarAdapter implements SpiritsBending {
    private Character character;

    public AvatarAdapter(Character character) {
        this.character = character;
    }

    @Override
    public void connectWithSpirits() {
        System.out.println("Connecting with Spirits through the Avatar!");
        character.applySpiritConnection();
    }
}

// Decorator Pattern
class PowerPointsDecorator implements BendingStrategy {
    private BendingStrategy strategy;

    public PowerPointsDecorator(BendingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void performBending() {
        strategy.performBending();
        System.out.println("Power points modified after bending.");
    }
}

// Factory Pattern
class CharacterFactory {
    Character createCharacter(String name, String nation, String bendingType, BendingStrategy bendingStrategy, int powerPoints) {
        return new Character(name, nation, bendingType, bendingStrategy, powerPoints);
    }
}

class Character {
    String name;
    String nation;
    String bendingType;
    int powerPoints;
    BendingStrategy bendingStrategy;

    Character(String name, String nation, String bendingType, BendingStrategy bendingStrategy, int powerPoints) {
        this.name = name;
        this.nation = nation;
        this.bendingType = bendingType;
        this.bendingStrategy = bendingStrategy;
        this.powerPoints = powerPoints;
    }

    void handleEvent(Event event) {
        System.out.println("Character - " + name + " handling event: " + event.description);
    }

    void performBending() {
        bendingStrategy.performBending();
    }

    void display() {
        System.out.println("Character - " + name + ", Nation - " + nation);
    }

    void applySpiritConnection() {
        System.out.println("Spirit connection applied.");
        // Additional logic for modifying power points can be added here
        int spiritModifier = new Random().nextInt(10) - 5; // Between -5 and 5
        powerPoints += spiritModifier;
        System.out.println("Power points modified by: " + spiritModifier);
    }
}

class Event {
    String description;

    Event(String description) {
        this.description = description;
    }
}

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static Character createOpponent(String bendingType) {
        int powerPoints = random.nextInt(91) + 40; // Random power points between 40 and 130
        return new Character("Opponent", "Unknown", bendingType, null, powerPoints);
    }

    static Event generateRandomEvent() {
        String[] events = {
                "A festival is happening in the Water Tribe!",
                "Political tension rises in the Earth Kingdom.",
                "Fire Nation discovers a new bending technique.",
                "Air Nomads organize a peaceful meditation event."
        };

        int randomIndex = random.nextInt(events.length);
        return new Event(events[randomIndex]);
    }

    static void updatePowerPoints(Character player, Event event) {
        int powerPointModifier = 0;

        switch (event.description.toLowerCase()) {
            case "a festival is happening in the water tribe!":
                powerPointModifier = 10;
                break;
            case "political tension rises in the earth kingdom.":
                powerPointModifier = -5;
                break;
            case "fire nation discovers a new bending technique.":
                powerPointModifier = 8;
                break;
            case "air nomads organize a peaceful meditation event.":
                powerPointModifier = 7;
                break;
        }

        System.out.println("Power points updated based on the event.");
        player.powerPoints += powerPointModifier;
    }

    static void displayCharacterInfo(Character player) {
        System.out.println("==== Character Info ====");
        System.out.println("Name: " + player.name);
        System.out.println("Nation: " + player.nation);
        System.out.println("Bending Type: " + player.bendingType);
        System.out.println("Power Points: " + player.powerPoints);
        System.out.println("========================");
    }

    static void performBattle(Character player, Character opponent) {
        if (player.powerPoints > opponent.powerPoints) {
            System.out.println(player.name + " wins!");
        } else if (player.powerPoints < opponent.powerPoints) {
            System.out.println(opponent.name + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    static void displayBattleResult(Character player, Character opponent) {
        System.out.println("Battle Result:");
        System.out.println(player.name + " Power Points: " + player.powerPoints);
        System.out.println(opponent.name + " Power Points: " + opponent.powerPoints);
    }

    static Character chooseBattle(Character player) {
        System.out.println("Do you want to engage in a battle? (yes/no)");
        String response = scanner.nextLine();

        if ("yes".equalsIgnoreCase(response)) {
            System.out.println("Choose your opponent (Air Mage, Water Mage, Earth Mage, Fire Mage):");
            String opponentBendingType = scanner.nextLine();

            Character opponent = createOpponent(opponentBendingType);

            System.out.println("Battle between " + player.name + " and " + opponent.name + "!");
            displayBattleResult(player, opponent);
            performBattle(player, opponent);

            return opponent;
        } else {
            System.out.println("You decided not to engage in a battle.");
            return null;
        }
    }

    public static void main(String[] args) {
        GameManager gameManager = GameManager.getInstance();

        Character player = createCharacter();
        EventObserver eventObserver = new EventObserver(player);

        while (true) {
            Event event = generateRandomEvent();
            System.out.println("Event: " + event.description);

            // Notify observers about the event
            eventObserver.update(event);

            // Update power points based on event
            updatePowerPoints(player, event);

            System.out.println("Do you want to connect with spirits through the avatar? (yes/no)");
            String response = scanner.nextLine();

            if ("yes".equalsIgnoreCase(response)) {
                // Use the Adapter to connect with spirits through the avatar
                SpiritsBending avatarAdapter = new AvatarAdapter(player);
                avatarAdapter.connectWithSpirits();
            } else if ("no".equalsIgnoreCase(response)) {
                break;
            }

            Character opponent = chooseBattle(player);

            if (opponent != null) {
                // You can update the world map or handle other logic here
            }
        }
    }

    static Character createCharacter() {
        System.out.println("Enter character name:");
        String name = scanner.nextLine();

        System.out.println("Select nation (Air, Water, Earth, Fire):");
        String nation = scanner.nextLine();

        System.out.println("Select bending type:");
        String bendingType = scanner.nextLine();

        // Use the Strategy Pattern to set the bending strategy based on bending type
        BendingStrategy bendingStrategy = null;

        switch (bendingType.toLowerCase()) {
            case "air":
                bendingStrategy = new AirBendingStrategy();
                break;
            // Add cases for other bending types as needed

            default:
                System.out.println("Invalid bending type. Default bending strategy will be used.");
                bendingStrategy = () -> System.out.println("Performing Default Bending!");
                break;
        }

        int powerPoints = random.nextInt(51) + 50;  // Power points initialized to a random number between 50 and 100

        CharacterFactory characterFactory = new CharacterFactory();
        return characterFactory.createCharacter(name, nation, bendingType, bendingStrategy, powerPoints);
    }

    // The rest of your code remains unchanged
}
