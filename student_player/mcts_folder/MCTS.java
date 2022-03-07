package student_player.mcts_folder;

import boardgame.Board;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;
import student_player.mcts_folder.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MCTS {
   
   
    private int opponent;
    Random rand = new Random();
   
    //
    // si = Node's number of time visited
    // wi = Node's number of time visited && winned
    // sp = Node's parent's number of time visited
    // c=sqrt(2)
    // UCT:  wi/si + c*sqrt( ln(sp)/si ) 
    //
    public static double uct(int sp, int wi, int si) {
        double result = 0;
    	if (si == 0) {
            result = Integer.MAX_VALUE;
        }
        else {
            result = (wi/si) + 1.41 * Math.sqrt(Math.log(sp)/ si);           
        }
    	return result;
    }

    public static Node getBestChild(Node parent){
        int sp = parent.state.si;
        return Collections.max(
                parent.children,
                Comparator.comparing(c ->
                    uct(sp, c.state.wi, c.state.si)
                )
        );
    }
    
    private Node getBestLeaf(Node root) {
        Node node = root;
        while (node.children.size() != 0) {
            node = getBestChild(node);
        }
        return node;
    }
    //
    //


    private int simulate(Node node) {
        Node temp = new Node(node);
        State tempState = temp.state;
        
        int winner = tempState.PBS.getWinner();
      
        while (winner == Board.NOBODY) {
            tempState.player = tempState.getOpponent();         
            ArrayList<PentagoMove> allMoves = tempState.PBS.getAllLegalMoves();
            PentagoMove randomMove = allMoves.get(rand.nextInt(allMoves.size()));         
            tempState.PBS.processMove(randomMove);
            winner = tempState.PBS.getWinner();
        }
        return winner;
    }
    

    
    //
    // Main MCTS function
    //
    public PentagoMove getMove(PentagoBoardState PBS, int player) {
    	
        long start = System.currentTimeMillis();
        long end = start + 1000 ;  // 1s

        if (player == PentagoBoardState.WHITE) {
            opponent = PentagoBoardState.BLACK;
        }
        else {
            opponent = PentagoBoardState.WHITE;
        }

        
        Node rootNode = new Node(PBS);
        rootNode.state.setPlayer(opponent);

        while (System.currentTimeMillis() < end) {
        	
            // 1. Selection
            Node BestLeaf = getBestLeaf(rootNode);

            // 2.Expansion
            if (BestLeaf.state.PBS.getWinner() == Board.NOBODY) {
            
            	 ArrayList<State> possibleStates = BestLeaf.state.getAllPossibleStates();
                 for(State s:possibleStates ) {               	 
                     Node newNode = new Node(s);
                     newNode.setParent(BestLeaf);
                     newNode.state.setPlayer(BestLeaf.state.getOpponent());
                     BestLeaf.children.add(newNode);
                 }
               
            }
            
            // 3.Simulation
            Node nextNode = BestLeaf;
            if (BestLeaf.children.size() > 0) {
            	int NumOfChild = BestLeaf.children.size();
            	nextNode = BestLeaf.children.get((int)(Math.random()*NumOfChild));
            }
            int playoutWinner = simulate(nextNode);
            
            // 4.Backpropagation
            Node tempNode = nextNode;
            while (tempNode != null) {
                tempNode.state.si++;
                if (tempNode.state.player == playoutWinner) {
                    tempNode.state.wi += 1;
                }
                tempNode = tempNode.parent;
            }
            
        }

        Node resultNode = rootNode.bestChild();       
        return resultNode.state.move;
    }

   
}
