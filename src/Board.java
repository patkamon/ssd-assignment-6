import java.util.Random;

public class Board {

    private Cell [][] cells;
    private int size;
    private int mineCount;
    private Random random = new Random();

    public Board(int size,int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        initCells();
        seedMines();
        genNumbers();

    }

    public void uncover(int row,int col){
        Cell cell = getCell(row,col);

        if(cell == null){
            return;
        }

        if(cell.isCovered()){
            cell.setCovered(false);

        if(cell.getAdjacentMines() ==0) {
            int[][] pairs = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1},/*cell*/{0, 1},
                    {1, -1}, {1, 0}, {1, 1}
            };
            for (int[] pair : pairs) {
                    uncover(row + pair[0], col + pair[1]);
                }
            }
        }
        }

    public void dead(){
        for(int row = 0; row <size ; row++){
            for(int col = 0; col< size; col++){
                Cell cell = getCell(row,col);
                if(cell.isMine()){
                    cell.setCovered(false);
                }
            }
        }
    }




    private void initCells(){
        cells = new Cell[size][size];
        for(int row = 0 ; row < size; row++){
            for( int col = 0; col < size ; col ++){
                cells[row][col] = new Cell();
            }
        }

    }

    private void seedMines(){
        int seeded = 0;
        while(seeded< mineCount){
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            Cell cell = getCell(row,col);
            if(cell.isMine()){
                continue;
            }
            cell.setMine(true);
            seeded++;
        }
    }


    private void genNumbers() {
        for(int row = 0 ; row < size; row++){
            for( int col = 0; col < size ; col ++){
                Cell cell = getCell(row,col);
                if(cell.isMine()){
                    continue;
                }
                int[][] pairs = {
                        {-1,-1},{-1,0},{-1,1},
                        {0,-1},/*cell*/{0,1},
                        {1,-1},{1,0},{1,1}
                };
                int count = 0;
                for(int [] pair : pairs){
                    Cell adj = getCell(row + pair[0], col +pair[1]);
                    if (adj!= null && adj.isMine()){
                        count++;
                    }
                }
                cell.setAdjacentMines(count);
            }
        }
    }

    public boolean mineUncovered(){
        for(int row = 0; row <size ; row++){
            for(int col = 0; col< size; col++){
                Cell cell = getCell(row,col);
                if(cell.isMine() && !cell.isCovered()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isWin(int mine){
        int all = 0;
        for(int row = 0; row <size ; row++){
            for(int col = 0; col< size; col++){
                Cell cell = getCell(row,col);
                if(cell.isCovered()){
                    all++;
                }
            }
        }
        System.out.println("all"+all);
            return all == mine;
    }

    public Cell getCell(int row,int col){
        if(row <0 || col<0 || row >= size || col >= size){
            return null;
        }
        return cells[row][col];
    }



}
