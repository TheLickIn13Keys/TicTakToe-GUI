import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener
{
    // setting up ALL the variables
    JFrame window = new JFrame("Bardia's Tic Tac Toe Game");

    JMenuBar mnuMain = new JMenuBar();
    JMenuItem   mnuNewGame = new JMenuItem("  New Game"), 
    mnuGameTitle = new JMenuItem("|Tic Tac Toe|  "),
    mnuStartingPlayer = new JMenuItem(" Starting Player"),
    mnuExit = new JMenuItem("    Quit");
    int size;
    JButton btnEmpty[] = new JButton[size];
    

    JPanel  pnlNewGame = new JPanel(),
    pnlNorth = new JPanel(),
    pnlSouth = new JPanel(),
    pnlTop = new JPanel(),
    pnlBottom = new JPanel(),
    pnlPlayingField = new JPanel();
    JPanel radioPanel = new JPanel();

    private JRadioButton SelectX = new JRadioButton("User Plays X", false);
    private  JRadioButton SelectO = new JRadioButton("User Plays O", false);
    private ButtonGroup radioGroup;
    private  String startingPlayer= "";
    final int X = 800, Y = 480, color = 190; // size of the game window
    private boolean inGame = false;
    private boolean win = false;
    private boolean btnEmptyClicked = false;
    private boolean setTableEnabled = false;
    private String message;
    private Font font = new Font("Arial", Font.BOLD, 100);
    private int remainingMoves = 1;
    private String [][] board;
    boolean winXEnd = false;
    boolean winOEnd = false;
    boolean catsGameEnd = false;

    //===============================  GUI  ========================================//
    public GUI() //This is the constructor
    {
        //Setting window properties:
        size = Integer.parseInt(JOptionPane.showInputDialog("What board dimensions do you want?"));
        board = new String [size][size];
        window.setSize(X, Y);
        window.setLocation(300, 180);
        window.setResizable(true);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

        //------------  Sets up Panels and text fields  ------------------------//
        // setting Panel layouts and properties
        pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER));

        pnlNorth.setBackground(new Color(70, 70, 70));
        pnlSouth.setBackground(new Color(color, color, color));

        pnlTop.setBackground(new Color(color, color, color));
        pnlBottom.setBackground(new Color(color, color, color));

        pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));

        radioPanel.setBackground(new Color(color, color, color));
        pnlBottom.setBackground(new Color(color, color, color));
        radioPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Who Goes First?"));

        // adding menu items to menu bar
        mnuMain.add(mnuGameTitle);
        mnuGameTitle.setEnabled(false);
        mnuGameTitle.setFont(new Font("Purisa",Font.BOLD,18));
        mnuMain.add(mnuNewGame);
        mnuNewGame.setFont(new Font("Purisa",Font.BOLD,18));
        mnuMain.add(mnuStartingPlayer);
        mnuStartingPlayer.setFont(new Font("Purisa",Font.BOLD,18));
        mnuMain.add(mnuExit);
        mnuExit.setFont(new Font("Purisa",Font.BOLD,18));//---->Menu Bar Complete

        // adding X & O options to menu
        SelectX.setFont(new Font("Purisa",Font.BOLD,18));
        SelectO.setFont(new Font("Purisa",Font.BOLD,18));
        radioGroup = new ButtonGroup(); // create ButtonGroup
        radioGroup.add(SelectX); // add plain to group
        radioGroup.add(SelectO);
        radioPanel.add(SelectX);
        radioPanel.add(SelectO);

        // adding Action Listener to all the Buttons and Menu Items
        mnuNewGame.addActionListener(this);
        mnuExit.addActionListener(this);
        mnuStartingPlayer.addActionListener(this);

        // setting up the playing field
        pnlPlayingField.setLayout(new GridLayout(size, size, size-1, size-1));
        pnlPlayingField.setBackground(Color.black);
        btnEmpty = new JButton[(size*size)+1];
        for(int x=1; x <= size*size; ++x)   
        {
            btnEmpty[x] = new JButton();
            btnEmpty[x].setBackground(new Color(220, 220, 220));
            btnEmpty[x].addActionListener(this);
            pnlPlayingField.add(btnEmpty[x]);
            btnEmpty[x].setEnabled(setTableEnabled);
        }

        // adding everything needed to pnlNorth and pnlSouth
        pnlNorth.add(mnuMain);
        BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);

        // adding to window and Showing window
        window.add(pnlNorth, BorderLayout.NORTH);
        window.add(pnlSouth, BorderLayout.CENTER);
        window.setVisible(true);
    }// End GUI

    // ===========  Start Action Performed  ===============//
    public void actionPerformed(ActionEvent click)  
    {
        // get the mouse click from the user
        Object source = click.getSource();

        // check if a button was clicked on the gameboard
        for(int currentMove=1; currentMove <= size*size; ++currentMove) 
        {
            if(source == btnEmpty[currentMove] && remainingMoves < (size*size)+1)  
            {
                btnEmptyClicked = true;
                BusinessLogic.GetMove(currentMove, remainingMoves, font, 
                    btnEmpty, startingPlayer);              
                btnEmpty[currentMove].setEnabled(false);
                pnlPlayingField.requestFocus();
                ++remainingMoves;
            }
        }

        // if a button was clicked on the gameboard, check for a winner
        if(btnEmptyClicked) 
        {
            inGame = true;
            CheckWin();
            btnEmptyClicked = false;
        }
        if(winXEnd)
        {
            message = "X has Won";
            JOptionPane.showMessageDialog(null, message, 
                "congrats", JOptionPane.INFORMATION_MESSAGE);
            inGame = false;
            startingPlayer = "";
            setTableEnabled = false;

            if(!inGame) 
            {
                RedrawGameBoard();
                winXEnd = false;
            }
        }
        else if(winOEnd)
        {
            message = "O has won";

            JOptionPane.showMessageDialog(null, message, 
                "congrats", JOptionPane.INFORMATION_MESSAGE);
            inGame = false;
            startingPlayer = "";
            setTableEnabled = false;
            // redraw the gameboard to its initial state
            if(!inGame) 
            {
                RedrawGameBoard();
                winOEnd = false;
            }

        }
        else if(catsGameEnd)
        {
            message = "Nobody has won";

            JOptionPane.showMessageDialog(null, message, 
                "congrats", JOptionPane.INFORMATION_MESSAGE);
            inGame = false;
            startingPlayer = "";
            setTableEnabled = false;
            if(!inGame) 
            {
                RedrawGameBoard();
                catsGameEnd = false;
            }

        }
        // check if the user clicks on a menu item
        if(source == mnuNewGame)    
        {
            System.out.println(startingPlayer);
            BusinessLogic.ClearPanelSouth(pnlSouth,pnlTop,pnlNewGame,
                pnlPlayingField,pnlBottom,radioPanel);
            if(startingPlayer.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please Select a Starting Player", 
                    "Oops..", JOptionPane.ERROR_MESSAGE);
                BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
            }
            else
            {
                if(inGame)  
                {
                    int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                            " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                        , "New Game?" ,JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_OPTION)    
                    {
                        inGame = false;
                        startingPlayer = "";
                        setTableEnabled = false;
                    }
                    else
                    {
                        BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                    }
                }
                // redraw the gameboard to its initial state
                if(!inGame) 
                {
                    RedrawGameBoard();
                }
            }       
        }       
        // exit button
        else if(source == mnuExit)  
        {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", 
                    "Quit" ,JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
        // select X or O player 
        else if(source == mnuStartingPlayer)  
        {
            if(inGame)  
            {
                JOptionPane.showMessageDialog(null, "Cannot select a new Starting "+
                    "Player at this time.nFinish the current game, or select a New Game "+
                    "to continue", "Game In Session..", JOptionPane.INFORMATION_MESSAGE);
                BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
            }
            else
            {
                setTableEnabled = true;
                BusinessLogic.ClearPanelSouth(pnlSouth,pnlTop,pnlNewGame,
                    pnlPlayingField,pnlBottom,radioPanel);

                SelectX.addActionListener(new RadioListener());
                SelectO.addActionListener(new RadioListener());
                radioPanel.setLayout(new GridLayout(2,1));

                radioPanel.add(SelectX);
                radioPanel.add(SelectO);
                pnlSouth.setLayout(new GridLayout(2, 1, 2, 1));
                pnlSouth.add(radioPanel);
                pnlSouth.add(pnlBottom);
            }
        }
        pnlSouth.setVisible(false); 
        pnlSouth.setVisible(true);  
    }// End Action Performed

    // ===========  Start RadioListener  ===============//  
    private class RadioListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) 
        {
            JRadioButton theButton = (JRadioButton)event.getSource();
            if(theButton.getText().equals("User Plays X")) 
            {
                startingPlayer = "X";
            }
            if(theButton.getText().equals("User Plays O"))
            {
                startingPlayer = "O";
            }

            // redisplay the gameboard to the screen
            pnlSouth.setVisible(false); 
            pnlSouth.setVisible(true);          
            RedrawGameBoard();
        }
    }// End RadioListener
    /*
    ----------------------------------
    Start of all the other methods. |
    ----------------------------------
     */
    private void RedrawGameBoard()  
    {
        BusinessLogic.ClearPanelSouth(pnlSouth,pnlTop,pnlNewGame,
            pnlPlayingField,pnlBottom,radioPanel);
        BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);       

        remainingMoves = 1;

        for(int x=1; x <= size*size; ++x)   
        {
            btnEmpty[x].setText("");
            btnEmpty[x].setEnabled(setTableEnabled);
        }

        win = false;        
    }

    public String[][] createBoard()
    {
        int btnSize = (int) Math.sqrt(btnEmpty.length);
        for(int rows = 0; rows < btnSize; rows++)
        {
            for(int cols = 0; cols<btnSize; cols++)
            {
                board[rows][cols] = null;
            }
        }
        return board;
    }

    public String[][] addElements(String[][] board)
    {
        for(int z = 1; z<size*size+1; z++)
        {
            board[(z-1)%size][((z-1)/size)] = btnEmpty[z].getText();
        }
        return board;
    }

    private void CheckWin() 
    {   
        board = createBoard();
        board = addElements(board);
        boolean winX = checkWinX(board);
        if(winX == true)
        {
            winXEnd =  true;
        }
        boolean winO = checkWinO(board);
        if(winO == true)
        {
            winOEnd =  true;
        }
        boolean catsGame = checkCatsGameWin(board);
        if(catsGame == true)
        {
            catsGameEnd = true;
        }

    }

    public boolean checkWinX(String [][] board)
    {
        int x = checkWinXAcross(board);
        boolean win = true;
        boolean notWin = false;
        if(x == 0)
        {
            //System.out.println("Player has won");
            //System.out.println("XAcross?");
            return win;
        }
        int y = checkWinXDown(board);
        if(y == 0)
        {
            //System.out.println("Player has won");
            //System.out.println("");
            //System.out.println("XDown?");
            return win;
        }
        int z = checkWinXForwardDiagonal(board);
        if(z == 0)
        {
            //System.out.println("Player has won");
            //System.out.println("");
            // System.out.println("FDiag");
            return win;
        }
        int u = checkWinXBackDiagonal(board);
        if(u == 0)
        {
            //System.out.println("Player has won");
            //System.out.println("");
            //System.out.println("BDiag");
            return win;
        }
        return notWin;
    }

    public int checkWinXAcross(String [][] board)
    {
        int changeCol = 0;
        int count = 0;
        int x = 0;
        int y = 1;
        boolean exit = false;
        int endWhile = 0;
        while(exit == false)
        {
            for(int rows = 0; rows<board[0].length; rows++)
            {
                if(board[rows][changeCol] == "X")
                {
                    count++;
                    if(count == board[0].length)
                    {

                        exit = true;
                        
                        return x;
                    }
                }
            }
            count = 0;
            changeCol++;
            endWhile++;
            if(endWhile == board[0].length)
            {
                exit = true;
            }
        }
        return y;
    }

    public int checkWinXDown(String [][] board)
    {
        int changeRow = 0;
        int count = 0;
        int x = 0;
        int y = 1;
        boolean exit = false;
        int endWhile = 0;
        while(exit == false)
        {
            for(int cols = 0; cols<board[0].length; cols++)
            {
                if(board[changeRow][cols] == "X")
                {
                    count++;
                    if(count == board[0].length)
                    {

                        exit = true;
                        
                        return x;
                    }
                }
            }
            count = 0;
            changeRow++;
            endWhile++;
            if(endWhile == board[0].length)
            {
                exit = true;
            }
        }
        return y;
    }

    // public void printBoard(String [][] board)
    // {
        // for( int i = 0; i<board.length; i++)
        // {
            // for(int y = 0; y<board.length; y++)
            // {
                // System.out.print(board[i][y]);

            // }
            // System.out.println();
        // }
    // }

    public int checkWinXForwardDiagonal(String [][] board)
    {
        int win = 0;
        int notWin = 1;
        int count = 0;

        for(int rows = 0; rows<board.length-1; rows++)
        {
            for(int cols = 0; cols< board[0].length-1; cols++)
            {
                if(board[rows][cols] == "X" && board[rows+1][cols+1] == "X")
                {
                    count++;

                }
            }
        }
        
        // printBoard(board);
        if(count == board.length-1)
        {

            return win;
        }
        else
        {
            return notWin;
        }
    }

    public int checkWinXBackDiagonal(String [][]board)
    {

        int win = 0;
        int notWin = 1;
        int count = 0;
        int lastRow = board.length -1;
        int lastCol = board[0].length - 1;
        for(int rows = 0; rows<board.length-1; rows++)
        {
            for(int cols = board[0].length-1; cols>0; cols--)
            {
                if(board[rows][cols] == "X" && board[rows+1][cols-1] == "X")
                {
                    count++;

                }
            }
        }

        if(count == board.length-1)
        {
            return win;
        }
        else
        {
            return notWin;
        }

    }

    public  boolean checkWinO(String [][] board)
    {
        int x = checkWinOAcross(board);
        boolean win = true;
        boolean notWin = false;
        if(x == 0)
        {
            //System.out.println("Player has won");
            //System.out.println("");

            return win;
        }
        int y = checkWinODown(board);
        if(y == 0)
        {
            //System.out.println("Player has won");
            //System.out.println("");

            return win;
        }
        int z = checkWinOForwardDiagonal(board);
        if(z == 0)
        {
            //System.out.println("Player has won");
            //System.out.println("");

            return win;
        }
        int u = checkWinOBackDiagonal(board);
        if(u == 0)
        {
            //System.out.println("Player has won");
            //System.out.println("");

            return win;
        }
        return notWin;
    }

    public boolean checkCatsGameWin(String [][] board)
    {
        int x = checkCatsGame(board);
        boolean win = true;
        boolean notWin = false;
        if(x == 0)
        {
            return win;
        }
        else
        {
            return notWin;
        }

    }

    public int checkWinOAcross(String [][] board)
    {
        int win = 0;
        int notWin = 1;
        int changeRow = 0;
        int count = 0;
        boolean exit = false;
        int endWhile = 0;
        while(exit == false)
        {
            for(int cols = 0; cols<board[0].length; cols++)
            {
                if(board[changeRow][cols] == "O")
                {
                    count++;
                    if(count == board[0].length)
                    {
                        System.out.println("Computer has won");
                        exit = true;
                        return win;
                    }
                }
            }
            count = 0;
            changeRow++;
            endWhile++;
            if(endWhile == board[0].length)
            {
                exit = true;
            }
        }
        return notWin;
    }

    public int checkWinODown(String [][] board)
    {
        int changeCol = 0;
        int count = 0;
        int endWhile = 0;
        int x = 0;
        int y = 1;
        boolean exit = false;
        while(exit == false)
        {
            for(int rows = 0; rows<board[0].length; rows++)
            {
                if(board[rows][changeCol] == "O")
                {
                    count++;
                    if(count == board[0].length)
                    {

                        exit = true;
                        return x;
                    }

                }
            }
            count = 0;
            changeCol++;
            endWhile++;
            if(endWhile == board[0].length)
            {
                exit = true;
            }
        }
        return y;
    }

    public int checkWinOForwardDiagonal(String [][] board)
    {
        int win = 0;
        int notWin = 1;
        int count = 0;
        for(int rows = 0; rows<board.length-1; rows++)
        {
            for(int cols = 0; cols< board[0].length-1; cols++)
            {
                if(board[rows][cols] == "O" && board[rows+1][cols+1] == "O")
                {
                    count++;
                }
            }
        }
        if(count == board.length-1)
        {
            return win;
        }
        else
        {
            return notWin;
        }
    }

    public int checkWinOBackDiagonal(String [][]board)
    {

        int win = 0;
        int notWin = 1;
        int count = 0;
        int lastRow = board.length -1;
        int lastCol = board[0].length - 1;
        for(int rows = 0; rows<board.length-1; rows++)
        {
            for(int cols = board[0].length-1; cols>0; cols--)
            {
                if(board[rows][cols] == "O" && board[rows+1][cols-1] == "O")
                {
                    count++;

                }
            }
        }
        if(count == board.length-1)
        {
            return win;
        }
        else
        {
            return notWin;
        }
    }

    public int checkCatsGame(String [][] board)
    {
        int count = 0;
        int catsGame = 0;
        int notCatsGame = 1;
        for(int rows = 0; rows<board.length; rows++)
        {
            for(int cols = 0; cols<board[0].length; cols++)
            {
                if(board[rows][cols] == "X" ||board[rows][cols] == "O")
                {
                    count++;
                }

            }

        }
        if(btnEmpty.length == count)
        {
            return catsGame;
        }
        else
        {
            return notCatsGame;
        }
    }
}