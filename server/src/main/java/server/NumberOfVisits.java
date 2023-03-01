package server.database;

import org.springframework.stereotype.Service;

@Service
public class NameExpansion {
    private int visits = 0;

    public void increase(){
        this.visits++;
    }

    public int value(){
        return visits;
    }
}
