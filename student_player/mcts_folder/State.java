package student_player.mcts_folder;

import java.util.ArrayList;

import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

public class State {
	PentagoBoardState PBS;
    PentagoMove move;
    int player;
    int si;
    int wi;
   
    
    public State(PentagoBoardState pbs) {
        this.PBS = pbs;
    }

   

    public State(State state) {
        this.PBS = (PentagoBoardState) state.PBS.clone();
        this.player = state.player;
        this.si = state.si;
        this.wi = state.wi;
        this.move = state.move;
    }



    public void setPlayer(int p) {
        this.player = p;
    }


    public void setMove(PentagoMove m) {
        this.move = m;
    }

    public int getOpponent(){
        if (this.player == PentagoBoardState.WHITE) {
            return PentagoBoardState.BLACK;
        }
        else if (this.player == PentagoBoardState.BLACK) {
            return PentagoBoardState.WHITE;
        }
        else {
            return -1;
        }
    }

    public ArrayList<State> getAllPossibleStates() {
     

        ArrayList<State> possibleStates = new ArrayList<>();
        ArrayList<PentagoMove> availableMoves = this.PBS.getAllLegalMoves();
        for(PentagoMove m: availableMoves) {
        	
            State newState = new State((PentagoBoardState)this.PBS.clone());
            newState.PBS.processMove(m);          
            newState.setPlayer(getOpponent());          
            newState.setMove(m);
            possibleStates.add(newState);
            
        }  
        return possibleStates;
    }






}
