package student_player;

import boardgame.Move;

import pentago_twist.PentagoPlayer;
import student_player.mcts_folder.MCTS;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

	
	
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260833548");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
        
        
        // Is random the best you can do?
    	 long start = System.currentTimeMillis();
    	 PentagoMove myMove;
         MCTS mcts = new MCTS();
         myMove = mcts.getMove(boardState,boardState.getTurnPlayer());         
         long end = System.currentTimeMillis();
         System.out.print("time:"+(end-start));

        // Return your move to be processed by the server.
        return myMove;
    }
}