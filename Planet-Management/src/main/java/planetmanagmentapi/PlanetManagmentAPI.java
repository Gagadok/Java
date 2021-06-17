package planetmanagmentapi;

import java.util.Queue;

/**
 *
 * @author Gagadok
 */
public interface PlanetManagmentAPI {

    public Queue<String> ShowOverlords();
    
    public boolean AddOverlord(String overlords_name, int overlords_age);

    public boolean AddPlanet(String planets_title);

    public boolean AppointOverlord(int overlords_id, String planets_title);

    public boolean DestroyPlanet(String planets_title);

    public Queue<String> FindLoafers();

    public Queue<String> Top10YoungOverlords();
    
    public void destroy();
    
}
