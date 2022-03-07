package student_player.mcts_folder;

import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Node {
	
	
    Node parent;
    ArrayList<Node> children;
    State state;
    
    public Node(PentagoBoardState pbs) {
        this.state = new State(pbs);
        children = new ArrayList<>();
    }
    
    public Node(State state) {
        this.state = state;
        children = new ArrayList<>();
    }

  

    public Node(Node node) {
        this.children = new ArrayList<>();
        this.state = new State(node.state);
        
        if (node.parent != null) {
            this.parent = node.parent;
        }
        ArrayList<Node> childArray = node.children;
        for (Node child : childArray) {
            this.children.add(new Node(child));
        }
    }

    public void setParent(Node node) {
        this.parent = node;
    }
    
  

    public Node bestChild() {
        return Collections.max(
        		this.children, 
        		Comparator.comparing(c -> {
                       return c.state.si;
        }));
    }

    

}
