public class Cell {

    private boolean mine;
    private boolean flagged;
    private boolean covered;
    private int adjacentMines;




    public Cell(){
        mine =  false;
        flagged = false;
        covered = true;
        adjacentMines = 0;
    }

    public boolean isMine() {
        return mine;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public boolean isCovered() {
        return covered;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }


}
