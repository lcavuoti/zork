import ch.bbw.zork.Game;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    protected void setup() {
        game = new Game();
        game.play();
    }
}
